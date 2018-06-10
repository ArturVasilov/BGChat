package ru.androidacademy.bgchat.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 10.06.2018.
 */

public class BluetoothController {

    public static final String BLUETOOTH_TAG = "Bluetooth";
    public static final int BLUETOOTH_ENABLE_REQUEST = 123;

    public BluetoothController(boolean debug_mode, AppCompatActivity activity, Callback callback) {

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        DEBUG_MODE = debug_mode;
        LocalActivity = activity;
        discoveryCallback = callback;

        mDiscoveredDevicesList = new ArrayList<>();

        //bluetoothBroadcastReceiver = broadcastReceiver;
        bluetoothBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Log.d(BLUETOOTH_TAG, "In DiscoverReceiver Broascast Receiver");
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    try {

                        String devName = device.getName();
                        String devMAC = device.getAddress();
                        String devSHA1 = "0";
                        if (devName != null) {
                            devSHA1 = AeSimpleSHA1.SHA1(devName);
                        }

                        if (DEBUG_MODE) {
                            Log.d(BLUETOOTH_TAG, "Найдено устройство: " + devName + " " + devMAC +
                                    " " + devSHA1);
                        }

                        if (!mDiscoveredDevicesList.contains(devSHA1)) {
                            mDiscoveredDevicesList.add(devSHA1);
                            discoveryCallback.discoveryFoundedDeviceCallback(devSHA1);
                        }

                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
                    if (DEBUG_MODE) {
                        Log.d(BLUETOOTH_TAG, "START DISCOVERY");
                    }

                    mDiscoveredDevicesList.clear();
                } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                    if (DEBUG_MODE) {
                        Log.d(BLUETOOTH_TAG, "FINISH DISCOVERY. Founded device hashes: ");

                        for (String element : mDiscoveredDevicesList) {
                            Log.d(BLUETOOTH_TAG, ": " + element);
                        }
                    }

                    if (discoveryCallback != null) {
                        discoveryCallback.discoveryFinishedCallback(mDiscoveredDevicesList);
                    }
                }
            }
        };

        bluetoothIntentFilter = new IntentFilter();
        bluetoothIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        bluetoothIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        bluetoothIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        //bluetoothIntentFilter.addAction(BluetoothDevice.ACTION_UUID);

        LocalActivity.registerReceiver(bluetoothBroadcastReceiver, bluetoothIntentFilter);

    }

    private Callback discoveryCallback;
    private BluetoothAdapter bluetoothAdapter;
    private AppCompatActivity LocalActivity;
    private BroadcastReceiver bluetoothBroadcastReceiver;
    private IntentFilter bluetoothIntentFilter;

    private boolean DEBUG_MODE = true;
    private List<String> mDiscoveredDevicesList;

    public boolean isEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public boolean Enable() {

        LocalActivity.registerReceiver(bluetoothBroadcastReceiver, bluetoothIntentFilter);

        return bluetoothAdapter.enable();
    }

    public boolean destroy() {

        LocalActivity.unregisterReceiver(bluetoothBroadcastReceiver);

        return bluetoothAdapter.disable();
    }

    public void enableDeviceRequest() {

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            LocalActivity.startActivityForResult(enableIntent, BLUETOOTH_ENABLE_REQUEST);
        }

    }

    public String getSelfHash() {
        try {
            enableDeviceRequest();
            return AeSimpleSHA1.SHA1(bluetoothAdapter.getName());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @TargetApi(23)
    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = LocalActivity.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += LocalActivity.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {
                LocalActivity.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            }
        }
    }

    public boolean discovery() {

        checkBTPermissions();

        if (bluetoothAdapter.isDiscovering()) {
            if (DEBUG_MODE) {
                Log.d(BLUETOOTH_TAG, "Процесс обнаружения уже запущен!");
                Toast.makeText(LocalActivity, "Процесс сканирования чатов уже запущен", Toast.LENGTH_LONG).show();
            }
            //bluetoothAdapter.cancelDiscovery();
            return false;
        }

        boolean discoveryStatus = bluetoothAdapter.startDiscovery();

        if (DEBUG_MODE) {
            if (discoveryStatus) {
                Log.d(BLUETOOTH_TAG, "Запуск обнаружения");
            } else {
                Log.d(BLUETOOTH_TAG, "Неудача запуска обнаружения");
            }
        }

        return discoveryStatus;
    }

    public interface Callback {
        void discoveryFinishedCallback(List<String> DiscoveredDeviceList);

        void discoveryFoundedDeviceCallback(String deviceHash);
    }

}

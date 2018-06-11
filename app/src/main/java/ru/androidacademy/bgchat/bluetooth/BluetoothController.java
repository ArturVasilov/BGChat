package ru.androidacademy.bgchat.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import ru.androidacademy.bgchat.BuildConfig;

/**
 * Created by User on 10.06.2018.
 */

public class BluetoothController {

    public static final String BLUETOOTH_TAG = "Bluetooth";
    public static final int BLUETOOTH_ENABLE_REQUEST = 123;

    private static final boolean DEBUG_MODE = BuildConfig.DEBUG;
    private Callback callback;
    private BluetoothAdapter bluetoothAdapter;
    private Context localContext;
    private BroadcastReceiver bluetoothBroadcastReceiver;
    private IntentFilter bluetoothIntentFilter;
    private List<String> mDiscoveredDevicesList;

    public BluetoothController(Context context) {

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        localContext = context;

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
                        String devSHA1 = AeSimpleSHA1.SHA1(devMAC);

                        if (DEBUG_MODE) {
                            Log.d(BLUETOOTH_TAG, "Найдено устройство: " + devName + " " + devMAC +
                                    " " + devSHA1);
                        }

                        if (!mDiscoveredDevicesList.contains(devSHA1)) {
                            mDiscoveredDevicesList.add(devSHA1);
                            if (callback != null) {
                                callback.discoveryFoundedDeviceCallback(devSHA1);
                            }
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
                }
            }
        };

        bluetoothIntentFilter = new IntentFilter();
        bluetoothIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        bluetoothIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        bluetoothIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        //bluetoothIntentFilter.addAction(BluetoothDevice.ACTION_UUID);

        localContext.registerReceiver(bluetoothBroadcastReceiver, bluetoothIntentFilter);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public boolean isEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public boolean enable() {
        //localContext.registerReceiver(bluetoothBroadcastReceiver, bluetoothIntentFilter);
        return bluetoothAdapter.enable();
    }

    public boolean destroy() {
        localContext.unregisterReceiver(bluetoothBroadcastReceiver);
        return bluetoothAdapter.disable();
    }

    public void enableDeviceRequest() {
        if (callback == null) {
            throw new IllegalStateException("Set callback before");
        }
        if (!bluetoothAdapter.isEnabled()) {
            enable();
            callback.startBluetoothSettingsActivity();
        }
    }

    public String getSelfBluetoothMacAddress() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String bluetoothMacAddress = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                Field mServiceField = bluetoothAdapter.getClass().getDeclaredField("mService");
                mServiceField.setAccessible(true);

                Object btManagerService = mServiceField.get(bluetoothAdapter);

                if (btManagerService != null) {
                    bluetoothMacAddress = (String) btManagerService.getClass().getMethod("getAddress").invoke(btManagerService);
                }
            } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            bluetoothMacAddress = bluetoothAdapter.getAddress();
        }
        return bluetoothMacAddress;
    }

    public String getSelfHash() {
        try {
            enableDeviceRequest();
            return AeSimpleSHA1.SHA1(getSelfBluetoothMacAddress());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void enableDiscoverability(Activity activity) {
        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            activity.startActivity(discoverableIntent);
        }
    }

    public boolean discovery() {
        if (callback == null) {
            throw new IllegalStateException("Set callback before");
        }
        if (!callback.requestPermission()) {
            return false;
        }

        if (bluetoothAdapter.isDiscovering()) {
            if (DEBUG_MODE) {
                Log.d(BLUETOOTH_TAG, "Процесс обнаружения уже запущен!");
                Toast.makeText(localContext, "Процесс сканирования чатов уже запущен", Toast.LENGTH_LONG).show();
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
        boolean requestPermission();

        void startBluetoothSettingsActivity();

        void discoveryFoundedDeviceCallback(String deviceHash);
    }

}

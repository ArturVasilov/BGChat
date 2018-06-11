package ru.androidacademy.bgchat.view;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import ru.androidacademy.bgchat.App;
import ru.androidacademy.bgchat.R;
import ru.androidacademy.bgchat.bluetooth.BluetoothController;
import ru.androidacademy.bgchat.model.User;

import static ru.androidacademy.bgchat.bluetooth.BluetoothController.BLUETOOTH_ENABLE_REQUEST;
import static ru.androidacademy.bgchat.bluetooth.BluetoothController.BLUETOOTH_TAG;

public class MainActivity extends AppCompatActivity implements HobbyListFragment.HobbiesCallback {
    private static final int RC_SIGN_IN = 3212;
    private static final String TAG = MainActivity.class.getSimpleName();

    private App app;
    private User currentUser;

    private final BluetoothController.Callback callback = new BluetoothController.Callback() {
        @Override
        public boolean requestPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permissionCheck = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                permissionCheck += checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
                if (permissionCheck != 0) {
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
                    return false;
                }
            }
            return true;
        }

        @Override
        public void startBluetoothSettingsActivity() {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, BLUETOOTH_ENABLE_REQUEST);
        }

        @Override
        public void discoveryFoundedDeviceCallback(String deviceHash) {
            Log.d(BLUETOOTH_TAG, "FOUND DEVICE: " + deviceHash);
            app.getUserRepo().readUser(deviceHash, foundUser -> {
                if (foundUser != null) {
                    TextView textView = findViewById(R.id.user_text);
                    textView.setText(foundUser.getName());
                    Toast.makeText(MainActivity.this, foundUser.getName(), Toast.LENGTH_SHORT).show();
                    textView.setOnClickListener(view -> {
                        String room = app.getRoomRepo().createRoom(currentUser, foundUser);
                        RoomActivity.start(MainActivity.this, room, currentUser.getId());
                    });
                }
            });
        }
    };

    private BluetoothController bluetoothController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App) getApplicationContext();
        bluetoothController = app.getBluetoothController();
        bluetoothController.setCallback(callback);
        if (app.getAuthRepo().getCurrentUser() == null) {
            startActivityForResult(app.getAuthRepo().getIntent(), RC_SIGN_IN);
        } else {
            Log.d(BLUETOOTH_TAG, "Self bluetooth hash: " + bluetoothController.getSelfHash());
            Log.d(BLUETOOTH_TAG, "Self bluetooth addr: " + bluetoothController.getSelfBluetoothMacAddress());

            app.getUserRepo().readUser(bluetoothController.getSelfHash(), user -> currentUser = user);
            bluetoothController.enableDeviceRequest();
            bluetoothController.discovery();
            // TODO : show chats
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "sign in done");
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "success google sign in");
                selectHobbies();
            } else {
                Log.w(TAG, "failure google sign in");
                // TODO next action?
            }
        }
    }

    private void selectHobbies() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, HobbyListFragment.newInstance())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        bluetoothController.setCallback(null);
    }

    @Override
    public void onFinished() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();

    }
}

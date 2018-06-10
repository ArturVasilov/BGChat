package ru.androidacademy.bgchat.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import ru.androidacademy.bgchat.App;
import ru.androidacademy.bgchat.R;
import ru.androidacademy.bgchat.bluetooth.BluetoothController;
import ru.androidacademy.bgchat.model.User;

import static ru.androidacademy.bgchat.bluetooth.BluetoothController.BLUETOOTH_TAG;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 3212;
    private static final String TAG = MainActivity.class.getSimpleName();

    private App app;

    private BluetoothController mBluetoothController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothController = new BluetoothController(false, this, new BluetoothController.Callback() {
            @Override
            public void discoveryFinishedCallback(List<String> DiscoveredDeviceList) {
                Log.d(BLUETOOTH_TAG, "in discoveryFinishedCallback");
                for (String element : DiscoveredDeviceList) {
                    Log.d(BLUETOOTH_TAG, ": " + element);
                }
            }

            @Override
            public void discoveryFoundedDeviceCallback(String deviceHash) {
                Log.d(BLUETOOTH_TAG, "FOUND DEVICE: " + deviceHash);
                app.getUserRepo().readUser(deviceHash, user -> {
                    if (user != null) {
                        Toast.makeText(MainActivity.this, "Founded: " + user.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        app = (App) getApplicationContext();
        if (app.getAuthRepo().getCurrentUser() == null) {
            startActivityForResult(app.getAuthRepo().getIntent(), RC_SIGN_IN);
        } else {
            Log.d(BLUETOOTH_TAG, "Self bluetooth hash: " + mBluetoothController.getSelfHash());

            mBluetoothController.enableDeviceRequest();
            mBluetoothController.discovery();
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
                loginPostProcess();
            } else {
                Log.w(TAG, "failure google sign in");
                // TODO next action?
            }
        }
    }

    private void loginPostProcess() {
        String id = mBluetoothController.getSelfHash();
        if (id == null) {
            // TODO what to do
            throw new RuntimeException("hash id is null");
        }
        FirebaseUser firebaseUser = app.getAuthRepo().getCurrentUser();
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Java");
        hobbies.add("Android");
        User user = new User(firebaseUser.getEmail(), id, firebaseUser.getDisplayName(), hobbies);
        app.getUserRepo().writeUser(user);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mBluetoothController.destroy();
    }
}
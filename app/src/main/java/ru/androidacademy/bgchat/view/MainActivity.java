package ru.androidacademy.bgchat.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import ru.androidacademy.bgchat.App;
import ru.androidacademy.bgchat.R;
import ru.androidacademy.bgchat.model.User;

public class MainActivity extends AppCompatActivity {
  private static final int RC_SIGN_IN = 3212;
  private static final String TAG = MainActivity.class.getSimpleName();

  private App app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    app = (App) getApplicationContext();
    startActivityForResult(app.getAuthRepo().getIntent(), RC_SIGN_IN);
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
    String id = "bluetooth id";
    FirebaseUser firebaseUser = app.getAuthRepo().getCurrentUser();
    List<String> hobbies = new ArrayList<>();
    hobbies.add("Java");
    hobbies.add("Android");
    User user = new User(firebaseUser.getEmail(), id, firebaseUser.getDisplayName(), hobbies);
    app.getUserRepo().writeIfNotExists(user);
  }
}

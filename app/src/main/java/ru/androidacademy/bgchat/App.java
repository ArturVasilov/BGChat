package ru.androidacademy.bgchat;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import ru.androidacademy.bgchat.services.AuthRepo;
import ru.androidacademy.bgchat.services.RoomRepo;
import ru.androidacademy.bgchat.services.UserRepo;

public class App extends Application {
    private AuthRepo authRepo;
    private UserRepo userRepo;
    private RoomRepo roomRepo;

    @Override
    public void onCreate() {
        super.onCreate();

        authRepo = new AuthRepo(FirebaseAuth.getInstance());
        userRepo = new UserRepo(FirebaseDatabase.getInstance());
        roomRepo = new RoomRepo(FirebaseDatabase.getInstance());

        roomRepo.setUserRepo(userRepo);

    }

    public AuthRepo getAuthRepo() {
        return authRepo;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

    public RoomRepo getRoomRepo() {
        return roomRepo;
    }
}

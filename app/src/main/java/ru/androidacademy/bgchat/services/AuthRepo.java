package ru.androidacademy.bgchat.services;

import android.content.Intent;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

public class AuthRepo {
    private static final String TAG = AuthRepo.class.getSimpleName();
    private final FirebaseAuth auth;

    public AuthRepo(FirebaseAuth auth) {
        this.auth = auth;
    }

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public Intent getIntent() {
        return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                        Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build()))
                .build();
    }
}

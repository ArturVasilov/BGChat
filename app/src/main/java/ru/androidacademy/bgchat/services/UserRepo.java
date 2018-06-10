package ru.androidacademy.bgchat.services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ru.androidacademy.bgchat.interfaces.Consumer;
import ru.androidacademy.bgchat.model.User;

public class UserRepo {
    private static final String REF_USERS = "users";
    private static final String TAG = UserRepo.class.getSimpleName();

    private final FirebaseDatabase db;

    public UserRepo(FirebaseDatabase firebaseDatabase) {
        db = firebaseDatabase;
    }

    public void writeUser(User user) {
        db.getReference()
                .child(REF_USERS)
                .child(user.getId())
                .setValue(user)
                .addOnSuccessListener(aVoid -> Log.i(TAG, "writeUser: success"))
                .addOnFailureListener(e -> {
                    Log.w(TAG, "wireUser: failure");
                    Log.w(TAG, e.getMessage());
                });
    }

    public void readUser(String id, Consumer<User> consumer) {
        db.getReference()
                .child(REF_USERS)
                .child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        consumer.accept(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    public void writeIfNotExists(User newUser) {
        readUser(newUser.getId(), user -> {
            if (user == null) {
                writeUser(newUser);
            }
        });
    }
}

package ru.androidacademy.bgchat.services;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.androidacademy.bgchat.interfaces.Consumer;
import ru.androidacademy.bgchat.model.Message;
import ru.androidacademy.bgchat.model.Room;
import ru.androidacademy.bgchat.model.User;

public class RoomRepo {
    private static final String REF_ROOMS = "rooms";
    private static final String REF_MESSAGES = "rooms";
    private static final String TAG = RoomRepo.class.getSimpleName();

    private final FirebaseDatabase db;

    private UserRepo userRepo;

    public RoomRepo(FirebaseDatabase firebaseDatabase) {
        db = firebaseDatabase;
    }

    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private void writeRoom(Room room) {
        db.getReference().child(REF_ROOMS).child(room.getId())
                .setValue(room)
                .addOnSuccessListener(aVoid -> Log.i(TAG, "writeRoom: success"))
                .addOnFailureListener(e -> {
                    Log.w(TAG, "writeRoom: failure");
                    Log.w(TAG, e.getMessage());
                });
    }

    public String createRoom(User user1, User user2) {
        String roomId = interceptRooms(user1.getRooms(), user2.getRooms());
        if (roomId != null) {
            return roomId;
        }
        String key = db.getReference().child(REF_ROOMS).push().getKey();
        Room room = new Room(key, user1.getId(), user2.getId());
        user1.addRoom(room.getId());
        user2.addRoom(room.getId());
        userRepo.writeUser(user1);
        userRepo.writeUser(user2);
        writeRoom(room);
        return room.getId();
    }

    public void readRoom(String id, Consumer<Room> consumer) {
        db.getReference()
                .child(REF_ROOMS)
                .child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Room user = dataSnapshot.getValue(Room.class);
                        consumer.accept(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    public void subscribeMessages(String room, Consumer<Message> consumer) {
        db.getReference().child(REF_ROOMS).child(room).child("messages").orderByKey()
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Message message = dataSnapshot.getValue(Message.class);
                        if (message != null) {
                            consumer.accept(message);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Message message = dataSnapshot.getValue(Message.class);
                        if (message != null) {
                            consumer.accept(message);
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void writeMessage(Room room, Message message) {
        String key = db.getReference().child(REF_ROOMS).child(room.getId()).child("messages").push().getKey();
        db.getReference().child(REF_ROOMS).child(room.getId()).child("messages").child(key).setValue(message)
                .addOnSuccessListener(aVoid -> Log.i(TAG, "writeMessage: success"))
                .addOnFailureListener(e -> {
                    Log.w(TAG, "writeMessage: failure");
                    Log.w(TAG, e.getMessage());
                });

    }

    public String interceptRooms(List<String> list1, List<String> list2) {
        Set<String> set = new HashSet<>(list2);
        for (String room : list1) {
            if (set.contains(room)) {
                return room;
            }
        }
        return null;
    }

}

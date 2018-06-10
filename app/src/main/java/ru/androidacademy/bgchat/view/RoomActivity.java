package ru.androidacademy.bgchat.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import ru.androidacademy.bgchat.App;
import ru.androidacademy.bgchat.R;
import ru.androidacademy.bgchat.model.Message;
import ru.androidacademy.bgchat.model.Room;
import ru.androidacademy.bgchat.model.User;
import ru.androidacademy.bgchat.view.adapters.ListMessageAdapter;

public class RoomActivity extends AppCompatActivity {
    private final static String TAG = RoomActivity.class.getSimpleName();
    private final static String ROOM_KEY = "room";
    private final static String USER_KEY = "user";
    private App app;
    private User currentUser;
    private Room currentRoom;

    public static void start(Activity activity, String room, String user) {
        Intent intent = new Intent(activity, RoomActivity.class);
        intent.putExtra(ROOM_KEY, room);
        intent.putExtra(USER_KEY, user);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        app = (App) getApplicationContext();

        configureRecyclerView();

        findViewById(R.id.send_message_button).setOnClickListener(view -> {
            TextView textView = findViewById(R.id.new_message_view);
            String text = textView.getText().toString();
            textView.setText("");
            Message message = new Message(null, text, currentUser.getId(), currentUser.getName());
            app.getRoomRepo().writeMessage(currentRoom, message);
        });
    }

    private void configureRecyclerView() {
        String room = getIntent().getStringExtra(ROOM_KEY);
        app.getRoomRepo().readRoom(room, r -> currentRoom = r);
        String user = getIntent().getStringExtra(USER_KEY);
        app.getUserRepo().readUser(user, u -> currentUser = u);

        RecyclerView recyclerView = findViewById(R.id.chat_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        ListMessageAdapter adapter = new ListMessageAdapter();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(RoomActivity.this);
                smoothScroller.setTargetPosition(adapter.getItemCount() - 1);
                layoutManager.startSmoothScroll(smoothScroller);
            }
        });
        recyclerView.setAdapter(adapter);
        app.getRoomRepo().subscribeMessages(room, adapter::pushMessage);
    }
}

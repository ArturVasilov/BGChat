package ru.androidacademy.bgchat.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView.Adapter;
import ru.androidacademy.bgchat.R;
import ru.androidacademy.bgchat.views.HobieTextView;

/**
 * Created by User on 11.06.2018.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ItemHolder> {

    //List<Chat> chatList = new ArrayList<>();
    private List<Chat> chatList;
    private OnItemClickListener onItemClickListener;

    private final View.OnClickListener internalClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Chat chat = (Chat) view.getTag();
            int position = chatList.indexOf(chat);
            onItemClickListener.onClick(chat, position);
        }
    };

    public ChatsAdapter(List<Chat> chatList, OnItemClickListener onItemClickListener) {
        this.chatList = chatList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_chat, parent, false);
        return new ItemHolder(itemView);
    }

    public void addChat(Chat chat) {
        chatList.add(chat);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.itemView.setTag(chat);
        //TODO: add loading avatar via Picasso
        holder.userNameTextView.setText(chat.getUserName());
        holder.firstHobbyTextView.setText(chat.getFirstHobby());
        holder.secondHobbyTextView.setText(chat.getSecondHobby());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public interface OnItemClickListener {
        public void onClick(Chat chat, int position);
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public HobieTextView firstHobbyTextView;
        public HobieTextView secondHobbyTextView;
        public TextView userNameTextView;
        public ImageView avatarImageView;

        public ItemHolder(View itemView) {
            super(itemView);
            firstHobbyTextView = itemView.findViewById(R.id.hobbyFirstTextView);
            secondHobbyTextView = itemView.findViewById(R.id.hobbySecondTextView);
            userNameTextView = itemView.findViewById(R.id.nameTextView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            itemView.setOnClickListener(internalClickListener);
        }

    }

}

package ru.androidacademy.bgchat.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.androidacademy.bgchat.R;
import ru.androidacademy.bgchat.model.Message;

public class ListMessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    private final List<Message> messages;

    public ListMessageAdapter() {
        this.messages = new ArrayList<>();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.applyData(message.getAuthorName(), message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void pushMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size());
    }

}

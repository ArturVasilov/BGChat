package ru.androidacademy.bgchat.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.androidacademy.bgchat.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    private final TextView author;
    private final TextView message;

    public MessageViewHolder(View itemView) {
        super(itemView);
        author = itemView.findViewById(R.id.author_view);
        message = itemView.findViewById(R.id.message_view);
    }

    public void applyData(String author, String message) {
        this.author.setText(author);
        this.message.setText(message);
    }
}

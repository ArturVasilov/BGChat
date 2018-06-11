package ru.androidacademy.bgchat.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import ru.androidacademy.bgchat.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    private final TextView message;

    public MessageViewHolder(View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.message_view);
    }

    public void applyData(String message, boolean sender) {
        this.message.setText(message);
        if (sender) {
//            this.message.setGravity(Gravity.RIGHT);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.message.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            this.message.setBackground(itemView.getContext().getDrawable(R.drawable.message_sender));
        } else {
//            this.message.setGravity(Gravity.LEFT);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.message.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            this.message.setBackground(itemView.getContext().getDrawable(R.drawable.message_receiver));
        }
    }
}

package ru.androidacademy.bgchat.view;

import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

import ru.androidacademy.bgchat.R;
import ru.androidacademy.bgchat.model.User;

/**
 * Created by User on 11.06.2018.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ItemHolder> {

    private final List<User> chatList;
    private final OnItemClickListener onItemClickListener;

    private final View.OnClickListener internalClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            User user = (User) view.getTag();
            int position = chatList.indexOf(user);
            onItemClickListener.onClick(user, position);
        }
    };

    ChatsAdapter(OnItemClickListener onItemClickListener) {
        this.chatList = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_chat, parent, false);
        return new ItemHolder(itemView);
    }

    public void addChat(User chat) {
        if (!chatList.contains(chat)) {
            chatList.add(chat);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        User chat = chatList.get(position);
        holder.itemView.setTag(chat);
        holder.userNameTextView.setText(chat.getName());
        List<String> hobbies = chat.getHobbies();
        if (hobbies.size() > 0 && !TextUtils.isEmpty(chat.getHobbies().get(0))) {
            holder.hobby1.setText(chat.getHobbies().get(0));
            holder.hobby1.setVisibility(View.VISIBLE);
        }
        if (hobbies.size() > 1 && !TextUtils.isEmpty(chat.getHobbies().get(1))) {
            holder.hobby2.setText(chat.getHobbies().get(1));
            holder.hobby2.setVisibility(View.VISIBLE);
        } else {
            holder.hobby2.setVisibility(View.GONE);
        }
        if (hobbies.size() > 2 && !TextUtils.isEmpty(chat.getHobbies().get(2))) {
            holder.hobby3.setText(chat.getHobbies().get(2));
            holder.hobby3.setVisibility(View.VISIBLE);
        } else {
            holder.hobby3.setVisibility(View.GONE);
        }
        if (hobbies.size() > 3 && !TextUtils.isEmpty(chat.getHobbies().get(3))) {
            holder.hobby4.setText(chat.getHobbies().get(3));
            holder.hobby4.setVisibility(View.VISIBLE);
        } else {
            holder.hobby4.setVisibility(View.GONE);
        }
        if (hobbies.size() > 4 && !TextUtils.isEmpty(chat.getHobbies().get(4))) {
            holder.hobby5.setText(chat.getHobbies().get(4));
            holder.hobby5.setVisibility(View.VISIBLE);
        } else {
            holder.hobby5.setVisibility(View.GONE);
        }

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(chat.getName().charAt(0)), randomColor());
        holder.avatarImageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public interface OnItemClickListener {
        void onClick(User chat, int position);
    }

    private static int randomColor() {
        float[] hsl = new float[]{0, 0, 0};
        hsl[0] = (float) (Math.random() * 360);
        hsl[1] = (float) (40 + (Math.random() * 60));
        hsl[2] = (float) (40 + (Math.random() * 60));
        return ColorUtils.HSLToColor(hsl);
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private final TextView userNameTextView;
        private final ImageView avatarImageView;

        private final TextView hobby1;
        private final TextView hobby2;
        private final TextView hobby3;
        private final TextView hobby4;
        private final TextView hobby5;

        ItemHolder(View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.nameTextView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            itemView.setOnClickListener(internalClickListener);

            hobby1 = itemView.findViewById(R.id.hobby1);
            hobby2 = itemView.findViewById(R.id.hobby2);
            hobby3 = itemView.findViewById(R.id.hobby3);
            hobby4 = itemView.findViewById(R.id.hobby4);
            hobby5 = itemView.findViewById(R.id.hobby5);
        }
    }

}

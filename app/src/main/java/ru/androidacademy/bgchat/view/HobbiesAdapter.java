package ru.androidacademy.bgchat.view;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import ru.androidacademy.bgchat.R;

/**
 * Created by User on 10.06.2018.
 */

public class HobbiesAdapter extends RecyclerView.Adapter<HobbiesAdapter.ItemHolder> {


    private List<String> hobbyList;
    private OnItemClickListener onItemClickListener;

    private final View.OnClickListener internalClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String hobby = (String) view.getTag();
            int position = hobbyList.indexOf(hobby);
            onItemClickListener.onClick(hobby, position);
        }
    };

    public HobbiesAdapter(OnItemClickListener onItemClickListener, List<String> hobbyList) {
        this.onItemClickListener = onItemClickListener;
        this.hobbyList = hobbyList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_hobby, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        String hobby = hobbyList.get(position);
        holder.itemView.setTag(hobby);
        holder.button.setText(hobby);
    }

    @Override
    public int getItemCount() {
        return hobbyList.size();
    }

    public interface OnItemClickListener {
        public void onClick(String hobby, int position);
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public Button button;

        public ItemHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.hobbyButton);
            button.setOnClickListener(internalClickListener);
        }

    }

}

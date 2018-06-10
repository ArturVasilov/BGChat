package ru.androidacademy.bgchat.view;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.androidacademy.bgchat.R;
import ru.androidacademy.bgchat.views.HobieTextView;

/**
 * Created by User on 10.06.2018.
 */

public class HobbiesAdapter extends RecyclerView.Adapter<HobbiesAdapter.ItemHolder> {


    private List<String> hobbyList;
    private List<String> selectedHobbyList;

    public HobbiesAdapter(List<String> hobbyList) {
        this.hobbyList = hobbyList;
        selectedHobbyList = new ArrayList<>();
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
        holder.textView.setText(hobby);
    }

    public List<String> getSelectedHobbyList() {
        return selectedHobbyList;
    }

    @Override
    public int getItemCount() {
        return hobbyList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public HobieTextView textView;

        public ItemHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.hobbyButton);
            textView.setOnSelectionChangedListener((view, isSelected) -> {
                String hobby = textView.getText().toString();
                if (isSelected) {
                    selectedHobbyList.add(hobby);
                } else {
                    selectedHobbyList.remove(hobby);
                }
            });
        }

    }

}

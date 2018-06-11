package ru.androidacademy.bgchat.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import ru.androidacademy.bgchat.App;
import ru.androidacademy.bgchat.R;
import ru.androidacademy.bgchat.model.User;

public class HobbyListFragment extends Fragment {

    private HobbiesCallback callback;

    @NonNull
    public static HobbyListFragment newInstance() {
        return new HobbyListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HobbiesCallback) {
            callback = (HobbiesCallback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_hobby_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.hobbyRecyclerView);

        RecyclerView.LayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        HobbiesAdapter hobbiesAdapter = new HobbiesAdapter(getHobbyList());
        recyclerView.setAdapter(hobbiesAdapter);

        FloatingActionButton acceptFab = rootView.findViewById(R.id.hobbyAcceptFab);
        acceptFab.setOnClickListener(v -> {
            App app = (App) getActivity().getApplicationContext();
            String id = app.getBluetoothController().getSelfHash();
            FirebaseUser firebaseUser = app.getAuthRepo().getCurrentUser();
            User user = new User(firebaseUser.getEmail(), id, firebaseUser.getDisplayName());
            user.setHobbies(new ArrayList<>(hobbiesAdapter.getSelectedHobbyList()));
            app.getUserRepo().writeUser(user);
            callback.onFinished();
        });

        return rootView;
    }

    public interface HobbiesCallback {
        void onFinished();
    }

    @NonNull
    public static List<String> getHobbyList() {
        List<String> hobbyList = new ArrayList<>();
        hobbyList.add("Kotlin");
        hobbyList.add("Dota");
        hobbyList.add("Verilog");
        hobbyList.add("Drinking");
        hobbyList.add("Tequila with limon");
        hobbyList.add("Girls");
        hobbyList.add("Cycling");
        hobbyList.add("Fishing");
        hobbyList.add("Travelling");
        hobbyList.add("Android Meetups");
        hobbyList.add("Football");
        hobbyList.add("Embedded development");
        hobbyList.add("Books");
        hobbyList.add("Learning foreign languages");
        hobbyList.add("Machine Learning");
        hobbyList.add("Auto");
        hobbyList.add("Films and serials");
        hobbyList.add("Rock music");
        hobbyList.add("Folk music");
        hobbyList.add("Rocket science");
        hobbyList.add("Software architecture development");
        hobbyList.add("Cuban cigars");
        hobbyList.add("Cooking");
        hobbyList.add("Does something shit");
        hobbyList.add("Sport betting");
        hobbyList.add("Sleeping");
        hobbyList.add("Kitties");
        hobbyList.add("Motobikes");

        return hobbyList;
    }
}

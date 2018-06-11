package ru.androidacademy.bgchat.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import ru.androidacademy.bgchat.App;
import ru.androidacademy.bgchat.R;
import ru.androidacademy.bgchat.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HobbyListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HobbyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HobbyListFragment extends Fragment {

    //private final static String ARG_HOBBY_LIST = "args:hobby_list";

    private final static String HOBBY_TAG = "HobbyListFragment";

    private RecyclerView recyclerView;
    private FloatingActionButton acceptFab;

    private OnFragmentInteractionListener mListener;

    private HobbiesCallback callback;

    public static HobbyListFragment newInstance() {
        return new HobbyListFragment();
    }

    public HobbyListFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_hobby_list, container, false);

        recyclerView = rootView.findViewById(R.id.hobbyRecyclerView);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        HobbiesAdapter hobbiesAdapter = new HobbiesAdapter(getHobbyList());
        recyclerView.setAdapter(hobbiesAdapter);

        acceptFab = rootView.findViewById(R.id.hobbyAcceptFab);
        acceptFab.setOnClickListener(v -> {
            App app = (App) getActivity().getApplicationContext();
            List<String> list = hobbiesAdapter.getSelectedHobbyList();
            String id = app.getBluetoothController().getSelfHash();
            if (id == null) {
                // TODO what to do
                throw new RuntimeException("hash id is null");
            }
            FirebaseUser firebaseUser = app.getAuthRepo().getCurrentUser();
            User user = new User(firebaseUser.getEmail(), id, firebaseUser.getDisplayName());
            user.setHobbies(new ArrayList<>(list));
            app.getUserRepo().writeUser(user);
            callback.onFinished();
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface HobbiesCallback {
        void onFinished();
    }

    public List<String> getHobbyList() {
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

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

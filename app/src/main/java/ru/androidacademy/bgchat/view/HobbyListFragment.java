package ru.androidacademy.bgchat.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.androidacademy.bgchat.R;

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

    public HobbyListFragment() {
        // Required empty public constructor
    }

    public static HobbyListFragment newInstance() {
        HobbyListFragment fragment = new HobbyListFragment();
        /*Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_HOBBY_LIST, hobbyList);
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        acceptFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Accept!", Toast.LENGTH_SHORT).show();
                for (String hobby:
                     hobbiesAdapter.getSelectedHobbyList()) {
                    Log.d(HOBBY_TAG, hobby);
                }
            }
        });

        return rootView;

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
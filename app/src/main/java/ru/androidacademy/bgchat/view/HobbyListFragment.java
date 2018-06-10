package ru.androidacademy.bgchat.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

    private RecyclerView recyclerView;

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

        HobbiesAdapter hobbiesAdapter = new HobbiesAdapter(new HobbiesAdapter.OnItemClickListener() {
            @Override
            public void onClick(String hobby, int position) {
                Toast.makeText(getContext(), hobby, Toast.LENGTH_SHORT);
            }
        }, getHobbyList());
        recyclerView.setAdapter(hobbiesAdapter);

        return rootView;

    }

    public List<String> getHobbyList() {
        List<String> hobbyList = new ArrayList<>();
        hobbyList.add("Kotlin");
        hobbyList.add("Dota");
        hobbyList.add("Verilog");
        hobbyList.add("Drinking");
        hobbyList.add("sdjkfjkdsfhdhfdhfhdghfdgfgdgfdghfgjdgfj");
        hobbyList.add("kkjkjjfgjjfjdfjkdjkgkdkgktiguugkdjgkjdjkrk");
        hobbyList.add("hsdsdhdhdhjsjsjdhjsjsjdhdhsjsjdhsjdjjdjs");
        hobbyList.add("lkskeiekdkjkdjkdjksdkjsdjsdjsdjsdjjsdkjks");
        hobbyList.add("jfjksfhshfiieieoioioieoidfhfhskjfkjeuifkewu");

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

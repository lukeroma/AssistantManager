package com.example.luke.assistantmanager.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.luke.assistantmanager.AddPlayerActivity;
import com.example.luke.assistantmanager.EditPlayerActivity;
import com.example.luke.assistantmanager.PlayersDB;
import com.example.luke.assistantmanager.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TAG = "ListDataActivity";

    PlayersDB mPlayerDB;
    private ListView mPlayerList;
    private ListView mPositionList;
    private ListView mGoalsList;

    private OnFragmentInteractionListener mListener;

    public PlayersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayersFragment newInstance(String param1, String param2) {
        PlayersFragment fragment = new PlayersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mPlayerDB.getPlayer();
        ArrayList<String> nameData = new ArrayList<>();
        ArrayList<String> positionData = new ArrayList<>();
        ArrayList<String> goalData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database
            //then add it to the ArrayList
            nameData.add(data.getString(1));
            positionData.add(data.getString(2));
            goalData.add(data.getString(3));
        }
        //create the list adapter and set the adapter
        ListAdapter nameAdapter = new ArrayAdapter<>(getActivity(),R.layout.custom_text, nameData);
        ListAdapter positionAdapter = new ArrayAdapter<>(getActivity(),R.layout.custom_text, positionData);
        ListAdapter goalAdapter = new ArrayAdapter<>(getActivity(),R.layout.custom_text, goalData);
        mPlayerList.setAdapter(nameAdapter);
        mPositionList.setAdapter(positionAdapter);
        mGoalsList.setAdapter(goalAdapter);

        //set an onItemClickListener to the ListView
        mPlayerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                String position = null;
                String goals = null;
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor nameData = mPlayerDB.getItemID(name); //get the id associated with that name
                int itemID = -1;
                while(nameData.moveToNext()){
                    itemID = nameData.getInt(0);

                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(getActivity(), EditPlayerActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    editScreenIntent.putExtra("position",position);
                    editScreenIntent.putExtra("goals",goals);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }
    public void onAddPlayerSelected(View view) {
        // react to the menu item being selected...
        startActivity(new Intent(getActivity(), AddPlayerActivity.class));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_players, container, false);
        mPlayerList = (ListView) mview.findViewById(R.id.viewPlayersList);
        mPositionList = (ListView) mview.findViewById(R.id.viewPositionsList);
        mGoalsList = (ListView) mview.findViewById(R.id.viewGoalsList);
        mPlayerDB = new PlayersDB(getActivity());
        populateListView();
        return mview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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

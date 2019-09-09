package com.example.aleksei.myrun;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment implements AdapterView.OnItemSelectedListener{

    Button button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * onCreate called before onCreateView
     * This is where non-graphical code goes
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    /**
     * onCreateView called after onCreate
     * This is where graphical code goes
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        //Calculating the Time/Distance/Pace
        button = (Button) view.findViewById(R.id.tab1_button);

        //Time
        final TextView hours = (TextView) view.findViewById(R.id.tab1_hours);
        final TextView minutes = (TextView) view.findViewById(R.id.tab1_minutes);
        final TextView seconds = (TextView) view.findViewById(R.id.tab1_seconds);

        //Distance
        final TextView distance = (TextView) view.findViewById(R.id.tab1_distance);
        final Spinner typeDistance = (Spinner) view.findViewById(R.id.tab1_distance_spinner);

        //Pace
        final TextView paceHours = (TextView) view.findViewById(R.id.tab1_paceHours);
        final TextView paceMinutes = (TextView) view.findViewById(R.id.tab1_paceMinutes);
        final TextView paceSeconds = (TextView) view.findViewById(R.id.tab1_paceSeconds);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if the user filled out only 2 of the rows.
                if (validDataEntries(hours, minutes, seconds, distance, paceHours, paceMinutes, paceSeconds, typeDistance)) {
                    Toast.makeText(getActivity(), paceHours.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;

    }

    public static boolean validDataEntries(TextView hours, TextView minutes, TextView seconds, TextView distance,
                                           TextView paceHours, TextView paceMinutes, TextView paceSeconds, Spinner typeDistance) {

        boolean timeRowFilled = false;
        boolean distanceRowFilled = false;
        boolean paceRowFilled = false;

        //timeRow is an array that holds 3 elements (strings) from time: hours, minutes, and seconds.
        //if any of the TextView's do not have user input the string will be an empty string: "".
        String[] timeRow = new String[] {hours.getText().toString(), minutes.getText().toString(), seconds.getText().toString()};

        //Check if timeRow is filled

        if (!timeRow[0].equals("") || !timeRow[1].equals("") || !timeRow[2].equals("")) {
            timeRowFilled = true;
        }

        //Distance, unit of measurment
        String[] distanceRow = new String[] {distance.getText().toString(), typeDistance.getSelectedItem().toString()};

        //Check if distanceRow is filled

        if (!distanceRow[0].equals("")) {
            distanceRowFilled = true;
        }

        //Hours, Minutes, Seconds
        String[] paceRow = new String[] {paceHours.getText().toString(), paceMinutes.getText().toString(), paceSeconds.getText().toString()};

        //Check if paceRow is filled
        if (!paceRow[0].equals("") || paceRow[1].equals("") || !paceRow[2].equals("")) {
            paceRowFilled = true;
        }

        //stub
        return true;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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

    /**
     * Method from Spinner
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    /**
     * Method from Spinner
     * @param adapterView
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

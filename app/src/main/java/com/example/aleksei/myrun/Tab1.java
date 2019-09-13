package com.example.aleksei.myrun;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


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
        final Spinner paceSpinner = (Spinner) view.findViewById(R.id.tab1_pace_spinner);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if the user filled out only 2 of the rows.
                if (validDataEntries(hours, minutes, seconds, distance, paceHours, paceMinutes, paceSeconds, typeDistance, paceSpinner)) {

                    //Knowing that the data is properly formatted lets figure out what column we are calculating

                    //timeRow is an array that holds 3 elements (strings) from time: hours, minutes, and seconds.
                    String[] timeRow = new String[] {hours.getText().toString(), minutes.getText().toString(), seconds.getText().toString()};

                    //Distance, unit of measurment
                    String[] distanceRow = new String[] {distance.getText().toString().trim(), typeDistance.getSelectedItem().toString()};

                    //Hours, Minutes, Seconds
                    String[] paceRow = new String[] {paceHours.getText().toString(), paceMinutes.getText().toString(), paceSeconds.getText().toString(), paceSpinner.getSelectedItem().toString()};

                    /*   Variable Template

                    //Time Variables
                    int timeHours = Integer.parseInt(timeRow[0]);
                    int timeMinutes = Integer.parseInt(timeRow[1]);
                    int timeSeconds = Integer.parseInt(timeRow[2]);

                    //Distance Variables
                    int distanceDistance = Integer.parseInt(distanceRow[0]);
                    String distanceUnits = distanceRow[1];

                    //Pace Variables
                    int paceHours = Integer.parseInt(paceRow[0]);
                    int paceMinutes = Integer.parseInt(paceRow[1]);
                    int paceSeconds = Integer.parseInt(paceRow[2]);
                    String paceUnits = paceRow[3];
                    */

                    //Calculating Time (convert distance units into pace units * pace in total seconds)
                    if (timeRow[0].equals("") && timeRow[1].equals("") && timeRow[2].equals("")) {

                        DecimalFormat decimalFormat = new DecimalFormat("#.#");


                        for (int i = 0; i < paceRow.length; i++) {
                            if (paceRow[i].equals("")) {
                                paceRow[i] = "0";
                                if (i == 0) {
                                    paceHours.setText("0");
                                }

                                else if (i == 1) {
                                    paceMinutes.setText("0");
                                }

                                else {
                                    paceSeconds.setText("0");
                                }
                            }
                        }

                        //Set up variables for calculating time
                        int distanceDistance = Integer.parseInt(distanceRow[0]);
                        String distanceUnits = distanceRow[1];
                        int paceHours = Integer.parseInt(paceRow[0]);
                        int paceMinutes = Integer.parseInt(paceRow[1]);
                        int paceSeconds = Integer.parseInt(paceRow[2]);
                        String paceUnits = paceRow[3];

                        //totalTimePace is in seconds
                        int totalTimePace = (paceHours * 3600) + (paceMinutes * 60) + (paceSeconds);

                        //distance will be converted to miles
                        if (paceUnits.equals("Mile")) {

                            if (distanceUnits.equals("Miles")) {

                                double totalTime = distanceDistance * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            //Convert distance from km to miles
                            if (distanceUnits.equals("Kilometers")) {

                                double totalTime = (distanceDistance / 1.609)  * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Meters")) {

                                double totalTime = (distanceDistance / 1609.344) * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Yards")) {

                                double totalTime = (distanceDistance / 1760)* totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }


                        }

                        //Distance will be converted to Km
                        if (paceUnits.equals("Kilometer")) {

                            if (distanceUnits.equals("Miles")) {

                                double totalTime = (distanceDistance * 1.609) * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            //Convert distance from km to miles
                            if (distanceUnits.equals("Kilometers")) {

                                double totalTime = distanceDistance * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Meters")) {

                                double totalTime = (distanceDistance / 1000) * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Yards")) {

                                double totalTime = (distanceDistance / 1093.613) * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }


                        }

                        //Calculating Time
                        if (paceUnits.equals("Meter")) {

                            if (distanceUnits.equals("Miles")) {

                                double totalTime = (distanceDistance * 1609.344) * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Kilometers")) {

                                double totalTime = (distanceDistance * 1000)  * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Meters")) {

                                double totalTime = distanceDistance * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Yards")) {

                                double totalTime = (distanceDistance / 1.094)* totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }


                        }

                        //Calculating Time
                        if (paceUnits.equals("Yard")) {

                            if (distanceUnits.equals("Miles")) {

                                double totalTime = (distanceDistance * 1760)* totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            //Convert distance from km to miles
                            if (distanceUnits.equals("Kilometers")) {

                                double totalTime = (distanceDistance * 1093.613)  * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Meters")) {

                                double totalTime = (distanceDistance * 1.094) * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Yards")) {

                                double totalTime = distanceDistance * totalTimePace;

                                double totalSeconds =  totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                totalSeconds = Double.parseDouble(decimalFormat.format(totalSeconds));

                                hours.setText(Integer.toString(totalHours));
                                minutes.setText(Integer.toString(totalMinutes));
                                seconds.setText(Double.toString(totalSeconds));

                            }


                        }



                    }

                    //Calculating Distance
                    else if (distanceRow[0].equals("")) {

                        DecimalFormat decimalFormat = new DecimalFormat("#.00");

                        //Iterate through time and pace array and set blank elements to 0

                        for (int i = 0; i < timeRow.length; i++) {
                            if (timeRow[i].equals("")) {
                                timeRow[i] = "0";
                                if (i == 0) {
                                    hours.setText("0");
                                }

                                else if (i == 1) {
                                    minutes.setText("0");
                                }

                                else {
                                    seconds.setText("0");
                                }
                            }
                        }

                        for (int i = 0; i < paceRow.length; i++) {
                            if (paceRow[i].equals("")) {
                                paceRow[i] = "0";
                                if (i == 0) {
                                    paceHours.setText("0");
                                }

                                else if (i == 1) {
                                    paceMinutes.setText("0");
                                }

                                else {
                                    paceSeconds.setText("0");
                                }
                            }
                        }

                        //Setup variables needed to calculate distance
                        int timeHours = Integer.parseInt(timeRow[0]);
                        int timeMinutes = Integer.parseInt(timeRow[1]);
                        int timeSeconds = Integer.parseInt(timeRow[2]);
                        int paceHours = Integer.parseInt(paceRow[0]);
                        int paceMinutes = Integer.parseInt(paceRow[1]);
                        int paceSeconds = Integer.parseInt(paceRow[2]);
                        String distanceUnits = distanceRow[1];
                        String paceUnits = paceRow[3];

                        //totalTime is in seconds
                        double totalTime = (timeHours * 3600) + (timeMinutes * 60) + (timeSeconds);

                        //totalTimePace is in seconds
                        double totalTimePace = (paceHours * 3600) + (paceMinutes * 60) + (paceSeconds);

                        //totalDistance is in the units paceUnits is in.
                        double totalDistance = totalTime/totalTimePace;

                        if (paceUnits.equals("Mile")) {

                            //totalDistance is in Miles

                            if (distanceUnits.equals("Miles")) {

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            else if (distanceUnits.equals("Kilometers")) {

                                totalDistance = totalDistance * 1.609;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            else if (distanceUnits.equals("Meters")) {

                                totalDistance = totalDistance * 1609.344;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            //Units must be in Yards
                            else {

                                totalDistance = totalDistance * 1760;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }



                        }

                        else if(paceUnits.equals("Kilometer")) {

                            //totalDistance is in Kilometers

                            //Check the units of distance
                            if (distanceUnits.equals("Miles")) {

                                totalDistance = totalDistance / 1.609;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            else if (distanceUnits.equals("Kilometers")) {

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            else if (distanceUnits.equals("Meters")) {

                                totalDistance = totalDistance * 1000;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            //Units must be in Yards
                            else {

                                totalDistance = totalDistance * 1093.613;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                        }

                        else if(paceUnits.equals("Meter")) {

                            //totalDistance is in Meters

                            //Check the units of distance
                            if (distanceUnits.equals("Miles")) {

                                totalDistance = totalDistance / 1609.344;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            else if (distanceUnits.equals("Kilometers")) {

                                totalDistance = totalDistance / 1000;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            else if (distanceUnits.equals("Meters")) {

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            //Units must be in Yards
                            else {

                                totalDistance = totalDistance * 1.094;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                        }

                        else if(paceUnits.equals("Yard")) {

                            //totalDistance is in Yards

                            //Check the units of distance
                            if (distanceUnits.equals("Miles")) {

                                totalDistance = totalDistance / 1760;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            else if (distanceUnits.equals("Kilometers")) {

                                totalDistance = totalDistance / 1093.613;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            else if (distanceUnits.equals("Meters")) {

                                totalDistance = totalDistance / 1.094;

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                            //Units must be in Yards
                            else {

                                totalDistance = Double.parseDouble(decimalFormat.format(totalDistance));

                                distance.setText(Double.toString(totalDistance));

                            }

                        }

                    }





                    /*   Variable Template

                    //Time Variables
                    int timeHours = Integer.parseInt(timeRow[0]);
                    int timeMinutes = Integer.parseInt(timeRow[1]);
                    int timeSeconds = Integer.parseInt(timeRow[2]);

                    //Distance Variables
                    int distanceDistance = Integer.parseInt(distanceRow[0]);
                    String distanceUnits = distanceRow[1];

                    //Pace Variables
                    int paceHours = Integer.parseInt(paceRow[0]);
                    int paceMinutes = Integer.parseInt(paceRow[1]);
                    int paceSeconds = Integer.parseInt(paceRow[2]);
                    String paceUnits = paceRow[3];
                    */



                    //Calculating Pace
                    else {

                        for (int i = 0; i < timeRow.length; i++) {
                            if (timeRow[i].equals("")) {
                                timeRow[i] = "0";
                                if (i == 0) {
                                    hours.setText("0");
                                }

                                else if (i == 1) {
                                    minutes.setText("0");
                                }

                                else {
                                    seconds.setText("0");
                                }
                            }
                        }

                        //Setup variables needed for pace computation
                        int timeHours = Integer.parseInt(timeRow[0]);
                        int timeMinutes = Integer.parseInt(timeRow[1]);
                        int timeSeconds = Integer.parseInt(timeRow[2]);
                        int distanceDistance = Integer.parseInt(distanceRow[0]);
                        String distanceUnits = distanceRow[1];
                        String paceUnits = paceRow[3];

                        //totalTime is in seconds
                        double totalTime = (timeHours * 3600) + (timeMinutes * 60) + (timeSeconds);

                        //Convert all distanceDistance into miles
                        if (paceUnits.equals("Mile")) {

                            //We dont need to change distance if both units is miles
                            if (distanceUnits.equals("Miles")) {

                                totalTime = totalTime / distanceDistance;

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Kilometers")) {

                                totalTime = totalTime / (distanceDistance / 1.609);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Meters")) {

                                totalTime = totalTime / (distanceDistance / 1609.344);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Yards")) {

                                totalTime = totalTime / (distanceDistance / 1760);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                        }

                        //PACE IS IN KILOMETERS
                        if (paceUnits.equals("Kilometer")) {

                            //We dont need to change distance if both units is miles
                            if (distanceUnits.equals("Miles")) {

                                totalTime = totalTime / distanceDistance;

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Kilometers")) {

                                totalTime = totalTime / (distanceDistance / 1.609);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Meters")) {

                                totalTime = totalTime / (distanceDistance / 1609.344);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Yards")) {

                                totalTime = totalTime / (distanceDistance / 1760);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                        }

                        //PACE IS IN METERS
                        if (paceUnits.equals("Meter")) {

                            //We dont need to change distance if both units is miles
                            if (distanceUnits.equals("Miles")) {

                                totalTime = totalTime / distanceDistance;

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Kilometers")) {

                                totalTime = totalTime / (distanceDistance / 1.609);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Meters")) {

                                totalTime = totalTime / (distanceDistance / 1609.344);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Yards")) {

                                totalTime = totalTime / (distanceDistance / 1760);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                        }

                        //PACE IS IN YARDS
                        if (paceUnits.equals("Yard")) {

                            //We dont need to change distance if both units is miles
                            if (distanceUnits.equals("Miles")) {

                                totalTime = totalTime / distanceDistance;

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Kilometers")) {

                                totalTime = totalTime / (distanceDistance / 1.609);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Meters")) {

                                totalTime = totalTime / (distanceDistance / 1609.344);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                            if (distanceUnits.equals("Yards")) {

                                totalTime = totalTime / (distanceDistance / 1760);

                                int totalSeconds =  (int)totalTime % 60;
                                int totalHours = (int)totalTime / 60;
                                int totalMinutes = totalHours % 60;
                                totalHours = (totalHours / 60);

                                paceHours.setText(Integer.toString(totalHours));
                                paceMinutes.setText(Integer.toString(totalMinutes));
                                paceSeconds.setText(Integer.toString(totalSeconds));

                            }

                        }

                    }










                } else {
                    Toast.makeText(getActivity(), "Only enter data to fill two rows!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;

    }

    public static boolean validDataEntries(TextView hours, TextView minutes, TextView seconds, TextView distance,
                                           TextView paceHours, TextView paceMinutes, TextView paceSeconds, Spinner typeDistance, Spinner paceSpinner) {

        boolean timeRowFilled = false;
        boolean distanceRowFilled = false;
        boolean paceRowFilled = false;

        //timeRow is an array that holds 3 elements (strings) from time: hours, minutes, and seconds.
        //if any of the TextView's do not have user input the string will be an empty string: "".
        String[] timeRow = new String[] {hours.getText().toString(), minutes.getText().toString(), seconds.getText().toString()};

        //Check if timeRow is filled

        if (!timeRow[0].equals("") || !timeRow[1].equals("") || !timeRow[2].equals("")) {

            Log.d("TIMEROW IS FILLED", "TIMEROW IS FILLED");
            timeRowFilled = true;
        }

        //Distance, unit of measurment
        String[] distanceRow = new String[] {distance.getText().toString().trim()};

        //Check if distanceRow is filled

        if (!distanceRow[0].equals("")) {

            Log.d("WHAT IS INSIDE DISTANCE", distanceRow[0]);
            distanceRowFilled = true;
        }

        //Hours, Minutes, Seconds
        String[] paceRow = new String[] {paceHours.getText().toString(), paceMinutes.getText().toString(), paceSeconds.getText().toString()};

        //Check if paceRow is filled
        if (!paceRow[0].equals("") || !paceRow[1].equals("") || !paceRow[2].equals("")) {

            Log.d("PACEROW IS FILLED", "PACEROW IS FILLED");
            paceRowFilled = true;
        }

        //Check the booleans "Filled" to see if a valid amount of data has been entered by the user.

        //Check if no row is filled out
        if (timeRowFilled == false && distanceRowFilled == false && paceRowFilled == false) {
            return false;
        }

        //Check if all rows are filled out
        else if (timeRowFilled && distanceRowFilled && paceRowFilled) {
            return false;
        }

        //Check if only 1 is filled out
        else if (timeRowFilled && !distanceRowFilled && !paceRowFilled) {
            return false;
        }

        else if (!timeRowFilled && distanceRowFilled && !paceRowFilled) {
            return false;
        }

        else if (!timeRowFilled && !distanceRowFilled && paceRowFilled) {
            return false;
        }

        else {

            //There are only 2 rows filled out so return true
            return true;
        }

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

package edu.fsu.cs.mobile.hw5.project2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MyDialogFragment extends DialogFragment{


    public static final String TAG = MyDialogFragment.class.getCanonicalName();
    public final static int REQUEST_CODE = 0;

    public final static String EVENT = "event";
    public final static String PLACE = "place";
    public final static String DATE = "date";
    public final static String TIME = "time";
    public final static String HOUSE = "house";
    private EditText EventName;
    private EditText EventLocation;
    private EditText date;
    private EditText time;
    private Button submit;
    private String house;
    //private int REQUEST_CODE;

    Spinner housespinner;

    public MyDialogFragment() {
        // Required empty public constructor
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_dialog, null);
        EventName = rootView.findViewById(R.id.NameEvent);
        EventLocation = rootView.findViewById(R.id.enterLocation);
        time = rootView.findViewById(R.id.inputTime);
        date = rootView.findViewById(R.id.inputDate);
        submit = rootView.findViewById(R.id.submit);
        //REQUEST_CODE = getParentFragment().REQUEST_CODE;
        //spinner
        housespinner = rootView.findViewById(R.id.HousesList);
        String[] housesInArray = getResources().getStringArray(R.array.housesarray);
        builder.setView(rootView);
        builder.setTitle("Create Event");
        builder.setMessage("Enter event name:");
        //builder.setPositiveButton("Create", mClickListener);


        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                if(checkInput(EventName.getText().toString(), EventLocation.getText().toString(),
                        date.getText().toString(), time.getText().toString()))
                {
                    dismiss();
                    try {
                        sendResult(SocialFragment.REQUEST_CODE);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        sendResult(SocialFragment.REQUEST_CODE);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


            }

        });

        return builder.create();
    }

/*
    private DialogInterface.OnClickListener mClickListener = new DialogInterface.OnClickListener() {




        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                checkInput(EventName.getText().toString(), EventLocation.getText().toString(),
                        date.getText().toString(), time.getText().toString());
                sendResult(SocialFragment.REQUEST_CODE);
            }
        }

    };*/

    private boolean checkInput(String e, String l, String d, String t) {
            if (e.isEmpty()) {
                EventName.setError("Please enter an event location");
                return false;

            }
            if (l.isEmpty()) {
                EventLocation.setError("Please enter an event location");
                return false;
            }
            if (d.isEmpty()) {
                date.setError("Please enter an event date");
                return false;
            }
            if (t.isEmpty()) {
                time.setError("Please enter a valid event time");
                return false;
            }

            String dateTemp = date.getText().toString();
            String day = dateTemp.substring(0, 1);
            String month = dateTemp.substring(3, 4);

            Pattern pattern1 = Pattern.compile("\\d{2}/\\d{2}/\\d{2}");
            Pattern pattern2 = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
            Pattern pattern5 = Pattern.compile("\\d{1}/\\d{2}/\\d{4}");
            Pattern pattern6 = Pattern.compile("\\d{2}/\\d{2}/\\d{2}");

        Matcher matcher1 = pattern1.matcher(dateTemp);
            Matcher matcher2 = pattern2.matcher(dateTemp);
            Matcher matcher5 = pattern5.matcher(dateTemp);
            boolean matchDate = false;

            if(matcher1.matches())
                matchDate = true;
            if(matcher2.matches())
                matchDate = true;
            if(matcher5.matches())
                matchDate = true;


        if (!matchDate || Integer.parseInt(day) > 31 || Integer.parseInt(day) < 1 ||
                    Integer.parseInt(month) > 12 || Integer.parseInt(month) < 1) {
                date.setError("Please enter a valid event date dd/mm/yy or dd/mm/yyyy");
                return false;
            }


            String timeTemp = time.getText().toString();
            Pattern pattern3 = Pattern.compile("\\d{2}:\\d{2}");
            Pattern pattern4 = Pattern.compile("\\d:\\d{2}");

        boolean matchTime = false;
        Matcher matcher3 = pattern3.matcher(timeTemp);
        Matcher matcher4 = pattern4.matcher(timeTemp);
        String hours = timeTemp.substring(0, 1);
        String minutes = timeTemp.substring(3, 4);

        if(matcher3.matches())
            matchTime = true;
        if(matcher4.matches())
            matchTime = true;



             if (!matchTime || Integer.parseInt(hours) > 24 || Integer.parseInt(hours) < 1 ||
                     Integer.parseInt(minutes) > 59 || Integer.parseInt(hours) < 1) {
              time.setError("Please enter a valid event time hh:mm");
              return false;
             }

        return true;
    }

    private void sendResult(int REQUEST_CODE) throws ParseException {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putString(EVENT, EventName.getText().toString());
        b.putString(PLACE, EventLocation.getText().toString());
        b.putString(DATE, date.getText().toString());
        b.putString(TIME, time.getText().toString());
        b.putString(HOUSE, housespinner.getSelectedItem().toString());

        intent.putExtras(b);
        SocialFragment.onFragmentResult(getTargetRequestCode(), REQUEST_CODE, intent);
    }


}

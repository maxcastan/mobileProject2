package edu.fsu.cs.mobile.hw5.project2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    Spinner housespinner;

    public MyDialogFragment() {
        // Required empty public constructor
    }



    //when dialog is created, attach UI components to their respective objects
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
        Button submit = rootView.findViewById(R.id.submit);
        housespinner = rootView.findViewById(R.id.HousesList);
        String[] housesInArray = getResources().getStringArray(R.array.housesarray);
        builder.setView(rootView);
        builder.setTitle("Create Event");
        builder.setMessage("Enter event name:");


        //when submit button is pressed, check that all input is correct and then send results
        //to SocialFragment
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


//error checks on all appropriate inputs
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
            String day = "";
            String month = "";
            int startIndex = 0;


            for(int i = 0; i < 3; i++)
            {
                if(dateTemp.charAt(i) != '/')
                    day = day + dateTemp.charAt(i);
                else
                    startIndex = i;

            }

            for(int i = startIndex; i < startIndex + 3; i++)
            {
                if(dateTemp.charAt(i) != '/')
                    month = month + dateTemp.charAt(i);
            }

            Pattern pattern1 = Pattern.compile("\\d{2}/\\d{2}/\\d{2}");
            Pattern pattern2 = Pattern.compile("\\d/\\d{2}/\\d{2}");
            Pattern pattern3 = Pattern.compile("\\d{2}/\\d/\\d{2}");
            Pattern pattern4 = Pattern.compile("\\d/\\d/\\d{2}");
            Pattern pattern5 = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
            Pattern pattern6 = Pattern.compile("\\d/\\d{2}/\\d{4}");
            Pattern pattern7 = Pattern.compile("\\d{2}/\\d/\\d{4}");
            Pattern pattern8 = Pattern.compile("\\d/\\d/\\d{4}");

            Matcher matcher1 = pattern1.matcher(dateTemp);
            Matcher matcher2 = pattern2.matcher(dateTemp);
            Matcher matcher3 = pattern3.matcher(dateTemp);
            Matcher matcher4 = pattern4.matcher(dateTemp);
            Matcher matcher5 = pattern5.matcher(dateTemp);
            Matcher matcher6 = pattern6.matcher(dateTemp);
            Matcher matcher7 = pattern7.matcher(dateTemp);
            Matcher matcher8 = pattern8.matcher(dateTemp);
            boolean matchDate = false;

            if(matcher1.matches() || matcher2.matches() || matcher3.matches() || matcher4.matches() ||
                    matcher5.matches() || matcher6.matches() || matcher7.matches() || matcher8.matches())
                matchDate = true;


            if (!matchDate || Integer.parseInt(day) > 31 || Integer.parseInt(day) < 1 ||
                    Integer.parseInt(month) > 12 || Integer.parseInt(month) < 1) {
                date.setError("Please enter a valid event date dd/mm/yy or dd/mm/yyyy");
                return false;
            }


            String timeTemp = time.getText().toString();
            Pattern pattern9 = Pattern.compile("\\d{2}:\\d{2}");
            Pattern pattern10 = Pattern.compile("\\d:\\d{2}");
            Pattern pattern11 = Pattern.compile("\\d{2}:\\d");
            Pattern pattern12 = Pattern.compile("\\d:\\d");



            boolean matchTime = false;
            Matcher matcher9 = pattern9.matcher(timeTemp);
            Matcher matcher10 = pattern10.matcher(timeTemp);
            Matcher matcher11 = pattern11.matcher(timeTemp);
            Matcher matcher12 = pattern12.matcher(timeTemp);


            String hours = "";
            String minutes = "";

            for(int i = 0; i < 3; i++)
            {
                if(timeTemp.charAt(i) != ':')
                    hours = hours + timeTemp.charAt(i);
                else
                    startIndex = i;

            }

            for(int i = startIndex; i < startIndex + 3; i++)
            {
                if(timeTemp.charAt(i) != ':')
                    minutes = minutes + timeTemp.charAt(i);
            }

            if(matcher9.matches() || matcher10.matches() || matcher11.matches() || matcher12.matches())
                matchTime = true;


             if (!matchTime || Integer.parseInt(hours) > 24 || Integer.parseInt(hours) < 1 ||
                     Integer.parseInt(minutes) > 59 || Integer.parseInt(hours) < 1) {
              time.setError("Please enter a valid event time hh:mm");
              return false;
             }

            return true;
    }

    // Sends the bundle back to event fragment.
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

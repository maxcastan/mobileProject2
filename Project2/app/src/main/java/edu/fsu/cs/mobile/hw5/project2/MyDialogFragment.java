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
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyDialogFragment extends DialogFragment{


    public static final String TAG = MyDialogFragment.class.getCanonicalName();
    public final static int REQUEST_CODE = 0;

    public final static String EVENT = "event";
    public final static String PLACE = "place";
    public final static String DATE = "date";
    public final static String TIME = "time";
    private EditText EventName;
    private EditText EventLocation;
    private EditText date;
    private EditText time;
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
        //spinner
        housespinner = rootView.findViewById(R.id.HousesList);
        String[] housesInArray = getResources().getStringArray(R.array.housesarray);
        builder.setView(rootView);
        builder.setTitle("Create Event");
        builder.setMessage("Enter event name:");
        builder.setPositiveButton("Create", mClickListener);
        return builder.create();
    }


    private DialogInterface.OnClickListener mClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                //mListener.onNewEvent(EventName.getText().toString(), EventLocation.getText().toString(),
                //      date.getText().toString(), time.getText().toString());
                sendResult(SocialFragment.REQUEST_CODE);
            }
        }

    };


    private void sendResult(int REQUEST_CODE) {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putString(EVENT, EventName.getText().toString());
        b.putString(PLACE, EventLocation.getText().toString());
        b.putString(DATE, date.getText().toString());
        b.putString(TIME, time.getText().toString());

        intent.putExtra("bunlde", b);
        SocialFragment.onFragmentResult(getTargetRequestCode(), REQUEST_CODE, intent);
    }


}

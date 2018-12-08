package edu.fsu.cs.mobile.hw5.project2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
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

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyDialogFragment extends DialogFragment{

    /*public interface MyDialogFragmentListener {
        void onNewEvent(String event);
    }*/

    public static final String TAG = MyDialogFragment.class.getCanonicalName();

    private EditText EventName;
    private TextView mTimeDisplay;
    private TextView mDateDisplay;
    private View rootView;
    private int mYear;
    private int mMonth;
    private int mDay;


    Spinner housespinner;


    public interface MyDialogListener {
        void onNewEvent(String name);
    }

    private MyDialogListener mListener;


    public MyDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.fragment_dialog, null);
        EventName = rootView.findViewById(R.id.name);
        builder.setView(rootView);
        builder.setTitle("Create Event");
        builder.setMessage("Enter event name:");
        builder.setPositiveButton("Create", mClickListener);

        Button setDate = rootView.findViewById(R.id.buttonDate);
        Button setTime = rootView.findViewById(R.id.buttonTime);
        mTimeDisplay = rootView.findViewById(R.id.mTimeDisplay);
        mDateDisplay = rootView.findViewById(R.id.mDateDisplay);


        //spinner
        housespinner = rootView.findViewById(R.id.HousesList);

        String[] housesInArray = getResources().getStringArray(R.array.housesarray);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatePickerDialogFragment frag = new MyDatePickerDialogFragment();
                frag.show(getFragmentManager(), MyDatePickerDialogFragment.TAG);

            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTimePickerDialogFragment frag = new MyTimePickerDialogFragment();
                frag.show(getFragmentManager(), MyTimePickerDialogFragment.TAG);
            }
        });
        return builder.create();
    }


    private DialogInterface.OnClickListener mClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                mListener.onNewEvent(EventName.getText().toString());
            }
        }

    };




    private void updateDateDisplay(int year,
                                   int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear;
        mDay = dayOfMonth;
        //mDateDisplay.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
    }

    private void updateTimeDisplay(int hour, int min) {
        mTimeDisplay.setText(String.format(Locale.US, "%02d:%02d", hour, min));
    }




    public static class MyDatePickerDialogFragment extends DialogFragment {
        public static final String TAG = MyDatePickerDialogFragment.class.getCanonicalName();
        MyDialogFragment mdf = new MyDialogFragment();


        private DatePickerDialog.OnDateSetListener mDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mdf.updateDateDisplay(year, monthOfYear, dayOfMonth);
                    }
                };

        public MyDatePickerDialogFragment() {
            // Required empty public constructor
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int monthOfYear = c.get(Calendar.MONTH);
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), mDateSetListener, year,
                    monthOfYear, dayOfMonth);
        }


    }

    public static class MyTimePickerDialogFragment extends DialogFragment {

        public static final String TAG = MyTimePickerDialogFragment.class.getCanonicalName();
        MyDialogFragment mdf;

        private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                mdf.updateTimeDisplay(hour, min);
            }
        };

        public MyTimePickerDialogFragment() {
            // Required empty public constructor
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), mTimeSetListener, hour, min, false);
        }


    }

   
}

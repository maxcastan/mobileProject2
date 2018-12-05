package edu.fsu.cs.mobile.hw5.project2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;


public class MyDialogFragment extends DialogFragment {

    public static final String TAG = MyDialogFragment.class.getCanonicalName();

    private EditText EventName;


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
        View rootView = inflater.inflate(R.layout.fragment_dialog, null);
        EventName = rootView.findViewById(R.id.name);
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
                mListener.onNewEvent(EventName.getText().toString());
            }
        }

    };



   
}

package edu.fsu.cs.mobile.hw5.project2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MessageDialogFragment extends DialogFragment {


    public static final String TAG = MyDialogFragment.class.getCanonicalName();
    private EditText msg;
    private Button submit;
    public final static String MESSAGE = "message";
    public final static int REQUEST_CODE = 1;



    public MessageDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_message_dialog, null);
        msg = rootView.findViewById(R.id.msg);
        submit = rootView.findViewById(R.id.msg_submit);
        builder.setView(rootView);
        builder.setTitle("Enter a message:");

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                sendResult(HomeFragment.REQUEST_CODE);
                dismiss();


            }

        });

        return builder.create();
    }

    private void sendResult(int REQUEST_CODE) {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putString(MESSAGE, msg.getText().toString());

        intent.putExtras(b);
        HomeFragment.onFragmentResult(getTargetRequestCode(), intent);
    }



}

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


public class MessageDialogFragment extends DialogFragment {


    public static final String TAG = MyDialogFragment.class.getCanonicalName();
    private EditText msg;
    private Button submit;
    public final static String MESSAGE = "message";
    public final static int REQUEST_CODE = 1;



    public MessageDialogFragment() {
        // Required empty public constructor
    }


    // When dialog is created, attach UI components to their respective objects
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_message_dialog, null);
        msg = rootView.findViewById(R.id.msg);
        submit = rootView.findViewById(R.id.msg_submit);
        final int code = getTargetRequestCode();
        builder.setView(rootView);
        builder.setTitle("Enter a message:");

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                sendResult(code);
                dismiss();


            }

        });

        return builder.create();
    }

    // Checks the number received from parent fragment and it sends the bundle back to the fragment that
    // matches the number.
    private void sendResult(int REQUEST_CODE) {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putString(MESSAGE, msg.getText().toString());

        intent.putExtras(b);
        if(REQUEST_CODE == HomeFragment.REQUEST_CODE)
            HomeFragment.onFragmentResult(getTargetRequestCode(), intent);
        if (REQUEST_CODE == HouseMessageComments.REQUEST_CODE)
            HouseMessageComments.onFragmentResult(getTargetRequestCode(), intent);
        if (REQUEST_CODE == EventMessageFragment.REQUEST_CODE)
            EventMessageFragment.onFragmentResult(getTargetRequestCode(), intent);

    }



}

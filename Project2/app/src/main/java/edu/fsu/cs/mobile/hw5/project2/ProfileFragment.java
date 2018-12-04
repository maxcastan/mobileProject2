package edu.fsu.cs.mobile.hw5.project2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private Map<String, Object> message=new HashMap<>();

    EditText userid, emailuser, rank;
    TextView houseuser , bday, actualName;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);
        final FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();

        //this lets the fragment know it will have an Options Menu
        setHasOptionsMenu(true);


        //edit text because these may change and are editable
        userid = (EditText) getActivity().findViewById(R.id.pUserId);
        emailuser = (EditText)  getActivity().findViewById(R.id.pEmail);
        rank = (EditText)  getActivity().findViewById(R.id.pRank);

        //text view bc these do not change
        bday = (TextView)  getActivity().findViewById(R.id.pBday);
        houseuser= (TextView)  getActivity().findViewById(R.id.pHouse);
        actualName = (TextView)  getActivity().findViewById(R.id.pName);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.logout_button, menu);
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.logout:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }

    }

    private void logout() {
        mAuth.signOut();
        Intent myIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(myIntent);
        getActivity().finish();
    }

}

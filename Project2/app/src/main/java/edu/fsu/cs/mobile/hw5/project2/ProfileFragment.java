package edu.fsu.cs.mobile.hw5.project2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static edu.fsu.cs.mobile.hw5.project2.R.mipmap.m1beard;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {




    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private Map<String, Object> message=new HashMap<>();

    EditText userid, emailuser, rank;
    TextView houseuser , bday, actualName;

    ImageView image;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("this is working 1:  " , "1");


        View v=inflater.inflate(R.layout.fragment_profile, container, false);


        Log.i("this is working 2:  " , "2");

        //this lets the fragment know it will have an options menu
        setHasOptionsMenu(true);
        final FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();


        Log.i("this is working 3:  " , "3");

        image = (ImageView) v.findViewById(R.id.pImg);

        Log.i("this is working 3.1:  " , "3.1");


        //male
        image.setImageResource(R.mipmap.m1beard);
        //Resource(m1beard);

        Log.i("this is working 3.2:  " , "3.2");


        //female
        //image.setImageResource(R.mipmap.w2);

        Log.i("this is working 4:  " , "4");


        //edit text because these may change and are editable
        userid = (EditText) getActivity().findViewById(R.id.pUserId);
        emailuser = (EditText)  getActivity().findViewById(R.id.pEmail);
        rank = (EditText)  getActivity().findViewById(R.id.pRank);


        Log.i("this is working 5:  " , "5");

        //text view bc these do not change
        bday = (TextView)  getActivity().findViewById(R.id.pBday);
        houseuser= (TextView)  getActivity().findViewById(R.id.pHouse);
        actualName = (TextView)  getActivity().findViewById(R.id.pName);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.profile_menu, menu);

        Log.i("this is working 6:  " , "6");

        super.onCreateOptionsMenu(menu, inflater);

        Log.i("this is working 7:  " , "7");
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.logout_option:
                logout();
                return true;

            case R.id.update_option:
                updateAccount();
                return true;

            case R.id.delete_option:
                deleteAccount();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }

    }

 
    //edit this method later on

    //the code for this method should be changed

    private void logout() {
        /*
        mAuth.signOut();
        Intent myIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(myIntent);
        getActivity().finish();
        */
    }

    private void updateAccount(){
        //code here
    }

    private void deleteAccount(){
        //code here
    }

}

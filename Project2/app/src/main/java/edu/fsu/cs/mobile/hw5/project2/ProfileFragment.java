package edu.fsu.cs.mobile.hw5.project2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static edu.fsu.cs.mobile.hw5.project2.R.mipmap.m1beard;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {




    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private Map<String, Object> user=new HashMap<>();
    private FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();

    TextView houseuser , actualName, emailuser;

    //pulls from firebase
    String house;//String variables that will store Firestore data
    String email=currentUser.getEmail();//user's email already grabbed
    String name=currentUser.getDisplayName();//user's name already grabbed
    boolean admin;



    ImageView image;

    Button requestBtn;

    Animation slideup;

    MenuItem requestHouseOption;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v=inflater.inflate(R.layout.fragment_profile, container, false);

        getActivity().setTitle("Profile");

        //this lets the fragment know it will have an options menu
        setHasOptionsMenu(true);
        final FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();


        image = (ImageView) v.findViewById(R.id.pImg);


        //male
        image.setImageResource(R.mipmap.m1beard);
        //Resource(m1beard);



        //female for scaling to identify gender
        //image.setImageResource(R.mipmap.w2);


        //text view bc these do not change
        houseuser= (TextView)  v.findViewById(R.id.pHouse);
        actualName = (TextView)  v.findViewById(R.id.pName);
        emailuser = (TextView)  v.findViewById(R.id.pEmail);

        //sets to what firebase pulled
        if(name.length()!=0) {
            actualName.setText(name);
            emailuser.setText(email);
        }



        requestBtn = v.findViewById(R.id.requestBtn);

         slideup = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_up);


        // Slide Up
        actualName.startAnimation(slideup);
        emailuser.startAnimation(slideup);
        houseuser.startAnimation(slideup);
        /*
        btnSlideUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSlideUp.startAnimation(animSlideUp);
            }
        });

*/

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.profile_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

        requestHouseOption = menu.findItem(R.id.request_option);

    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.logout_option:
                logout();
                return true;

            case R.id.delete_option:
                deleteAccount();
                return true;

            case R.id.request_option:
                requestHouse();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }

    }

 
    //edit this method later on

    //the code for this method should be changed

    private void logout() {
        mAuth.signOut();
        Intent myIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(myIntent);
        getActivity().finish();
    }

    private void requestHouse(){
        ListHouseFragment listHouseFragment=new ListHouseFragment();
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.user_frame, listHouseFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void deleteAccount(){
        //code here
        db.collection("Users").document(currentUser.getEmail())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent myintent=new Intent(getActivity(), MainActivity.class);
                startActivity(myintent);
                getActivity().finish();
                currentUser.delete();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        DocumentReference user=db.collection("Users").document(currentUser.getEmail());//chooses User's document
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {//gets data from document
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc=task.getResult();//when complete grabs the result
                house=doc.getString("house");//gets house
                if(house==null){//if house hasn't been defined yet, set to undefined
                    house="Alpha Rho Rho";
                }
                if(doc.getBoolean("admin")==null){
                    admin =false;
                }
                else {
                    admin = doc.getBoolean("admin");
                }
                if(admin){

                    requestBtn.setVisibility(View.VISIBLE);

                    requestHouseOption.setVisible(false);

                    requestBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RequestFragment requestFragment = new RequestFragment();
                            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.user_frame, requestFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                }
                houseuser.setText(house);
            }
        });

    }
}

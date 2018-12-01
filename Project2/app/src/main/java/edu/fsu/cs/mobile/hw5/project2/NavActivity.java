package edu.fsu.cs.mobile.hw5.project2;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import edu.fsu.cs.mobile.hw5.project2.HomeFragment;

/*
import edu.fsu.cs.mobile.hw5.project2.LocationFragment;
import edu.fsu.cs.mobile.hw5.project2.SocialFragment;
*/

public class NavActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;

    private SocialFragment socialFragment;


    private AdminFragment adminFragment;
    private ProfileFragment profileFragment;



    private Context context;
    private String phoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        //the fragments we have

        homeFragment=new HomeFragment();
        socialFragment=new SocialFragment();

        profileFragment=new ProfileFragment();


        mMainFrame=(FrameLayout) findViewById(R.id.user_frame);
        mMainNav=(BottomNavigationView) findViewById(R.id.user_nav);
        setFragment(homeFragment);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){


                    case R.id.nav_home:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(homeFragment);
                        return true;


                    case R.id.nav_social:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(socialFragment);
                        return true;


                    case R.id.nav_profile:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(adminFragment);
                        return true;


                    default:
                        return false;

                }
            }
        });

        Intent background = new Intent(context, BackgroundService.class);
        Toast.makeText(getApplicationContext(), "about to start Service", Toast.LENGTH_LONG)
                .show();
        context.startService(background);


    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.user_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }






}
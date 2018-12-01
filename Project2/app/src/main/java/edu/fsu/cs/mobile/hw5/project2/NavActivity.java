package edu.fsu.cs.mobile.hw5.project2;


import android.content.Context;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.FrameLayout;


public class NavActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;

    private SocialFragment socialFragment;
    private ProfileFragment profileFragment;



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
                        setFragment(homeFragment);
                        return true;


                    case R.id.nav_social:
                        setFragment(socialFragment);
                        return true;


                    case R.id.nav_profile:
                        setFragment(profileFragment);
                        return true;


                    default:
                        return false;

                }
            }
        });


    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.user_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }






}
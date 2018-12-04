package edu.fsu.cs.mobile.hw5.project2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment {


    public SocialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FloatingActionButton fab = new FloatingActionButton(getActivity());
        fab.findViewById(R.id.add);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_social, container, false);
        fab.setScaleType(ImageView.ScaleType.CENTER);


        return rootView;
    }

}

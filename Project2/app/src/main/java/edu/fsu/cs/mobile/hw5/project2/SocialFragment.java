package edu.fsu.cs.mobile.hw5.project2;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private Map<String, Object> event=new HashMap<>();
    FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference eventRef=db.collection("Events");
    private EventAdapter adapter;
    private View v;
    private Query query;
    private String house;

    public SocialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_social, container, false);

        FloatingActionButton fab = v.findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }
    private void setUpRecyclerView(View v, final Context c) {

        FirestoreRecyclerOptions<Event> options=new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();
        adapter=new EventAdapter(options);

        RecyclerView recyclerView=v.findViewById(R.id.recycler_social_fragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        query=eventRef;
        setUpRecyclerView(v, getContext());
        adapter.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

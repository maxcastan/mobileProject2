package edu.fsu.cs.mobile.hw5.project2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventMessageFragment extends Fragment {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static FirebaseFirestore db=FirebaseFirestore.getInstance();
    private Map<String, Object> message=new HashMap<>();
    private static FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference messsageRef=db.collection("Events");
    private MessageAdapter adapter;
    View v;
    private static String eventID;
    Query query;
    private static String events;
    private static String commentID;
    public final static int REQUEST_CODE = 3;


    public EventMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_event_message, container, false);
        Bundle bundle=this.getArguments();
        if(bundle!=null){
            eventID=bundle.getString("eventID");
            setUpRecyclerView(v, getContext());
        }
        getActivity().setTitle("Event Comments");
        FloatingActionButton add=v.findViewById(R.id.floatingActionButton_event_messages);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialogFragment mdf = new MessageDialogFragment();
                mdf.setTargetFragment(getFragmentManager().getPrimaryNavigationFragment(), REQUEST_CODE);
                mdf.show(getFragmentManager(), MessageDialogFragment.TAG);
            }
        });
        return v;
    }
    private void setUpRecyclerView(View v, final Context c) {
        query=messsageRef.document(eventID).collection("Messages")
                .orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Message> options=new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();
        adapter=new MessageAdapter(options);

        RecyclerView recyclerView=v.findViewById(R.id.recycler_event_messages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    public static void onFragmentResult(int requestCode, Intent data) {
        // Make sure fragment codes match up
        if (requestCode == EventMessageFragment.REQUEST_CODE) {

            Bundle b = data.getExtras();
            String text = b.getString(MessageDialogFragment.MESSAGE);

            Map<String, Object> message=new HashMap<>();
            message.put("message", text);
            message.put("name", currentUser.getDisplayName());
            message.put("timestamp", new Date());
            db.collection("Events").document(eventID)
                    .collection("Messages").document().set(message);
        }
    }

}

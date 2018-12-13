package edu.fsu.cs.mobile.hw5.project2;


import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HouseMessageComments extends Fragment {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static FirebaseFirestore db=FirebaseFirestore.getInstance();
    private Map<String, Object> message=new HashMap<>();
    private static FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference messsageRef=db.collection("House");
    private MessageAdapter adapter;
    View v;
    Query query;
    private static String house;
    private static String commentID;
    public final static int REQUEST_CODE = 2;



    public HouseMessageComments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {//creates view to display all comments inside a message
        v=inflater.inflate(R.layout.fragment_house_message_comments, container, false);
        Bundle bundle=this.getArguments();
        if(bundle!=null){
            house=bundle.getString("house");
            commentID=bundle.getString("commentID");
            setUpRecyclerView(v, getContext());
        }
        getActivity().setTitle("Comments");
        FloatingActionButton add=v.findViewById(R.id.floatingActionButton_house_message_comments);
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

    private void setUpRecyclerView(View v, final Context c) {//queries all comments within the specific message and displays them in order of time posted
        query=messsageRef.document(house).collection("Messages").document(commentID)
                .collection("Comments")
                .orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Message> options=new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();
        adapter=new MessageAdapter(options);

        RecyclerView recyclerView=v.findViewById(R.id.recycler_house_message_comments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {//start listening to adapter
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {//stop listening to adapter
        super.onStop();
        adapter.stopListening();
    }

    public static void onFragmentResult(int requestCode, Intent data) {//grabs comment data from dialog fragment and pushes to firestore
        // Make sure fragment codes match up
        if (requestCode == HouseMessageComments.REQUEST_CODE) {

            Bundle b = data.getExtras();
            String text = b.getString(MessageDialogFragment.MESSAGE);

            Map<String, Object> message=new HashMap<>();
            message.put("message", text);
            message.put("name", currentUser.getDisplayName());
            message.put("timestamp", new Date());
            db.collection("House").document(house)
                    .collection("Messages").document(commentID).collection("Comments").document().set(message);
        }
    }

}

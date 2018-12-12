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
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment{

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private Map<String, Object> event=new HashMap<>();
    FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference eventRef=db.collection("Events");
    private EventAdapter adapter;
    private View v;
    private Query query;
    public final static int REQUEST_CODE = 0;


    public SocialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_social, container, false);

        getActivity().setTitle("Events");

        FloatingActionButton fab = v.findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogFragment mdf = new MyDialogFragment();
                mdf.setTargetFragment(getFragmentManager().getPrimaryNavigationFragment(), REQUEST_CODE);
                mdf.show(getFragmentManager(), MyDialogFragment.TAG);
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

        adapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                EventMessageFragment eventMessageFragment=new EventMessageFragment();
                Bundle bundle=new Bundle();
                bundle.putString("eventID", documentSnapshot.getId());
                eventMessageFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.user_frame, eventMessageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        query=eventRef
                .orderBy("timestamp", Query.Direction.ASCENDING);

        setUpRecyclerView(v, getContext());
        adapter.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public static void onFragmentResult(int requestCode, int resultCode, Intent data) {
        // Make sure fragment codes match up
        if (requestCode == MyDialogFragment.REQUEST_CODE) {

            Bundle b = data.getExtras();
            String name = b.getString(MyDialogFragment.EVENT);
            String place = b.getString(MyDialogFragment.PLACE);
            String date = b.getString(MyDialogFragment.DATE);
            String time = b.getString(MyDialogFragment.TIME);

            onNewEvent(name, place, date, time);


        }

    }

    public static void onNewEvent(String e, String l, String d, String t) {

    }
}

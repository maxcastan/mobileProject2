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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment{

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static FirebaseFirestore db=FirebaseFirestore.getInstance();
    private Map<String, Object> event=new HashMap<>();
    FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference eventRef=db.collection("Events");
    private EventAdapter adapter;
    private View v;
    private Query query;
    private static String house;

    public final static int REQUEST_CODE = 0;


    public SocialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_social, container, false);

        getActivity().setTitle("Event Stream");

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
        DocumentReference user=db.collection("Users").document(currentUser.getEmail());
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc=task.getResult();
                house=doc.getString("house");
                if(house==null){
                    house="Alpha Rho Rho";
                }
                query=eventRef.whereArrayContains("Invited", house);
                    //    .orderBy("timestamp", Query.Direction.DESCENDING);

                setUpRecyclerView(v, getContext());
                adapter.startListening();
            }
        });



    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public static void onFragmentResult(int requestCode, int resultCode, Intent data) throws ParseException {
        // Make sure fragment codes match up
        if (requestCode == MyDialogFragment.REQUEST_CODE) {

            Bundle b = data.getExtras();
            String name = b.getString(MyDialogFragment.EVENT);
            String place = b.getString(MyDialogFragment.PLACE);
            String date = b.getString(MyDialogFragment.DATE);
            String time = b.getString(MyDialogFragment.TIME);
            String inviteHouse=b.getString(MyDialogFragment.HOUSE);
            DateFormat format=new SimpleDateFormat("dd/MM/yy hh:mm", Locale.US);
            Date eventDate=format.parse(date+" "+time);
            Map<String, Object> event=new HashMap<>();
            event.put("address", place);
            event.put("title", name);
            event.put("timestamp", eventDate);
            event.put("Invited", Arrays.asList(house, inviteHouse));
            db.collection("Events").document().set(event);
        }

    }

    public static void onNewEvent(String e, String l, String d, String t) {

    }
}

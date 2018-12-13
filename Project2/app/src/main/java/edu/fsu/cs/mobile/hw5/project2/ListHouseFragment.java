package edu.fsu.cs.mobile.hw5.project2;


import android.content.Context;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListHouseFragment extends Fragment {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference houseRef=db.collection("House");
    private HouseAdapter adapter;


    public ListHouseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,//creates view of list houses, calls recycler view
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_list_house, container, false);
        getActivity().setTitle("Request a House");
        setUpRecyclerView(v, getContext());
        return v;
    }

    private void setUpRecyclerView(View v, final Context c) {//queries and returns a list of all houses the user can request to join
        Query query=houseRef;
        FirestoreRecyclerOptions<House> options=new FirestoreRecyclerOptions.Builder<House>()
                .setQuery(query, House.class)
                .build();
        adapter=new HouseAdapter(options);

        RecyclerView recyclerView=v.findViewById(R.id.recycler_house_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {//on click, the user is added to the request array for that house in firestore
                Map<String, Object> user=new HashMap<>();
                user.put("userEmail", currentUser.getEmail());
                user.put("userName", currentUser.getDisplayName());
                documentSnapshot.getReference().collection("PendingUsers")
                        .add(user);
                Toast.makeText(getContext(), "Requested House", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStart() {//adapter starts listening
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {//adapter stops listening
        super.onStop();
        adapter.stopListening();
    }

}

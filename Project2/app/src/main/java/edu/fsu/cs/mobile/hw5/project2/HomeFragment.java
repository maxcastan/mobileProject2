package edu.fsu.cs.mobile.hw5.project2;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

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
public class HomeFragment extends Fragment {


    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static FirebaseFirestore db=FirebaseFirestore.getInstance();
  //  private Map<String, Object> message=new HashMap<>();
    private static FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference messsageRef=db.collection("House");
    private MessageAdapter adapter;
    private View v;
    private Query query;
    private static String house;
    public final static int REQUEST_CODE = 1;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton add=v.findViewById(R.id.floatingActionButton_home_fragment);

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

        FirestoreRecyclerOptions<Message> options=new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();
        adapter=new MessageAdapter(options);

        RecyclerView recyclerView=v.findViewById(R.id.recycler_home_fragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                HouseMessageComments houseMessageComments=new HouseMessageComments();
                Bundle bundle=new Bundle();
                bundle.putString("house", house);
                bundle.putString("commentID", documentSnapshot.getId());
                houseMessageComments.setArguments(bundle);
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.user_frame, houseMessageComments);
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
                    house="APP";
                    getActivity().setTitle("Alpha Rho Rho Stream");
                }
                else{
                    getActivity().setTitle(house+" Stream");
                }
                query=messsageRef.document(house).collection("Messages");
              //  .orderBy("timestamp", Query.Direction.DESCENDING);
                setUpRecyclerView(v, getContext());
                adapter.startListening();
            }
        });

    }

    public static void onFragmentResult(int requestCode, Intent data) {
        // Make sure fragment codes match up
        if (requestCode == MessageDialogFragment.REQUEST_CODE) {
            String msg= null;
            Bundle bl = data.getExtras();
            //if(bl != null)
             //   msg = (String) bl.getSerializable(MessageDialogFragment.MESSAGE);
            Map<String, Object> message=new HashMap<>();
            message.put("message", bl.getString("message"));
            message.put("name", currentUser.getDisplayName());
            db.collection("House").document(house)
                    .collection("Messages").document().set(message);


        }

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}

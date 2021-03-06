package edu.fsu.cs.mobile.hw5.project2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserAdapter extends FirestoreRecyclerAdapter<User, UserAdapter.UserHolder> {

    private OnItemClickListener listener;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    //must create this recycler view that listens to Firestore queries
    public UserAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    //binds object member data to the ViewHolder
    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull User model) {//binds the objects member data to the view holder
        holder.userEmail.setText(model.getUserEmail());
        holder.userName.setText(model.getUserName());
    }

    //create the actual ViewHolder for the UserAdapter
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {//creates the view holder
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_card,
                viewGroup, false);
        return new UserHolder(v);

    }

    //sets the user's house to the house requested and removes them from list of requestees that the admin sees
    public void approveUser(final int adapterPosition, final String house) {
        getSnapshots().getSnapshot(adapterPosition).getReference().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc=task.getResult();
                String email=doc.getString("userEmail");
                db.collection("Users").document(email).update("house", house);
                getSnapshots().getSnapshot(adapterPosition).getReference().delete();

            }
        });
    }


    class UserHolder extends RecyclerView.ViewHolder {
        TextView userEmail;//number
        TextView userName; //message

        //holder function for the card layout
        public UserHolder(View itemView) {
            super(itemView);
            userEmail = itemView.findViewById(R.id.email_description);
            userName = itemView.findViewById(R.id.user_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //protects from crash getting -1 position of a deleted item, -1 is dne
                    if (position != RecyclerView.NO_POSITION
                            && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

    }

    //sets an interface for when the item is clicked
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    //sets a list click listener for an adapter
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}

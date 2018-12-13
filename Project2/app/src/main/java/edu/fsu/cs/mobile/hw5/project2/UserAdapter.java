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

    public UserAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull User model) {//binds the objects member data to the view holder
        holder.userEmail.setText(model.getUserEmail());
        holder.userName.setText(model.getUserName());
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {//creates the view holder
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_card,
                viewGroup, false);
        return new UserHolder(v);

    }

    public void approveUser(final int adapterPosition, final String house) {//sets the user's house to the house requested and removes them from list of requestees that the admin sees
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

        public UserHolder(View itemView) {//holder function for the card layout
            super(itemView);
            userEmail = itemView.findViewById(R.id.email_description);
            userName = itemView.findViewById(R.id.user_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION//protects from crash getting -1 position of a deleted item, -1 is dne
                            && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {//sets an interface for when the item is clicked
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }//sets a list click listener for an adapter

}

package edu.fsu.cs.mobile.hw5.project2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class HouseAdapter extends FirestoreRecyclerAdapter<House, HouseAdapter.HouseHolder> {
    private UserAdapter.OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HouseAdapter(@NonNull FirestoreRecyclerOptions<House> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HouseHolder holder, int position, @NonNull House model) {//binds the objects member data to the view holder
        holder.houseName.setText(model.getName());

    }

    @NonNull
    @Override
    public HouseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {//creates the view holder
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.house_card,
                viewGroup, false);
        return new HouseAdapter.HouseHolder(v);
    }


    class HouseHolder extends RecyclerView.ViewHolder {
        TextView houseName;

        public HouseHolder(View itemView) {//holder function for the card layout
            super(itemView);
            houseName=itemView.findViewById(R.id.house_name);
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

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(UserAdapter.OnItemClickListener listener) {//sets a  click listener for an adapter
        this.listener = listener;
    }
}

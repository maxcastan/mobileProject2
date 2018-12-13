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

public class EventAdapter extends FirestoreRecyclerAdapter <Event, EventAdapter.EventHolder>{
    private MessageAdapter.OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EventAdapter(@NonNull FirestoreRecyclerOptions<Event> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventHolder holder, int position, @NonNull Event model) {//binds the objects member data to the view holder
        holder.title.setText(model.getTitle());
        holder.address.setText(model.getAddress());
        holder.timestamp.setText(model.getTimestamp().toDate().toString());
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {//creates the view holder
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_card,
                viewGroup, false);
        return new EventAdapter.EventHolder(v);    }

    class EventHolder extends RecyclerView.ViewHolder {
        TextView title;//title
        TextView address; //address
        TextView timestamp; //timestamp

        public  EventHolder(View itemView){//holder function for the card layout
            super(itemView);
            title=itemView.findViewById(R.id.event_title);
            address=itemView.findViewById(R.id.event_address);
            timestamp=itemView.findViewById(R.id.event_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION//protects from crash getting -1 position of a deleted item, -1 is dne
                            && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }


    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(MessageAdapter.OnItemClickListener listener){//sets a  click listener for an adapter
        this.listener=listener;
    }
}

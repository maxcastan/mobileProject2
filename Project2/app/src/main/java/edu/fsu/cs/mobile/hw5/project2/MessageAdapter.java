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

public class MessageAdapter extends FirestoreRecyclerAdapter <Message, MessageAdapter.MessageHolder>{
    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MessageAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
    }

    //binds the objects member data to the view holder
    @Override
    protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull Message model) {
        holder.title.setText(model.getMessage());
        holder.description.setText(model.getName());
    }


    //creates the view holder
    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_card,
                viewGroup, false);
        return new MessageHolder(v);
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        TextView title;//number
        TextView description; //message

        //holder function for the card layout
        public  MessageHolder(View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.text_title);
            description=itemView.findViewById(R.id.text_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    //protects from crash getting -1 position of a deleted item, -1 is dne
                    if(position!=RecyclerView.NO_POSITION
                            && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }


    }
    //interface fo when an item is clicked
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    //sets a click listener for an adapter
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }//sets a list click listener for an adapter
}

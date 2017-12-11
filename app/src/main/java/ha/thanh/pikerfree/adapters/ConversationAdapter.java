package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.Conversation;
import ha.thanh.pikerfree.models.Messages.Message;
import ha.thanh.pikerfree.models.User;
import ha.thanh.pikerfree.utils.Utils;


public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder> {

    private List<Conversation> dataSet;
    private ItemClickListener mClickListener;
    private Context mConText;
    private android.os.Handler handler;
    private String currentUserId;
    private FirebaseDatabase database;


    public ConversationAdapter(Context context, List<Conversation> dataSet, ItemClickListener listener) {

        this.dataSet = dataSet;
        this.mConText = context;
        this.mClickListener = listener;
        database = FirebaseDatabase.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        handler = new Handler();
    }

    // todo get OP Image, name from op id
    // todo get LastMess data from last mess id
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.op_status)
        ImageView opStatus;
        @BindView(R.id.op_pic)
        CircleImageView OpImage;
        @BindView(R.id.tv_last_mess)
        TextView tvLastMess;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_userName)
        TextView tvOpName;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) {
                mClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversation, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final String user2Id = getOpString(position);
        // get op images
        FirebaseStorage
                .getInstance()
                .getReference()
                .child("userImages")
                .child(user2Id + ".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(mConText)
                                .load(uri)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.loading)
                                        .centerCrop()
                                        .dontAnimate()
                                        .override(100, 100)
                                        .dontTransform())
                                .into(holder.OpImage);
                    }
                });

        // Get OP name
        handler.post(new Runnable() {
            @Override
            public void run() {
                final DatabaseReference userPref;
                userPref = database
                        .getReference("users")
                        .child(user2Id);
                userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        holder.tvOpName.setText(user.getName());
                        user.setOnline((Boolean) dataSnapshot.child("isOnline").getValue());
                        if (user.isOnline())
                            holder.opStatus.setImageResource(R.drawable.bg_circle_check);
                        else holder.opStatus.setImageResource(R.drawable.bg_circle_gray);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        // Get last mess data
        if (dataSet.get(position).getLastMessId() == 0) {
            holder.tvLastMess.setText("No message on this conversation");
            holder.tvTime.setText("N/A");
        } else {
            final DatabaseReference messPref;
            messPref = database
                    .getReference(Constants.CONVERSATION)
                    .child(dataSet.get(position).getConversationId())
                    .child(Constants.MESS_STRING)
                    .child(dataSet.get(position).getLastMessId() + "");
            messPref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Message mess = dataSnapshot.getValue(Message.class);
                    holder.tvLastMess.setText(mess.getText());
                    holder.tvTime.setText(Utils.getTimeInHour(mess.getTime()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private String getOpString(int position) {
        if (dataSet.get(position).getIdUser1().equals(currentUserId))
            return dataSet.get(position).getIdUser2();
        return dataSet.get(position).getIdUser1();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}

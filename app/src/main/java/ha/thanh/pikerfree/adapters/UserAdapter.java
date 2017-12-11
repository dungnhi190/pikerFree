package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.net.Uri;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.models.User;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> userList;
    private ItemClickListener mClickListener;
    private Context context;

    public UserAdapter(Context context, List<User> list, ItemClickListener listener) {
        this.context = context;
        this.userList = list;
        mClickListener = listener;
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgItemImage;
        TextView tvName;
        ImageView opStatus;

        MyViewHolder(View view) {
            super(view);
            imgItemImage = (ImageView) view.findViewById(R.id.owner_pic);
            tvName = (TextView) view.findViewById(R.id.tv_owner_name);
            opStatus = (ImageView) view.findViewById(R.id.op_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onChooseUser(getAdapterPosition());

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_requesting_user, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvName.setText(userList.get(position).getName());
        if (userList.get(position).getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            holder.tvName.setText(userList.get(position).getName() + " (You)");
        if(userList.get(position).isOnline())
            holder.opStatus.setImageResource(R.drawable.bg_circle_check);
        else  holder.opStatus.setImageResource(R.drawable.bg_circle_gray);
        getUserImageLink(userList.get(position).getAvatarLink(), holder.imgItemImage);
    }

    private void getUserImageLink(String link, final ImageView imageView) {
        StorageReference mStorageRef = FirebaseStorage.getInstance()
                .getReference().child(link);
        mStorageRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context)
                                .load(uri)
                                .apply(new RequestOptions()
                                        .error(R.drawable.action_button_bg)
                                        .centerCrop()
                                        .dontAnimate()
                                        .override(200, 200)
                                        .dontTransform())
                                .into(imageView);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public interface ItemClickListener {

        void onChooseUser(int position);

    }
}


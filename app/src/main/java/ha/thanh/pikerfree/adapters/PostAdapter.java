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
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.utils.Utils;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private List<Post> dataSet;
    private ItemClickListener mClickListener;
    private Context mConText;
    private double lat;
    private double lng;

    public PostAdapter(Context context, List<Post> dataSet, ItemClickListener listener, double lat, double lng) {
        this.dataSet = dataSet;
        this.mConText = context;
        this.mClickListener = listener;
        this.lat = lat;
        this.lng = lng;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_post_image)
        ImageView imgPostImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_distance)
        TextView tvDistance;

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
                .inflate(R.layout.item_post, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Post post = dataSet.get(position);
        holder.tvDistance.setText(Utils.getDistance(lat, lng, post.getLocation().latitude, post.getLocation().longitude));
        holder.tvTitle.setText(post.getTitle());
        holder.tvDay.setText(Utils.getTimeString(post.getTimePosted()));
        holder.tvStatus.setText(getTextFromStatus(post.getStatus()));
        FirebaseStorage
                .getInstance()
                .getReference()
                .child("postImages")
                .child(post.getPostId() + "")
                .child("image_no_1.jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(mConText)
                                .load(uri)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.background)
                                        .centerCrop()
                                        .dontAnimate()
                                        .override(400, 250)
                                        .dontTransform())
                                .into(holder.imgPostImage);
                    }
                });
    }

    private String getTextFromStatus(int status) {
        switch (status) {
            case 1:
                return mConText.getResources().getString(R.string.status_open);
            case 2:
                return mConText.getResources().getString(R.string.status_close);
            default:
                return "";
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}



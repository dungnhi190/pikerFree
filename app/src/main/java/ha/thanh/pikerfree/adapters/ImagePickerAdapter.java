package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.models.ImagePost;

public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.MyViewHolder> {
    private List<ImagePost> imagePosts;
    private ItemClickListener mClickListener;
    private Context context;

    public ImagePickerAdapter(Context context, List<ImagePost> dataSet, ItemClickListener listener) {
        this.context = context;
        this.imagePosts = dataSet;
        mClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_item_image)
        ImageView imgItemImage;
        @BindView(R.id.text_uploading)
        TextView tvUploading;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onAddImagesToAdapter();
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_images, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        File imgFile = new File(imagePosts.get(position).getPathLocal());
        if (imgFile.exists()) {
            Glide.with(context)
                    .load(imgFile)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.add)
                            .centerCrop()
                            .dontAnimate()
                            .override(120, 160)
                            .dontTransform())
                    .into(holder.imgItemImage);
        }
        if (imagePosts.get(position).isUploadDone()) {
            holder.tvUploading.setVisibility(View.GONE);
        } else {
            holder.tvUploading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return imagePosts.size();
    }

    public interface ItemClickListener {
        void onAddImagesToAdapter();
    }

}
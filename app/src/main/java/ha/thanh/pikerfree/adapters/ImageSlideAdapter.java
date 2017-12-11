package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import ha.thanh.pikerfree.R;


public class ImageSlideAdapter extends PagerAdapter {

    private List<String> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ImageSlideAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_image_slide, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_item_image);

        Glide.with(this.mContext)
                .load(mList.get(position))
                .apply(new RequestOptions()
                        .placeholder(R.drawable.background)
                        .centerCrop()
                        .dontAnimate()
                        .override(400, 270)
                        .dontTransform())
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

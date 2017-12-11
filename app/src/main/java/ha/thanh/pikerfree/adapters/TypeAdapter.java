package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder> {

    private String[] dataSetString;
    private int[] dataSetInt = {
            R.drawable.ic_menu,
            R.drawable.ic_baby_trolley,
            R.drawable.ic_coat,
            R.drawable.ic_electronic,
            R.drawable.ic_shopping_basket,
            R.drawable.ic_home,
            R.drawable.ic_rabbit,
            R.drawable.ic_more,

    };
    private ItemClickListener mClickListener;
    private Context mConText;


    public TypeAdapter(Context context, ItemClickListener listener) {
        this.dataSetString = context.getResources().getStringArray(R.array.Section);
        this.mConText = context;
        this.mClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


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
                .inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TypeAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}

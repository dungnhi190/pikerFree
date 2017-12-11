package ha.thanh.pikerfree.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private String[] dataSet;
    private ItemClickListener mClickListener;


    public CategoryAdapter(Context context, ItemClickListener listener) {
        this.dataSet = context.getResources().getStringArray(R.array.Section);
        this.mClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.tvSectionTitle)
        TextView tvTitle;


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
                .inflate(R.layout.item_session, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tvTitle.setText(dataSet[position]);

    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}

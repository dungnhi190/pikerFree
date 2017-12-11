package ha.thanh.pikerfree.activities.selectCategory;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.CategoryAdapter;

public class SelectCategoryActivity extends AppCompatActivity implements CategoryAdapter.ItemClickListener {

    @BindView(R.id.lv_session)
    public RecyclerView listView;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        adapter = new CategoryAdapter(this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(int position) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("selected", position + 1);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}


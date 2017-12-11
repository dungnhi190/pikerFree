package ha.thanh.pikerfree.activities.newPost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.vlk.multimager.activities.GalleryActivity;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Params;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.selectCategory.SelectCategoryActivity;
import ha.thanh.pikerfree.adapters.ImagePickerAdapter;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.WaitingDialog;
import ha.thanh.pikerfree.utils.Utils;


public class NewPostActivity extends AppCompatActivity
        implements NewPostInterface.RequiredViewOps,
        CustomAlertDialog.AlertListener {

    @BindView(R.id.rv_images)
    public RecyclerView recyclerViewImage;
    @BindView(R.id.et_item_title)
    public TextView tvTitle;
    @BindView(R.id.et_description)
    public TextView tvDescription;
    @BindView(R.id.tv_select)
    public TextView tvSelect;

    private NewPostPresenter mPresenter;
    private ImagePickerAdapter adapter;

    private WaitingDialog waitingDialog;
    private CustomAlertDialog alertDialog;
    public final static int SELECT_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        waitingDialog = new WaitingDialog(this);
        alertDialog = new CustomAlertDialog(this);
        mPresenter = new NewPostPresenter(this, this);
        adapter = new ImagePickerAdapter(this, mPresenter.getItemList(), null);
    }

    private void initView() {
        alertDialog.setListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewImage.setLayoutManager(gridLayoutManager);
        recyclerViewImage.setAdapter(adapter);
        tvTitle.clearFocus();
        recyclerViewImage.requestFocus();
    }

    @OnClick(R.id.ic_back)

    public void getBack() {
        onBackPressed();
    }

    @OnClick(R.id.edit_images)
    public void goEditImage() {
        Intent intent = new Intent(this, GalleryActivity.class);
        Params params = new Params();
        params.setCaptureLimit(6);
        params.setPickerLimit(6);
        params.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        params.setActionButtonColor(getResources().getColor(R.color.colorPrimary));
        params.setButtonTextColor(getResources().getColor(R.color.white));
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent, Constants.TYPE_MULTI_PICKER);
    }

    @OnClick(R.id.bnt_post_this)
    public void đoPostToServer() {

        String tile = tvTitle.getText().toString();
        String description = tvDescription.getText().toString();
        alertDialog.setListener(null);
        if (TextUtils.isEmpty(tile)) {
            alertDialog.showAlertDialog("Oop!", "Please enter the title for your item");
            return;
        }
        if (TextUtils.isEmpty(description)) {
            alertDialog.showAlertDialog("Oop!", "Please enter some description");
            return;
        }
        if (tvSelect.getText().equals(getResources().getString(R.string.select_a_category))) {
            alertDialog.showAlertDialog("Oop!", "Please select a category for you post");
            return;
        }

        waitingDialog.showDialog();
        mPresenter.uploadPostToDatabase(tile, description);
    }

    @OnClick(R.id.tv_select)
    public void startSelectActivity() {
        Intent intent = new Intent(this, SelectCategoryActivity.class);
        startActivityForResult(intent, SELECT_CODE);
    }

    @Override
    public void onPostDone() {
        waitingDialog.hideDialog();
        alertDialog.showAlertDialog("Done", "Completed, Press OK to back to Main screen");
    }

    @Override
    public void onPostFail(String error) {
        waitingDialog.hideDialog();
        alertDialog.showAlertDialog("Error", error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.TYPE_MULTI_PICKER:
                recyclerViewImage.setVisibility(View.VISIBLE);
                ArrayList<Image> imagesList = data.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
                mPresenter.addAllImage(imagesList);
                adapter.notifyDataSetChanged();
                mPresenter.startUploadImages();
                break;
            case SELECT_CODE:
                int selected = data.getIntExtra("selected", 8);
                tvSelect.setText(Utils.getTextFromIntCategory(selected));
                mPresenter.selectedCategory = selected;
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUploadSingleImageDone() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerViewImage.requestFocus();
    }

    @Override
    public void onOkClicked() {
        finish();
    }
}

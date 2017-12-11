package ha.thanh.pikerfree.activities.editPost;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.vlk.multimager.activities.GalleryActivity;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Params;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.selectCategory.SelectCategoryActivity;
import ha.thanh.pikerfree.adapters.ImagePickerAdapter;
import ha.thanh.pikerfree.adapters.ImageSlideAdapter;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.CustomEditText;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.customviews.WaitingDialog;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.utils.Utils;

public class EditPostActivity extends AppCompatActivity implements EditPostInterface.RequiredViewOps {

    @BindView(R.id.vp_image_slide)
    ViewPager vpImageSlide;
    @BindView(R.id.rv_images)
    public RecyclerView recyclerViewImage;

    @BindView(R.id.et_item_title)
    CustomEditText title;
    @BindView(R.id.et_description)
    CustomEditText description;
    @BindView(R.id.tv_post_status)
    CustomTextView postStatus;
    @BindView(R.id.tv_post_category)
    CustomTextView tvCategory;

    @BindView(R.id.scroll_view)
    View scrollView;

    @BindView(R.id.edit_images)
    CustomTextView ChangeImg;

    private ImageSlideAdapter imageSlideAdapter;
    private ImagePickerAdapter imagePickerAdapter;
    private EditPostPresenter mPresenter;
    private WaitingDialog waitingDialog;
    private CustomAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        initData();
        initView();
    }

    private void initData() {

        Intent in = getIntent();
        int postID = in.getIntExtra(Constants.POST_VIEW, 1);
        mPresenter = new EditPostPresenter(this, this);
        mPresenter.getPostData(postID + "");
        mPresenter.getImageLinksFromId(postID + "");
        imagePickerAdapter = new ImagePickerAdapter(this, mPresenter.getItemList(), null);
        alertDialog = new CustomAlertDialog(this);
        imageSlideAdapter = new ImageSlideAdapter(this, mPresenter.getImagePostList());
    }

    private void initView() {
        ButterKnife.bind(this);
        scrollView.setVisibility(View.INVISIBLE);
        vpImageSlide.setAdapter(imageSlideAdapter);
        waitingDialog = new WaitingDialog(this);
        waitingDialog.showDialog();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewImage.setLayoutManager(gridLayoutManager);
        recyclerViewImage.setAdapter(imagePickerAdapter);
    }


    @OnClick(R.id.ic_back)
    public void getBack() {
        onBackPressed();
    }

    @OnClick(R.id.tv_post_status)
    public void changeStatus() {
        if (postStatus.getText().toString().equalsIgnoreCase("opening")) {
            postStatus.setText("Closed");
            postStatus.setTextColor(getResources().getColor(R.color.GrayScale));
            mPresenter.changeStatus(Constants.STATUS_CLOSE);
        } else {
            postStatus.setText("Opening");
            postStatus.setTextColor(getResources().getColor(R.color.green));
            mPresenter.changeStatus(Constants.STATUS_OPEN);
        }
    }

    @OnClick(R.id.ic_warning)
    public void showWarning() {
        alertDialog.showAlertDialog("notice", "When you change the status to closed, other users can not send receiving request to you post any more.");
        alertDialog.setListener(null);
    }

    @OnClick(R.id.btn_save_edit_post)
    public void saveEdit() {
        String tile = title.getText().toString();
        String des = description.getText().toString();

        if (TextUtils.isEmpty(tile)) {
            alertDialog.showAlertDialog("Oop!", "Please enter the title for your item");
            return;
        }
        if (TextUtils.isEmpty(des)) {
            alertDialog.showAlertDialog("Oop!", "Please enter some description");
            return;
        }

        waitingDialog.showDialog();
        mPresenter.uploadPostToDatabase(tile, des);
    }

    @OnClick(R.id.edit_images)
    public void goEditImage() {

        Intent intent = new Intent(this, GalleryActivity.class);
        Params params = new Params();
        params.setCaptureLimit(6);
        params.setPickerLimit(6);
        params.setToolbarColor(getResources().getColor(R.color.colorPrimaryDark));
        params.setActionButtonColor(getResources().getColor(R.color.colorAccent));
        params.setButtonTextColor(getResources().getColor(R.color.white));
        intent.putExtra(com.vlk.multimager.utils.Constants.KEY_PARAMS, params);
        startActivityForResult(intent, com.vlk.multimager.utils.Constants.TYPE_MULTI_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case com.vlk.multimager.utils.Constants.TYPE_MULTI_PICKER:
                recyclerViewImage.setVisibility(View.VISIBLE);
                ArrayList<Image> imagesList = data.getParcelableArrayListExtra(com.vlk.multimager.utils.Constants.KEY_BUNDLE_LIST);
                mPresenter.addAllImage(imagesList);
                imagePickerAdapter.notifyDataSetChanged();
                mPresenter.startUploadImages();
                break;
            case SELECT_CODE:
                int selected = data.getIntExtra("selected", 8);
                tvCategory.setText(Utils.getTextFromIntCategory(selected));
                mPresenter.selectedCategory = selected;
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onPostDone() {
        waitingDialog.hideDialog();
        alertDialog.showAlertDialog("Done", "Click ok to back to previous screen");
        alertDialog.setListener(new CustomAlertDialog.AlertListener() {
            @Override
            public void onOkClicked() {
                onBackPressed();
            }
        });
    }

    @Override
    public void onUploadSingleImageDone() {
        imagePickerAdapter.notifyDataSetChanged();
    }

    @Override
    public void getPostDone(Post post) {

        scrollView.setVisibility(View.VISIBLE);
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        waitingDialog.hideDialog();
        postStatus.setText(mPresenter.getStatus());
        tvCategory.setText(Utils.getTextFromIntCategory(post.getCategory()));
    }

    public final static int SELECT_CODE = 101;

    @OnClick(R.id.tv_post_category)
    public void startSelectActivity() {
        Intent intent = new Intent(this, SelectCategoryActivity.class);
        startActivityForResult(intent, SELECT_CODE);
    }


    @Override
    public void getLinkDone() {
        imageSlideAdapter.notifyDataSetChanged();
    }

    @Override
    public void getPostFail() {
        alertDialog.showAlertDialog("Error", "Can not complete action");
    }
}

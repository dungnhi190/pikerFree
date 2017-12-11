package ha.thanh.pikerfree.activities.viewProfile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.viewPost.PostActivity;
import ha.thanh.pikerfree.adapters.PostAdapter;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.customviews.RatingDialog;
import ha.thanh.pikerfree.models.User;

public class ViewProfileActivity extends AppCompatActivity implements ViewProfileInterface.RequiredViewOps, PostAdapter.ItemClickListener {


    @BindView(R.id.op_status)
    ImageView opStatus;
    @BindView(R.id.rv_my_post)
    public RecyclerView rvPost;
    @BindView(R.id.profile_image)
    public CircleImageView userImage;
    @BindView(R.id.user_address)
    CustomTextView tvUserAddress;
    @BindView(R.id.user_name)
    CustomTextView tvUserName;
    @BindView(R.id.tv_loading_post)
    CustomTextView tvLoadingPost;
    @BindView(R.id.tv_no_data)
    CustomTextView tvNoData;
    @BindView(R.id.user_phone)
    CustomTextView tvPhone;
    @BindView(R.id.btn_follow)
    CustomTextView followBtn;
    @BindView(R.id.tv_rating_num)
    CustomTextView tvRateNum;
    private ViewProfilePresenter presenter;
    PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_conslapp);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {

        adapter = new PostAdapter(this,
                presenter.getPostList(),
                this, presenter.getUserLat(),
                presenter.getUserLng());
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPost.setLayoutManager(layoutManager);
        rvPost.setAdapter(adapter);

    }


    private void initData() {
        Intent intent = getIntent();
        String userId = intent.getStringExtra(Constants.USER_ID);
        presenter = new ViewProfilePresenter(this, this, userId);
    }

    @OnClick(R.id.btn_follow)
    public void followUser() {
        if(followBtn.getText().toString().equalsIgnoreCase("follow"))
        presenter.followUser();
        else presenter.unfollowUser();
    }

    @Override
    public void onUnFollowSuccess(String inform) {
        CustomAlertDialog alertDialog = new CustomAlertDialog(this);
        alertDialog.showAlertDialog("Success", inform);
        followBtn.setText("FOLLOW");
    }

    @Override
    public void onFollowSuccess(String inform) {

        CustomAlertDialog alertDialog = new CustomAlertDialog(this);
        alertDialog.showAlertDialog("Success", inform);
        followBtn.setText("UNFOLLOW");
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadAllMyPost();
    }

    @Override
    public void onGetUserDataDone(User user) {
        tvUserAddress.setText(user.getAddress());
        tvUserName.setText(user.getName());
        tvPhone.setText(user.getPhoneNumber());
        tvRateNum.setText(String.valueOf(user.getRating()));
        if (user.isOnline()) opStatus.setImageResource(R.drawable.bg_circle_check);
        else opStatus.setImageResource(R.drawable.bg_circle_gray);
    }

    @Override
    public void getOwnerImageDone(Uri uri) {
        Glide.with(this)
                .load(uri)
                .apply(new RequestOptions()
                        .error(R.drawable.action_button_bg)
                        .centerCrop()
                        .dontAnimate()
                        .override(150, 150)
                        .dontTransform())
                .into(userImage);
    }

    @Override
    public void onAlreadyFollow() {
        followBtn.setText("UNFOLLOW");
    }

    @Override
    public void onGetUserPostsDone() {
        rvPost.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);
        tvLoadingPost.setText("User's posts");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Intent in = new Intent(this, PostActivity.class);
        in.putExtra(Constants.POST_VIEW, presenter.getPostList().get(position).getPostId());
        startActivity(in);
    }

    @OnClick(R.id.rating)
    public void showRating() {
        RatingDialog ratingDialog = new RatingDialog(this, new RatingDialog.optionInterface() {
            @Override
            public void onReview(double rating) {
                presenter.updateRating(rating);
            }
        });
        ratingDialog.showRatingDialog();
    }


    @Override
    public void onRatingFail(String err) {
        CustomAlertDialog alertDialog = new CustomAlertDialog(this);
        alertDialog.showAlertDialog("Error", err);
    }

    @Override
    public void onRatingDone(double newrate) {
        tvRateNum.setText(String.valueOf(newrate));
        CustomAlertDialog alertDialog = new CustomAlertDialog(this);
        alertDialog.showAlertDialog("Done", "Successful");
    }
}

package ha.thanh.pikerfree.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.editProfile.EditProfileActivity;
import ha.thanh.pikerfree.activities.newPost.NewPostActivity;
import ha.thanh.pikerfree.activities.viewPost.PostActivity;
import ha.thanh.pikerfree.adapters.PostAdapter;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.models.User;

public class HomeFragment extends Fragment
        implements HomeInterface.RequiredViewOps, PostAdapter.ItemClickListener {
    @BindView(R.id.rv_my_post)
    public RecyclerView rvPost;
    @BindView(R.id.profile_image)
    public CircleImageView userImage;
    @BindView(R.id.user_name)
    CustomTextView tvUserName;
    @BindView(R.id.tv_loading_post)
    CustomTextView tvLoadingPost;
    @BindView(R.id.tv_no_data)
    CustomTextView tvNoData;
    private HomePresenter homePresenter;
    PostAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initView() {

        adapter = new PostAdapter(this.getContext(),
                homePresenter.getPostList(),
                this, homePresenter.getUserLat(),
                homePresenter.getUserLng());
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPost.setLayoutManager(layoutManager);
        rvPost.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {
        Intent in = new Intent(this.getContext(), PostActivity.class);
        in.putExtra(Constants.POST_VIEW, homePresenter.getPostList().get(position).getPostId());
        startActivity(in);
    }

    private void initData() {
        homePresenter = new HomePresenter(this.getContext(), this);
        homePresenter.getLocalData();
    }

    @OnClick(R.id.btn_new_post)
    public void doNewPost() {
        startActivity(new Intent(this.getContext(), NewPostActivity.class));
    }

    @OnClick(R.id.btn_edit_profile)
    public void editProfile() {
        startActivity(new Intent(this.getContext(), EditProfileActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        homePresenter.loadAllMyPost();
    }

    @Override
    public void onLocalDataReady(String name, String address, String filepath) {
        tvUserName.setText(name);
        File imgFile = new File(filepath);
        if (imgFile.exists()) {
            Glide.with(this)
                    .load(imgFile)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.loading)
                            .centerCrop()
                            .dontAnimate()
                            .override(160, 160)
                            .dontTransform())
                    .into(userImage);
        }
    }

    @Override
    public void onGetUserDataDone(User user) {
        // get user data from server. do nothing. lol
    }

    @Override
    public void onGetUserPostsDone() {
        rvPost.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);
        tvLoadingPost.setText("Your posts");
        adapter.notifyDataSetChanged();
    }
}

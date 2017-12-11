package ha.thanh.pikerfree.fragments.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.viewListPost.ViewListPostActivity;
import ha.thanh.pikerfree.constants.Constants;

public class NewsFragment extends Fragment
        implements NewsInterface.RequiredViewOps {

    NewsPresenter newsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        newsPresenter = new NewsPresenter(this.getContext(), this);
        initData();
        return view;
    }

    private void initData() {

    }

    @OnClick(R.id.accessories)
    public void goToAC() {
        goToList(Constants.CATE_ACCESSORY);
    }

    @OnClick(R.id.near_by)
    public void goToAC1() {
        goToList(Constants.CATE_NEAR_BY);
    }

    @OnClick(R.id.recent)
    public void goToAC2() {
        goToList(Constants.CATE_RECENT);
    }

    @OnClick(R.id.baby_and_toy)
    public void goToAC3() {
        goToList(Constants.CATE_BABY);
    }

    @OnClick(R.id.groceries)
    public void goToAC4() {
        goToList(Constants.CATE_GROCERY);
    }

    @OnClick(R.id.electronics)
    public void goToAC5() {
        goToList(Constants.CATE_ELECTRONIC);
    }

    @OnClick(R.id.cloths)
    public void goToAC6() {
        goToList(Constants.CATE_FASHION);
    }

    @OnClick(R.id.home_living)
    public void goToAC7() {
        goToList(Constants.CATE_HOME);
    }

    @OnClick(R.id.others)
    public void goToAC8() {
        goToList(Constants.CATE_OTHER);
    }

    @OnClick(R.id.pet)
    public void goToAC9() {
        goToList(Constants.CATE_PET);
    }


    private void goToList(int category) {
        Intent intent = new Intent(this.getContext(), ViewListPostActivity.class);
        intent.putExtra(Constants.CATEGORY, category);
        startActivity(intent);
    }

    @Override
    public void onGetPostsDone() {

    }

    @Override
    public void onGetPostsFailed() {

    }
}
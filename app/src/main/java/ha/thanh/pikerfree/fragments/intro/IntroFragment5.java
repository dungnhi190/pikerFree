package ha.thanh.pikerfree.fragments.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.login.LoginActivity;


public class IntroFragment5 extends IntroFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_5, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void animationViewPage() {

    }

    @Override
    public void disableAnimationViewPage() {

    }

    @OnClick(R.id.tv_do_test)
    public void onClickView(View view) {
        startMainActivity();
    }

    void startMainActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        getActivity().finish();
    }

}

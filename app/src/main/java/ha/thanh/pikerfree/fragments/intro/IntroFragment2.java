package ha.thanh.pikerfree.fragments.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;


public class IntroFragment2 extends IntroFragment {
    @BindView(R.id.iv_hand_phone)
    View viewHandPhone;
    @BindView(R.id.iv_finger)
    View viewFinger;
    @BindViews({R.id.iv_star1, R.id.iv_star2, R.id.iv_star3, R.id.iv_star4, R.id.iv_star5, R.id.iv_star6})
    List<View> viewsStar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void animationViewPage() {
        animationViewHandPhone();
        animationViewFinger();
        animationViewStar();
    }

    @Override
    public void disableAnimationViewPage() {
        if (viewHandPhone != null && viewFinger != null) {
            viewHandPhone.clearAnimation();
            viewFinger.clearAnimation();
            viewHandPhone.setVisibility(View.GONE);
            viewFinger.setVisibility(View.GONE);
            for (int i = 0; i < viewsStar.size(); i++) {
                viewsStar.get(i).clearAnimation();
                viewsStar.get(i).setAlpha(0);
            }
        }
    }


    private void animationViewHandPhone() {
        viewHandPhone.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(-viewHandPhone.getWidth(), 0, 0, 0);
        animate.setDuration(1000);
        animate.setFillAfter(true);
        viewHandPhone.startAnimation(animate);
    }

    private void animationViewFinger() {
        viewFinger.setVisibility(View.VISIBLE);
        TranslateAnimation animateHand = new TranslateAnimation(0, 0, viewFinger.getHeight(), 0);
        animateHand.setDuration(1000);
        animateHand.setFillAfter(true);
        viewFinger.startAnimation(animateHand);
    }


    private void animationViewStar() {
        viewsStar.get(0).animate().alpha(1).setDuration(300);
        viewsStar.get(1).animate().setStartDelay(200).alpha(1).setDuration(500);
        viewsStar.get(2).animate().setStartDelay(500).alpha(1).setDuration(500);
        viewsStar.get(3).animate().alpha(1).setDuration(300);
        viewsStar.get(4).animate().setStartDelay(200).alpha(1).setDuration(500);
        viewsStar.get(5).animate().setStartDelay(500).alpha(1).setDuration(500);
    }

}

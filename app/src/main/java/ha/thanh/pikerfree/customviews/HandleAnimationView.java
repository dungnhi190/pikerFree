package ha.thanh.pikerfree.customviews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by HaVan on 6/1/2017.
 */
public class HandleAnimationView {
    public void slideExitView(final View currentView, final View comingView) {
        comingView.setTranslationX(comingView.getWidth());
        currentView.animate().translationXBy(-currentView.getWidth()).
                setDuration(500).
                setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        slideShowView(comingView);
                        comingView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        currentView.setVisibility(View.INVISIBLE);
                    }
                });
    }

    public void slideShowView(View v) {
        v.animate().translationX(0).setDuration(500).
                setInterpolator(new LinearInterpolator());
    }

    public void animationFromXToY(View viewX, View viewY) {
        viewX.startAnimation(outToLeftAnimation());
        viewX.setVisibility(View.GONE);
        viewY.startAnimation(inFromRightAnimation());
        viewY.setVisibility(View.VISIBLE);
    }

    private Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(500);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }


    private Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(500);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }
}

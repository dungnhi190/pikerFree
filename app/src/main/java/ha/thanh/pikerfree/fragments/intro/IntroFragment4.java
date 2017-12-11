package ha.thanh.pikerfree.fragments.intro;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindViews;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;


public class IntroFragment4 extends IntroFragment {
    @BindViews({R.id.iv_planes, R.id.iv_tree, R.id.iv_animal, R.id.iv_football, R.id.iv_guitar})
    View[] ivImages;
    int currentPosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_4, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void animationViewPage() {
        animationView();
    }

    @Override
    public void disableAnimationViewPage() {
        for (int i = 0; i < ivImages.length; i++) {
            ivImages[i].clearAnimation();
            ivImages[i].setVisibility(View.GONE);
        }
    }


    private void animationView() {
        currentPosition = 0;

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (currentPosition >= 0 && currentPosition < ivImages.length) {
                    Animation zoom = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
                    ivImages[currentPosition].setVisibility(View.VISIBLE);
                    ivImages[currentPosition].setAnimation(zoom);
                    currentPosition++;
                    handler.postDelayed(this, 300);
                }
            }
        });
    }
}

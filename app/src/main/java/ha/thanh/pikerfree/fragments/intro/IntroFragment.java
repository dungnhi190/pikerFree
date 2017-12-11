package ha.thanh.pikerfree.fragments.intro;

import android.os.Handler;
import android.support.v4.app.Fragment;


public abstract class IntroFragment extends Fragment {

    public void startAnimation() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animationViewPage();
            }
        }, 200);

    }

    public abstract void animationViewPage();

    public abstract void disableAnimationViewPage();

}

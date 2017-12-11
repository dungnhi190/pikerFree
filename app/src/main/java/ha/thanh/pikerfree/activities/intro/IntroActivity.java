package ha.thanh.pikerfree.activities.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.ViewPagerAdapter;
import ha.thanh.pikerfree.fragments.intro.IntroFragment;
import ha.thanh.pikerfree.fragments.intro.IntroFragment1;
import ha.thanh.pikerfree.fragments.intro.IntroFragment2;
import ha.thanh.pikerfree.fragments.intro.IntroFragment3;
import ha.thanh.pikerfree.fragments.intro.IntroFragment4;
import ha.thanh.pikerfree.fragments.intro.IntroFragment5;


public class IntroActivity extends AppCompatActivity {
    @BindView(R.id.vp_intro)
    public ViewPager vpIntro;
    private List<Fragment> introFragments;
    @BindView(R.id.layout_count_dot)
    public LinearLayout pager_indicator;
    @BindView(R.id.view_title)
    View viewTitle;
    @BindView(R.id.img_next)
    public ImageView imgNext;
    @BindView(R.id.img_back)
    public ImageView imgBack;

    private ImageView[] dots;
    private int currentPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        createData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createData() {
        introFragments = new ArrayList<>();
        IntroFragment1 introFragment1 = new IntroFragment1();
        IntroFragment2 introFragment2 = new IntroFragment2();
        IntroFragment3 introFragment3 = new IntroFragment3();
        IntroFragment4 introFragment4 = new IntroFragment4();
        IntroFragment5 introFragment5 = new IntroFragment5();
        introFragments.add(introFragment1);
        introFragments.add(introFragment2);
        introFragments.add(introFragment3);
        introFragments.add(introFragment4);
        introFragments.add(introFragment5);
    }

    private void initView() {
        setUiPageViewController();
        final ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), introFragments);
        vpIntro.setAdapter(pagerAdapter);
        vpIntro.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void pageSelected(int position) {
        currentPosition = position;
        switchDot();
        animationIntroFragment();
    }


    private void animationIntroFragment() {
        for (int i = 0; i < introFragments.size(); i++) {
            if (i == currentPosition) {
                ((IntroFragment) introFragments.get(currentPosition)).startAnimation();
            } else {
                ((IntroFragment) introFragments.get(currentPosition)).disableAnimationViewPage();
            }
        }


    }

    private void switchDot() {
        for (int i = 0; i < introFragments.size(); i++) {
            if (i == currentPosition)
                dots[i].setImageResource(R.drawable.seclected_dot);
            else
                dots[i].setImageResource(R.drawable.none_seclected_dot);
        }

    }

    private void setUiPageViewController() {
        dots = new ImageView[introFragments.size()];
        for (int i = 0; i < introFragments.size(); i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.none_seclected_dot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(6, 0, 6, 0);
            pager_indicator.addView(dots[i], params);
        }
        dots[0].setImageResource(R.drawable.seclected_dot);
    }

    @OnClick(R.id.img_next)
    public void btnNextClicked() {
        if (currentPosition < introFragments.size() - 1)
            vpIntro.setCurrentItem(currentPosition + 1);
    }

    @OnClick(R.id.img_back)
    public void backClicked() {
        if (currentPosition > 0)
            vpIntro.setCurrentItem(currentPosition - 1);
    }

}

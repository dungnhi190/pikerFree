package ha.thanh.pikerfree.activities.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.intro.IntroActivity;
import ha.thanh.pikerfree.activities.login.LoginActivity;
import ha.thanh.pikerfree.activities.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements SplashInterface.RequiredViewOps {

    private static final int LOAD_SUCCESS = 1;
    private static final int LOAD_ERROR = 2;
    private Handler handler = new Handler();
    private boolean isFirstRun = true;
    private int statusLoadLanguage;
    private SplashPresenter mPresenter;
    @BindView(R.id.tv_network_error)
    TextView tvNetworkError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mPresenter = new SplashPresenter(SplashActivity.this, this);
        isFirstRun = mPresenter.isFirstRun();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFirstRun) {
                if (statusLoadLanguage == LOAD_SUCCESS) {
                    startLoginActivity();
                } else if (statusLoadLanguage == LOAD_ERROR) {
                    tvNetworkError.setText(getResources().getString(R.string.turn_network_on));
                } else {
                    handler.postDelayed(this, 100);
                }
            } else {
                startIntroActivity();
                mPresenter.setIsFirstRun();
            }
        }
    };


    private void startIntroActivity() {
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void onLoadConfigDone() {
        statusLoadLanguage = LOAD_SUCCESS;
    }

    @Override
    public void onNetworkFail() {
        statusLoadLanguage = LOAD_ERROR;
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000);
    }
}

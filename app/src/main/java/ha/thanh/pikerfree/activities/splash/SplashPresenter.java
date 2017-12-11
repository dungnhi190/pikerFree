package ha.thanh.pikerfree.activities.splash;

import android.content.Context;
import android.net.ConnectivityManager;

import ha.thanh.pikerfree.constants.Globals;


/**
 * Created by HaVan on 5/24/2017.
 */

class SplashPresenter implements SplashInterface.RequiredPresenterOps {
    private SplashInterface.RequiredViewOps mView;
    private SplashModel mModel;
    private Context context;

    SplashPresenter(Context context, SplashInterface.RequiredViewOps mView) {
        this.mView = mView;
        this.context = context;
        mModel = new SplashModel(context, this);
        checkNetwork();
    }

    private void checkNetwork() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null)
            mView.onNetworkFail();
    }

    boolean isFirstRun() {
        return Globals.getIns().getConfig().isFirstRun();
    }

    void setIsFirstRun() {
        mModel.setIsFirstRun();
    }


    @Override
    public void loadConfigDone() {
        mView.onLoadConfigDone();
    }


}

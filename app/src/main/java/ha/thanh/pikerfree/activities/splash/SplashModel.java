package ha.thanh.pikerfree.activities.splash;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.constants.Globals;

/**
 * Created by HaVan on 5/24/2017.
 */

class SplashModel {

    private Context context;
    private SharedPreferences sPref;
    private SplashInterface.RequiredPresenterOps mPresenter;

    SplashModel(Context context, SplashInterface.RequiredPresenterOps mPresenter) {
        this.context = context;
        this.mPresenter = mPresenter;
        sPref = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
        loadAllConfig();
    }

    private void loadAllConfig() {
        boolean isFirstRun = sPref.getBoolean(Constants.IS_FIRST_RUN, true);
        Globals.getIns().getConfig().setFirstRun(isFirstRun);
        mPresenter.loadConfigDone();
    }

    public void setIsFirstRun() {
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(Constants.IS_FIRST_RUN, false);
        editor.apply();
    }
}

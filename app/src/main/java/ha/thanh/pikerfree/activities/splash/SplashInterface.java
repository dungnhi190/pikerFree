package ha.thanh.pikerfree.activities.splash;

/**
 * Created by HaVan on 5/24/2017.
 */

interface SplashInterface {

    interface RequiredViewOps {
        void onLoadConfigDone();

        void onNetworkFail();
    }

    interface RequiredPresenterOps {
        void loadConfigDone();
    }
}

package ha.thanh.pikerfree.activities.login;

/**
 * Created by HaVan on 9/10/2017.
 */

public interface LoginInterface {

    interface RequiredViewOps {
        void onHideWaitingDialog();

        void onLogInSuccess();

        void onPasswordWeek();

        void onShowAlert(String title, String message);
    }

    interface RequiredPresenterOps {

    }
}

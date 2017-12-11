package ha.thanh.pikerfree.activities.signup;

/**
 * Created by HaVan on 9/10/2017.
 */

public interface SignUpInterface {

    interface RequiredViewOps {
        void onHideWaiting();

        void onShowInforDialog(String title, String mess);

        void onDoneProcess();

    }
}

package ha.thanh.pikerfree.activities.viewProfile;

import android.net.Uri;

import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 11/29/2017.
 */

public class ViewProfileInterface {
    interface RequiredViewOps {

        void getOwnerImageDone(Uri uri);

        void onGetUserDataDone(User user);

        void onGetUserPostsDone();

        void onRatingFail(String err);

        void onRatingDone(double newrate);

        void onFollowSuccess(String inform);

        void onUnFollowSuccess(String inform);

        void onAlreadyFollow();
    }
}

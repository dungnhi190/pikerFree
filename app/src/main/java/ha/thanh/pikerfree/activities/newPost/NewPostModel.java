package ha.thanh.pikerfree.activities.newPost;

import android.content.Context;

import ha.thanh.pikerfree.services.GPSTracker;

/**
 * Created by HaVan on 8/23/2017.
 */

public class NewPostModel {

    private GPSTracker gpsTracker;

    NewPostModel(Context context) {
        this.gpsTracker = new GPSTracker(context);
    }

    double getUserLat() {
        return gpsTracker.getLatitude();
    }

    double getUserLng() {
        return gpsTracker.getLongitude();
    }

}

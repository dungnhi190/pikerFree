package ha.thanh.pikerfree.activities.viewListPost;

import android.content.Context;

import ha.thanh.pikerfree.services.GPSTracker;

class ViewListPostModel {
    private GPSTracker gpsTracker;

    ViewListPostModel(Context context) {
        this.gpsTracker = new GPSTracker(context);

    }

    double getUserLat() {
        return gpsTracker.getLatitude();
    }

    double getUserLng() {
        return gpsTracker.getLongitude();
    }

}

package ha.thanh.pikerfree.activities.viewPost;


import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.services.GPSTracker;

class PostModel {
    private GPSTracker gpsTracker;
    private SharedPreferences sharedPreferences;

    PostModel(Context context) {
        this.gpsTracker = new GPSTracker(context);
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }

    String getUserIdFromSharePref() {
        return sharedPreferences.getString(Constants.USER_ID, "");

    }
    String getLocalImageStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_PROFILE_PIC_PATH, "");
    }

    double getUserLat() {
        return gpsTracker.getLatitude();
    }

    double getUserLng() {
        return gpsTracker.getLongitude();
    }

}

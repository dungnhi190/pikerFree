package ha.thanh.pikerfree.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.services.GPSTracker;

class HomeModel {
    private SharedPreferences sharedPreferences;
    private GPSTracker gpsTracker;

    HomeModel(Context context) {
        this.gpsTracker = new GPSTracker(context);
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);

    }

    String getUserNameStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_NAME, "");
    }

    String getUserAddressStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_ADDRESS, "");

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

    String getUserIdFromSharePf() {
        return sharedPreferences.getString(Constants.USER_ID, "");
    }
}

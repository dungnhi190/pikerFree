package ha.thanh.pikerfree.activities.editPost;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.services.GPSTracker;

/**
 * Created by HaVan on 10/16/2017.
 */

public class EditPostModel {
    private GPSTracker gpsTracker;
    private SharedPreferences sharedPreferences;

    EditPostModel(Context context) {
        this.gpsTracker = new GPSTracker(context);
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }

    String getUserIdFromSharePref() {
        return sharedPreferences.getString(Constants.USER_ID, "");

    }

    double getUserLat() {
        return gpsTracker.getLatitude();
    }

    double getUserLng() {
        return gpsTracker.getLongitude();
    }

}

package ha.thanh.pikerfree.activities.editProfile;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;

/**
 * Created by HaVan on 9/10/2017.
 */

public class EditProfileModel {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    EditProfileModel(Context context) {

        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }

    String getUserNameStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_NAME, "");

    }

    String getUserPhoneFromSharePf() {
        return sharedPreferences.getString(Constants.USER_PHONE, "");

    }

    String getUserAddressStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_ADDRESS, "");

    }

    String getLocalImageStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_PROFILE_PIC_PATH, "");
    }

    String getUserIdFromSharePf() {
        return sharedPreferences.getString(Constants.USER_ID, "");
    }

    void saveLocal(String username, String userAddress, String path, String userPhone) {
        editor = sharedPreferences.edit();
        editor.putString(Constants.USER_NAME, username);
        editor.putString(Constants.USER_ADDRESS, userAddress);
        editor.putString(Constants.USER_PROFILE_PIC_PATH, path);
        editor.putString(Constants.USER_PHONE, userPhone);
        editor.apply();

    }
}

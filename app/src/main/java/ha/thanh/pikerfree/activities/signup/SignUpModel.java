package ha.thanh.pikerfree.activities.signup;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;

class SignUpModel {
    private SharedPreferences sharedPreferences;


    SignUpModel(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }

    void saveLocal(String username, String userId) {
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putString(Constants.USER_NAME, username);
        editor.putString(Constants.USER_ID, userId);
        editor.apply();
    }
}

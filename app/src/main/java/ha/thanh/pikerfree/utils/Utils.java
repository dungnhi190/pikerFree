package ha.thanh.pikerfree.utils;

import android.location.Location;
import android.text.format.DateFormat;
import android.util.Log;


import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import ha.thanh.pikerfree.constants.Constants;

public class Utils {


    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    public static String getTextFromIntCategory(int intput) {
        switch (intput) {
            case Constants.CATE_ACCESSORY:
                return "ACCESSORIES";
            case Constants.CATE_BABY:
                return "BABY AND TOYS";
            case Constants.CATE_ELECTRONIC:
                return "ELECTRONIC";
            case Constants.CATE_FASHION:
                return "FASHION";
            case Constants.CATE_GROCERY:
                return "GROCERIES";
            case Constants.CATE_HOME:
                return "HOME AND STUFFS";
            case Constants.CATE_OTHER:
                return "OTHERS";
            case Constants.CATE_PET:
                return "PETS";
            case Constants.CATE_RECENT:
                return "RECENT";
            case Constants.CATE_NEAR_BY:
                return "NEAR BY";
            default:
                return "OTHERS";
        }
    }

    public static void sendNotificationToUser(String user, final String message) {
        Firebase ref = new Firebase(FirebaseDatabase.getInstance().toString());
        final Firebase notifications = ref.child("notificationRequests");

        Map notification = new HashMap<>();
        notification.put("username", user);
        notification.put("message", message);

        notifications.push().setValue(notification);
    }
    public static String getTimeString(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = calendar.getTimeZone();
            calendar.setTimeInMillis(timestamp);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date currentTimeZone = calendar.getTime();
            return sdf.format(currentTimeZone);
        } catch (Exception e) {
            Log.e("Utils", e.getMessage());
        }
        return "";
    }

    public static String getTimeInHour(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm";
        final String dateTimeFormatString = "dd-MM-yyyy, h:mm";
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("dd-MM-yyyy, h:mm", smsTime).toString();
        }
    }

    public static String getDistance(final double lat1, final double lon1, final double lat2, final double lon2) {

        if (lat1 == 0 || lat2 == 0) return "Unknown";
        Location l1 = new Location("One");
        l1.setLatitude(lat1);
        l1.setLongitude(lon1);

        Location l2 = new Location("Two");
        l2.setLatitude(lat2);
        l2.setLongitude(lon2);

        float distance = l1.distanceTo(l2);
        int distanceInt = (Math.round(distance) / 100) * 100;
        String dist = distanceInt + " m";

        if (distanceInt > 1000) {
            double roundOff = Math.round(distance) / 1000;
            dist = roundOffTo2DecPlaces(roundOff) + " km";
        }
        return dist;
    }

    private static String roundOffTo2DecPlaces(double val) {
        return String.format(Locale.getDefault(), "%.2f", val);
    }

}

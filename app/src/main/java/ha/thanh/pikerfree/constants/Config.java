package ha.thanh.pikerfree.constants;


public class Config {

    private boolean isPermissionWriteFile = false;
    private boolean isPermissionCamera = false;
    private boolean isPermissionLocation = false;

    private boolean isFirstRun = true;
    private int lastDay = 7;
    private int distanceSearch = 7;
    private boolean notificationMess = true;
    private boolean notificationPost = true;

    final public static String CLUSTERURL = "http://ortc-developers.realtime.co/server/2.1/";
    final public static String URL = null;
    final public static String METADATA = "androidApp";
    final public static String TOKEN = "token";
    final public static String APPKEY = "YOUR_APPLICATION_KEY";
    final public static String PROJECT_ID = "YOUR_FIREBASE_SENDER_ID";


    final public static int CHANNEL_NAME = 1;
    final public static int CHANNEL_ADD = 2;
    final public static int CHANNEL_DEL = 3;
    final public static int MSG_SIZE = 260;

    final public static String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public int getLastDay() {
        return lastDay;
    }

    public void setLastDay(int lastDay) {
        this.lastDay = lastDay;
    }

    public int getDistanceSearch() {
        return distanceSearch;
    }

    public void setDistanceSearch(int distanceSearch) {
        this.distanceSearch = distanceSearch;
    }

    public boolean isNotificationMess() {
        return notificationMess;
    }

    public void setNotificationMess(boolean notificationMess) {
        this.notificationMess = notificationMess;
    }

    public boolean isNotificationPost() {
        return notificationPost;
    }

    public void setNotificationPost(boolean notificationPost) {
        this.notificationPost = notificationPost;
    }

    Config() {

    }


    public boolean isFirstRun() {
        return isFirstRun;
    }

    public void setFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
    }


    public boolean isPermissionWriteFile() {
        return isPermissionWriteFile;
    }

    public void setPermissionWriteFile(boolean permissionWriteFile) {
        isPermissionWriteFile = permissionWriteFile;
    }

    public boolean isPermissionCamera() {
        return isPermissionCamera;
    }

    public void setPermissionCamera(boolean permissionCamera) {
        isPermissionCamera = permissionCamera;
    }

    public boolean isPermissionLocation() {
        return isPermissionLocation;
    }

    public void setPermissionLocation(boolean permissionLocation) {
        isPermissionLocation = permissionLocation;
    }
}


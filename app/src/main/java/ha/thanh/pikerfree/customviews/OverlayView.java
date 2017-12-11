package ha.thanh.pikerfree.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class OverlayView extends View implements SensorEventListener, LocationListener {

    public static final String DEBUG_TAG = "OverlayView Log";
    String accelData = "Accelerometer Data";
    String compassData = "Compass Data";
    String gyroData = "Gyro Data";
    Paint contentPaint;
    SensorManager sensors;

    float[] lastAccelerometer = new float[3];
    float[] lastCompass = new float[3];
    float rotation[] = new float[9];
    float identity[] = new float[9];
    float cameraRotation[] = new float[9];
    float orientation[] = new float[3];
    float curBearingToMW;
    LocationManager locationManager;

    private Location lastLocation = null;

    public OverlayView(Context context) {
        super(context);

        contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        sensors = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gyroSensor = sensors.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensors.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensors.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensors.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);

        String best = locationManager.getBestProvider(criteria, true);

        Log.v(DEBUG_TAG, "Best provider: " + best);

        try {
            locationManager.requestLocationUpdates(best, 50, 0, this);
        } catch (SecurityException e) {
            Log.e(DEBUG_TAG, e.getMessage());
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        contentPaint.setTextAlign(Paint.Align.CENTER);
        contentPaint.setTextSize(20);
        contentPaint.setColor(Color.RED);
        canvas.drawText(accelData, canvas.getWidth() / 2, canvas.getHeight() / 4, contentPaint);
        canvas.drawText(compassData, canvas.getWidth() / 2, canvas.getHeight() / 2, contentPaint);
        canvas.drawText(gyroData, canvas.getWidth() / 2, (canvas.getHeight() * 3) / 4, contentPaint);
        boolean gotRotation = SensorManager.getRotationMatrix(rotation,
                identity, lastAccelerometer, lastCompass);
        if (gotRotation) {

            SensorManager.remapCoordinateSystem(rotation, SensorManager.AXIS_X,
                    SensorManager.AXIS_Z, cameraRotation);

            SensorManager.getOrientation(cameraRotation, orientation);

            SensorManager.remapCoordinateSystem(rotation, SensorManager.AXIS_X,
                    SensorManager.AXIS_Z, cameraRotation);
            SensorManager.getOrientation(cameraRotation, orientation);
            Camera.Parameters params;
            try {
                params = Camera.open().getParameters();
                float verticalFOV = params.getVerticalViewAngle();
                float horizontalFOV = params.getHorizontalViewAngle();
                // use roll for screen rotation
                canvas.rotate((float) (0.0f - Math.toDegrees(orientation[2])));
                // Translate, but normalize for the FOV of the camera -- basically, pixels per degree, times degrees == pixels
                float dx = (float) ((canvas.getWidth() / horizontalFOV) * (Math.toDegrees(orientation[0]) - curBearingToMW));
                float dy = (float) ((canvas.getHeight() / verticalFOV) * Math.toDegrees(orientation[1]));

                // wait to translate the dx so the horizon doesn't get pushed off
                canvas.translate(0.0f, 0.0f - dy);

                // now translate the dx
                canvas.translate(0.0f - dx, 0.0f);
            } catch (Exception e) {
                Log.e(DEBUG_TAG, "Failed to open Camera");
                e.printStackTrace();
            }


            // make our line big enough to draw regardless of rotation and translation
            canvas.drawLine(0f - canvas.getHeight(),
                    canvas.getHeight() / 2,
                    canvas.getWidth() + canvas.getHeight(),
                    canvas.getHeight() / 2, contentPaint);


            // draw our point -- we've rotated and translated this to the right spot already
            canvas.drawCircle(canvas.getWidth() / 2,
                    canvas.getHeight() / 2,
                    8.0f, contentPaint);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        StringBuilder msg = new StringBuilder(event.sensor.getName()).append(" ");

        for (float value : event.values) {
            msg.append(" - ").append(value).append(" - ");
        }

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                System.arraycopy(event.values, 0, lastAccelerometer, 0, 3);
                accelData = msg.toString();
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyroData = msg.toString();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                System.arraycopy(event.values, 0, lastCompass, 0, 3);
                compassData = msg.toString();
                break;
        }
        this.invalidate();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        curBearingToMW = lastLocation.bearingTo(mountWashington);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private final static Location mountWashington = new Location("manual");

    static {
        mountWashington.setLatitude(10.876288d);
        mountWashington.setLongitude(106.807727);
        mountWashington.setAltitude(25.5d);  // the height to sea levels
    }
}

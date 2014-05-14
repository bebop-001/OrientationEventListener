package com.example.orientationeventlistener;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static TextView orientation;
	private static OrientationEventListener orientationListener;
	public static int rotation = 0;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        final Display display 
        	= ((WindowManager) getSystemService(WINDOW_SERVICE))
        		.getDefaultDisplay();

        orientation = (TextView)findViewById(R.id.orientation);

        orientationListener = new OrientationEventListener(
            this, SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int angle) {
                rotation = display.getRotation();
                String orientName;
                if (rotation == Surface.ROTATION_0) {
                    orientName = "ROTATION_0";
                }
                else if (rotation == Surface.ROTATION_90) {
                    orientName = "ROTATION_90";
                }
                else if (rotation == Surface.ROTATION_180) {
                    orientName = "ROTATION_180";
                }
                else {
                    orientName = "ROTATION_270";
                }
                orientation.setText(
                    String.format("Orientation %3d: %s", angle, orientName));
            }
        };

        if (orientationListener.canDetectOrientation()){
            orientationListener.enable();
        }
        else{
            Toast.makeText(this,
            "Can not Detect Orientation", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orientationListener.disable();
    }
}

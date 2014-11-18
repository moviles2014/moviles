package parcados.sensors;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Compass extends Activity{

	private static float azimuth_angle;
	private static SensorManager mSensorManager;

	private final static SensorEventListener mSensorListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent event) {
			azimuth_angle = event.values[0];
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	
	

	public static void startCompass (SensorManager sensorManager) { 
		mSensorManager = sensorManager;
		mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
	}

	public static void pauseCompass (  ) { 
		mSensorManager.unregisterListener(mSensorListener);
	}

	public static float getAzimuth()
	{
		return azimuth_angle;
	}
}

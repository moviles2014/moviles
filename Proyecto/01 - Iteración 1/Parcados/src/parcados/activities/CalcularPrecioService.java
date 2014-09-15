package parcados.activities;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CalcularPrecioService extends Service {

	/**
	 * Al hacer bind
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	/**
	 * Cuando se inicia
	 */
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	/**
	 * Cuando se destruye
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}

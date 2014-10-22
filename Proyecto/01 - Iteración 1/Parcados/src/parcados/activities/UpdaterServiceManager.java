package parcados.activities;

import java.util.Timer;
import java.util.TimerTask;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class UpdaterServiceManager extends Service {


	//--------------------------------------------------------------------------------------
	// Constantes
	//--------------------------------------------------------------------------------------

	/**
	 * Intervalo de actualización
	 */
	private final int UPDATE_INTERVAL = 1000;
	/**
	 * Notificación
	 */
	private static final int NOTIFICATION_EX = 1;

	//--------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------

	/**
	 * Atributos del service
	 */
	private  Timer timer = new Timer();
	private static NotificationManager notificationManager;
	private  static int precio  ; 
	private static int precioActual ; 
	private static boolean running  ;
	private static int count = 0  ; 
	private static int notificationCount = 60 ; 


	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------


	/**
	 * Cuando se crea
	 */
	@Override
	public void onCreate() {
		// code to execute when the service is first created
		System.out.println( "created service");
		running = true ;
		precioActual = 0 ; 
		count = 0 ; 
	}

	/**
	 * Cuando se destruye
	 */
	@Override
	public void onDestroy() {
		if (timer != null) {
			timer.cancel();
		}
	}

	/**
	 * Al iniciar el servicio
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		running = true; 
		notificationManager = (NotificationManager) 
				getSystemService(Context.NOTIFICATION_SERVICE);


		timer.scheduleAtFixedRate(new TimerTask() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {

				count%= notificationCount ; 
				if ( running ) {

					//notificacion cada minuto
					if ( count == 0 ) { 
						precioActual +=precio ; 
						int icon = R.drawable.ic_menu_info_details ;

						CharSequence tickerText = "Parcados - Precio parqueadero: $" + precioActual ;
						long when = System.currentTimeMillis();
						Notification notification = new Notification(icon, tickerText, when);
						Context context = getApplicationContext();
						CharSequence contentTitle = "Parcados";

						CharSequence contentText = "Precio parqueadero: $" + precioActual;
						Intent notificationIntent = new Intent( UpdaterServiceManager.this , CalculadoraActivity.class);
						PendingIntent contentIntent = PendingIntent.getActivity(UpdaterServiceManager.this, 0,
								notificationIntent, 0);
						notification.setLatestEventInfo(context, contentTitle, contentText,
								contentIntent);		           
						startForeground(NOTIFICATION_EX, notification);
					}

				}
				else {
					stopForeground(true) ;
					stopSelf() ; 
				}
				count ++ ; 
			}
		}, 0, UPDATE_INTERVAL);


		return START_NOT_STICKY ; 
	}

	/**
	 * Da el precio
	 * @return el precio
	 */
	public static double getPrecio() {
		return precio;
	}

	/**
	 * Cambia el precio
	 * @param precio el precio nuevo
	 */
	public static void setPrecio(int precio) {
		UpdaterServiceManager.precio = precio;
	}

	/**
	 * Detiene el servicio
	 */
	public static  void stopService() {
		running = false ;
		precio =0 ;
		precioActual  =0 ; 
		count = 0 ; 
		notificationManager.cancel(NOTIFICATION_EX) ; 
	}

	/**
	 * Indica si el servicio está corriendo
	 * @return true si sí, false en caso contrario
	 */
	public static boolean isRunning() {
		return running;
	}

	/**
	 * Asigna un valor al atributo running
	 * @param running
	 */
	public static void setRunning(boolean running) {
		UpdaterServiceManager.running = running;
	}


	/**
	 * Establece el bind
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	/**
	 * Da el precio actual
	 * @return el precio actual
	 */
	public static int getPrecioActual() {
		return precioActual;
	}
}
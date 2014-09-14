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
import android.widget.Toast;

public class UpdaterServiceManager extends Service {

    private final int UPDATE_INTERVAL = 60000;
    private  Timer timer = new Timer();
    private static final int NOTIFICATION_EX = 1;
    private static NotificationManager notificationManager;

    private UpdaterServiceManager yo  ;
    private  static int precio  ; 

	private static int precioActual ; 
    
    private static boolean running  ;

	public UpdaterServiceManager() {}



    @Override
    public void onCreate() {
        // code to execute when the service is first created
    	System.out.println( "created service");
    	running = true ;
    	precioActual = 0 ; 
    	yo = this ; 
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
        	System.out.println( " entro ");
            timer.cancel();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
    	running = true; 
        notificationManager = (NotificationManager) 
                getSystemService(Context.NOTIFICATION_SERVICE);
       
        
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
            	
            	
            	if ( running ) {
	                // Check if there are updates here and notify if true
//	            	System.out.println( precio );
            		 precioActual +=precio ; 
	            	 int icon = R.drawable.ic_menu_info_details ;
	                 
	                 CharSequence tickerText = "El precio actual es: $" + precioActual ;
	                 long when = System.currentTimeMillis();
	                Notification notification = new Notification(icon, tickerText, when);
	                Context context = getApplicationContext();
	                CharSequence contentTitle = "El precio actual es:";
	               
	                CharSequence contentText = "$" + precioActual;
	                Intent notificationIntent = new Intent( yo , CalculadoraActivity.class);
	                PendingIntent contentIntent = PendingIntent.getActivity(yo, 0,
	                        notificationIntent, 0);
	                notification.setLatestEventInfo(context, contentTitle, contentText,
	                        contentIntent);
	               
//	                notificationManager.notify(NOTIFICATION_EX, notification);
//	                notification.flags|=Notification.FLAG_NO_CLEAR;
	                startForeground(NOTIFICATION_EX, notification);
	                
            	}
            	else {
            		stopForeground(true ) ;
            		stopSelf() ; 
            	}
            }
        }, 0, UPDATE_INTERVAL);
        
       
        return START_NOT_STICKY ; 
    }
    
    public static double getPrecio() {
		return precio;
	}



	public static void setPrecio(int precio) {
		UpdaterServiceManager.precio = precio;
	}
    
    public static  void stopService() {
//    	System.out.println( " entro coleto ");p
//    	stopForeground(true);
    	running = false ;
    	precio =0 ;
    	precioActual  =0 ; 
    	notificationManager.cancel(NOTIFICATION_EX) ; 
//        if (timer != null) timer.cancel();
    }
    
    public static boolean isRunning() {
		return running;
	}



	public static void setRunning(boolean running) {
		UpdaterServiceManager.running = running;
	}
    

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
//		System.out.println(  " entro en binder ");
		return null;
	}



	public static int getPrecioActual() {
		return precioActual;
	}
}
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

    private final int UPDATE_INTERVAL = 10000;
    private  Timer timer = new Timer();
    private static final int NOTIFICATION_EX = 1;
    private static NotificationManager notificationManager;

    private UpdaterServiceManager yo  ;
    public  static double precio = 0 ; 
    
    public static boolean running  ;
    public UpdaterServiceManager() {}



    @Override
    public void onCreate() {
        // code to execute when the service is first created
    	System.out.println( "created service");
    	running = true ; 
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
	            	precio ++ ; 
//	            	System.out.println( precio );
	            	
	            	 int icon = R.drawable.ic_menu_info_details ;
	                 
	                 CharSequence tickerText = "El precio actual es: " + precio ;
	                 long when = System.currentTimeMillis();
	                Notification notification = new Notification(icon, tickerText, when);
	                Context context = getApplicationContext();
	                CharSequence contentTitle = "El precio actual es:";
	                CharSequence contentText = "$" + precio;
	                Intent notificationIntent = new Intent( yo , CalculadoraActivity.class);
	                PendingIntent contentIntent = PendingIntent.getActivity(yo, 0,
	                        notificationIntent, 0);
	                notification.setLatestEventInfo(context, contentTitle, contentText,
	                        contentIntent);
	               
//	                notificationManager.notify(NOTIFICATION_EX, notification);
//	                notification.flags|=Notification.FLAG_NO_CLEAR;
	                startForeground(NOTIFICATION_EX, notification);
//	                startfor
	                
            	}
            	else {
            		stopForeground(true ) ;
            		stopSelf() ; 
            	}
            }
        }, 0, UPDATE_INTERVAL);
        
       
        return START_NOT_STICKY ; 
    }
    
    
    public static  void stopService() {
//    	System.out.println( " entro coleto ");
//    	stopForeground(true);
    	System.out.println( " se llamo ");
    	running = false ;
    	UpdaterServiceManager.precio =0.0 ; 
    	notificationManager.cancel(NOTIFICATION_EX) ; 
//        if (timer != null) timer.cancel();
    }
    

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
//		System.out.println(  " entro en binder ");
		return null;
	}
}
package parcados.receivers;
import parcados.services.BackgroundService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
 
public class NetworkChangeReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
        boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        if ( !noConnectivity){
        	BackgroundService.subscribe() ; 
        	System.out.println( "del receiver entro ");
        }
    } 
}
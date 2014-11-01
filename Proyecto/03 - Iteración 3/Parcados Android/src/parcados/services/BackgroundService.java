package parcados.services;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import parcados.activities.MyApplication;
import parcados.mundo.Parcados;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BackgroundService  extends Service { 
	
	public  static boolean running  ;
	public static HashMap<String, Boolean > hm  = null; 

	static  Pubnub pubnub = new Pubnub("pub-c-08e15781-225a-4935-a61f-bcafefa86481", "sub-c-414c745c-5273-11e4-a191-02ee2ddab7fe");
	
	public void onCreate() {
		// code to execute when the service is first created
		running = true ;
		System.out.println( "created service");
		subscribe() ;
		
	}
	
	
	public static void actualizarParqueadero ( String json) {
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			String nombre = null; 
			int precio = 0 , cupos = 0 ; 
			nombre = obj.get("nombre").toString() ; 
			precio = Integer.parseInt(obj.get("precio").toString()) ;
			cupos  = Integer.parseInt(obj.get("cupos").toString()) ;
			if ( !hm.containsKey(nombre) )
				Parcados.darInstancia(MyApplication.getAppContext()).actualizarParqueadero(nombre, precio, cupos) ;
			hm.put(nombre, true) ; 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void subscribe ( ) { 
		hm = new HashMap<String, Boolean>()	 ;
		
		System.out.println( "subscribing...");
		pubnub.unsubscribeAll() ; 
		 // Subscribe to a channel
		 try {
		   pubnub.subscribe("parcados", new Callback() {
		 
		       @Override
		       public void connectCallback(String channel, Object message) {
		           System.out.println("SUBSCRIBE : CONNECT on channel:" + channel
		                      + " : " + message.getClass() + " : "
		                      + message.toString());
		       }
		 
		       @Override
		       public void disconnectCallback(String channel, Object message) {
		           System.out.println("SUBSCRIBE : DISCONNECT on channel:" + channel
		                      + " : " + message.getClass() + " : "
		                      + message.toString());
		       }
		 
		       public void reconnectCallback(String channel, Object message) {
		           System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
		                      + " : " + message.getClass() + " : "
		                      + message.toString());
		       }
		 
		       @Override
		       public void successCallback(String channel, Object message) {
					String msg = message.toString() ;
					String arr[] = msg.split(";") ; 
					String nombre = null; 
					int precio = 0 , cupos = 0 ; 
					nombre = arr[0] ; 
					precio = Integer.parseInt(arr[1]) ; 
					cupos  = Integer.parseInt(arr[2]) ;  
					Parcados.darInstancia(MyApplication.getAppContext()).actualizarParqueadero(nombre, precio, cupos) ;
				
		       }
		 
		       @Override
		       public void errorCallback(String channel, PubnubError error) {
		           System.out.println("SUBSCRIBE : ERROR on channel " + channel
		                      + " : " + error.toString());
		       }
		     }
		   );
		 } catch (PubnubException e) {
		   System.out.println(e.toString());
		 }
		 
		  
		 // retrieve last 100 messages
  		 Callback callback2 = new Callback() {
  		   public void successCallback(String channel, Object response) {
  				String res = response.toString() ;
  				res = res.substring(2) ; 
  				res = res.split("],")[0] ;
  				System.out.println( res );
  				
  				String arr[] =  res.split(",") ; 
  				String tmp = null; 
  				for (int i = arr.length-1 ; i >= 1 ; i-- ) {
  					tmp = arr[i] ;
  					tmp = tmp.substring(1 , tmp.length()-1) ;
  					System.out.println( tmp );
  					String arr2 [] = tmp.split ( ";" )  ; 
  					
  					if ( !hm.containsKey(arr2[0]) ) { 
  						Parcados.darInstancia(MyApplication.getAppContext()).actualizarParqueadero
  						(arr2[0], Integer.parseInt (arr2[1]), Integer.parseInt(arr2[2])) ;
  						System.out.println( " actualizada " + arr2[0]+ " " + arr2[1]+ " " + arr2[2]+ " " );
  					}
  					hm.put(arr2[0], true) ; 
//  					actualizarParqueadero(tmp) ; 
  				}
  				tmp = arr[0]  ; 
  				tmp = tmp.substring(1 , tmp.length()-1 ) ;
  				String arr2 [] = tmp.split ( ";" )  ; 
  				if ( !hm.containsKey(arr2[0]) ) { 
						Parcados.darInstancia(MyApplication.getAppContext()).actualizarParqueadero
						(arr2[0], Integer.parseInt (arr2[1]), Integer.parseInt(arr2[2])) ;
						System.out.println( " actualizada " + arr2[0]+ " " + arr2[1]+ " " + arr2[2]+ " " );
					}
					hm.put(arr2[0], true) ; 
  		   }
  		   public void errorCallback(String channel, PubnubError error) {
  		   System.out.println(error.toString());
  		   }
  		 };
  		 pubnub.history("parcados", false , 5 , callback2);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		running = false ;
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}

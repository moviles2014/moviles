package parcados.services;

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
	
	public void onCreate() {
		// code to execute when the service is first created
		running = true ;
		System.out.println( "created service");
		
		 Pubnub pubnub = new Pubnub("pub-c-08e15781-225a-4935-a61f-bcafefa86481", "sub-c-414c745c-5273-11e4-a191-02ee2ddab7fe");

		 // Subscribe to a channel
		 try {
		   pubnub.subscribe("parcados_channel", new Callback() {
		 
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
		          actualizarParqueadero(message.toString()) ;
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
			   	JSONObject obj;
			   	String nombre = null; 
				int precio = 0 , cupos = 0 ; 
		   
				System.out.println( " response " + response.toString());
				System.out.println();
				String res = response.toString() ;
				res = res.substring(2) ; 
				res = res.split("],")[0] ;
				System.out.println( " antes " + res );
				System.out.println( " dfaslfas ;df");
				String arr[] =  res.split("\\}") ; 
				
				String tmp = arr[0] + "}";
				actualizarParqueadero(tmp) ; 
				
				for (int i = 1; i < arr.length; i++) {
					tmp = arr[i].substring(1) + "}";
					actualizarParqueadero(tmp) ; 
				}
				
		   }
		   public void errorCallback(String channel, PubnubError error) {
		   System.out.println(error.toString());
		   }
		 };
		 pubnub.history("parcados_channel", 100 , callback2);
	}
	
	
	void actualizarParqueadero ( String json) {
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			String nombre = null; 
			int precio = 0 , cupos = 0 ; 
			nombre = obj.get("nombre").toString() ; 
			precio = Integer.parseInt(obj.get("precio").toString()) ;
			cupos  = Integer.parseInt(obj.get("cupos").toString()) ;
			Parcados.darInstancia(MyApplication.getAppContext()).actualizarParqueadero(nombre, precio, cupos) ;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

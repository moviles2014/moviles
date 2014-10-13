package db_remote;
import java.util.LinkedHashMap;
import java.util.Map;

import android.util.Log;

import com.pubnub.api.*;

import org.json.JSONException;
import org.json.JSONObject;

public class DB_Queries {
	
	private static String PUBLISH_KEY = "";
	private static  String SUBSCRIBE_KEY = "sub-c-414c745c-5273-11e4-a191-02ee2ddab7fe";
	private static   String CIPHER_KEY = "";
	private static   String SECRET_KEY = "";
	private static String ORIGIN = "pubsub";
	private static   String AUTH_KEY;
	private static   String UUID;
	private static   Boolean SSL = false;
	
	
	public static String whereDidTheyAct () {
		 String ret = ""  ; 
		 
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
		           System.out.println("SUBSCRIBE : " + channel + " : "
		                      + message.getClass() + " : " + message.toString());
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



		 Callback callback = new Callback() {
		   public void successCallback(String channel, Object response) {
		     Log.d("PUBNUB",response.toString());
		   }
		   public void errorCallback(String channel, PubnubError error) {
		   Log.d("PUBNUB",error.toString());
		   }
		 };
		 pubnub.publish("otro", "Free Gift, sign up now" , callback);
		 
		 


		 // retrieve last 100 messages
		 Callback callback2 = new Callback() {
		   public void successCallback(String channel, Object response) {
		     System.out.println(response.toString());
		   }
		   public void errorCallback(String channel, PubnubError error) {
		   System.out.println(error.toString());
		   }
		 };
		 pubnub.history("parcados_channel", 100, callback2);


//		 Retrieve a "slice" of the time line by providing both a start and end time token.
//		 pubnub.history(arg0, arg1, arg2, arg3)
		
		 String result = HttpHandler.cypher_query
		 ( "match (a:Person) -[r:ACTED_IN]-> (movie:Movie) return a.name , " +
		 		"collect( movie.title) order by a.name limit 20" );
		 
//		System.out.println( result );
		 
		try {
			
			JSONObject obj = new JSONObject(result);
			ret = obj.getJSONArray("data").getJSONArray(0)
					.getJSONArray(1).get(0).toString() ; 
			
		}
		catch (JSONException e) {e.printStackTrace(); }
		 
		 return ret ; 
	}
	
	public static String topGunActorsAndRoles () {
		 String result = HttpHandler.cypher_query
	        		
		 ( "match (m:Movie {title:'Top Gun'} )<-[r]-(a:Person) return a.name , r.roles " );
			        
		 return result ; 
	}
	
	
}

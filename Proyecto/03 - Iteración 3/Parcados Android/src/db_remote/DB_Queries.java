package db_remote;
import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;
import com.pubnub.api.*;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import parcados.activities.MyApplication;
import parcados.mundo.Parcados;

public class DB_Queries {
	
//	private static String PUBLISH_KEY = "";
//	private static  String SUBSCRIBE_KEY = "sub-c-414c745c-5273-11e4-a191-02ee2ddab7fe";
//	private static   String CIPHER_KEY = "";
//	private static   String SECRET_KEY = "";
//	private static String ORIGIN = "pubsub";
//	private static   String AUTH_KEY;
//	private static   String UUID;
//	private static   Boolean SSL = false;
	public static boolean  inRequest = false ; 
	
	
	public static String whereDidTheyAct () throws ClientProtocolException, JSONException, IOException {
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
	
	public static String  crearParqueadero ( String _nombre , String _zona, String _horario , String _caracteristicas , String _direccion , int _precio , int _cupos, double _latitud, double _longitud ) throws ClientProtocolException, JSONException, IOException { 
		String res  = HttpHandler.cypher_query
				
		("create (a:Parqueadero { nombre:'"+_nombre+"' , zona:'"+_zona+"', horario:'"+_horario+"' , caracteristicas:'"+_caracteristicas+"', direccion:'"+_direccion+"' , precio:-1 , cupos:-1 , latitud:"+_latitud+" , longitud:"+_longitud+"} ) return a") ; 
		
		return res  ; 
	}
	
	public static String  crearEmpresa ( String _nombre ) throws ClientProtocolException, JSONException, IOException {  
		String res  = HttpHandler.cypher_query
				
		("create (a:Empresa { nombre:'"+_nombre+"' } ) return a") ; 
		
		return res  ; 
	}
	
	public static String  crearRelacion ( String _nombreEmpresa , String _nombreParqueadero ) throws ClientProtocolException, JSONException, IOException {  
		String res  = HttpHandler.cypher_query
				
				("match (a:Empresa {nombre:'"+_nombreEmpresa+"' } ) , (b:Parqueadero {nombre:'"+_nombreParqueadero+"' } ) create (a)-[:OWNS]->(b)") ; 
		
		return res  ; 
	}
	

	
	public static String topGunActorsAndRoles () throws ClientProtocolException, JSONException, IOException {
		 String result = HttpHandler.cypher_query
	        		
		 ( "match (m:Movie {title:'Top Gun'} )<-[r]-(a:Person) return a.name , r.roles " );
			        
		 return result ; 
	}
	
	public static String createTestNode  (  ) throws ClientProtocolException, JSONException, IOException { 
		 String result = HttpHandler.cypher_query
	        		
				 ( "create (a:Test {colo:2})  return a " );
					        
				 return result ; 
	}
	
	public static String consultarParqueadero  ( String nombre ) throws JSONException, ClientProtocolException, IOException {
		
		 inRequest = true ; 
		 String result = HttpHandler.cypher_query
	        		
				 ( "match (a:Parqueadero {nombre:'"+nombre+"'} ) return a.precio , a.cupos" );
		 
		 
		 	int precio = 0  , cupos = 0 ; 
			JSONObject obj;
			
				obj = new JSONObject(result);
				precio  = Integer.parseInt(obj.getJSONArray("data").getJSONArray(0)
						.get(0).toString()) ;
				cupos  = Integer.parseInt(obj.getJSONArray("data").getJSONArray(0)
						.get(1).toString()) ;
			
				// TODO Auto-generated catch block
			
			System.out.println( nombre + " " + precio + " " + cupos );
			Parcados.darInstancia(MyApplication.getAppContext()).actualizarParqueadero(nombre, precio,cupos) ;
			inRequest = false ; 
		 return result ;
				 

	}
	
	public static void eliminarFavorito  ( String nombre ) throws JSONException, ClientProtocolException, IOException {
		 inRequest = true ; 
		 String result = HttpHandler.cypher_query
	        		
				 ( "match (u:User {nombre:'test'})-[r:FAVORITO {nombre:'"+nombre+"'}]->(b) delete r" );
		 
		 
		 System.out.println( "resultado de eliminar  " + result);
			inRequest = false ; 
	}
	
	public static void eliminarReserva ( String parqueadero , String fecha ) throws JSONException, ClientProtocolException, IOException {
		 inRequest = true ;
		 
		 System.out.println( "a eliminar " + parqueadero + " " + fecha );
		 
		 String result = HttpHandler.cypher_query
	        		
				 ( "match (u:User {nombre:'test'})-[r:RESERVA {fecha:'"+fecha+"'}]->(b:Parqueadero {nombre:'"+parqueadero+"'} ) delete r" );
		 
		 
		 System.out.println( "resultado de eliminar  " + result);
			inRequest = false ; 
	}
	
	public static void agregarAFavoritos  ( String nombreFavorito , String parqueadero ) throws JSONException, ClientProtocolException, IOException {
		 inRequest = true ; 
		 String result = HttpHandler.cypher_query
	        		
				 ( "match (a:User { nombre:'test' } ) , (b:Parqueadero {nombre:'"+parqueadero+ "'})" +
				 		" create unique (a)-[:FAVORITO {nombre:'"+nombreFavorito+"'} ]->(b) return a ,b" );
		 
		 
		 System.out.println( "resultado de agregar favorito  " + result);
			inRequest = false ; 
	}
	
	public static void agregarReserva  ( String fecha , String parqueadero ) throws JSONException, ClientProtocolException, IOException {
		 inRequest = true ; 
		 String result = HttpHandler.cypher_query
	        		
				 ( "match (a:User { nombre:'test' } ) , (b:Parqueadero {nombre:'"+parqueadero+"'}) " +
				 		"create unique (a)-[:RESERVA {fecha:'"+ fecha + "'} ]->(b) return a ,b "  ) ; 
		 
		 
		 System.out.println( "resultado de agregar reserva  " + result);
			inRequest = false ; 
	}
	 
	
	public static ArrayList<RespuestaFavs> consultarFavoritos  () throws JSONException, ClientProtocolException, IOException {
		
		 inRequest = true ; 

		 
		 ArrayList<RespuestaFavs> res = new ArrayList<RespuestaFavs>() ; 
		 String count = HttpHandler.cypher_query
	        		
				  
				 ( "MATCH (a:User { nombre: 'test' })-[:FAVORITO]->(x) RETURN count(x)" );
		 
		 
		 System.out.println( count );
			JSONObject obj;
				obj = new JSONObject(count);
				int n  = Integer.parseInt(obj.getJSONArray("data").getJSONArray(0)
						.get(0).toString()) ;
		
				
		 
		 String result = HttpHandler.cypher_query
	        		
				  
				 ( "match (a:User {nombre:'test'})-[r:FAVORITO]->(b) return r.nombre , b.nombre , b.cupos , b.precio" );
		 
		 System.out.println( result );
		 
		 for (  int i = 0  ;i < n  ; i ++ ) { 
			 String favorito , parqueadero , precio , cupos ; 
		 	obj = new JSONObject(result);
			favorito  = obj.getJSONArray("data").getJSONArray(i).get(0).toString() ;
			parqueadero  = obj.getJSONArray("data").getJSONArray(i).get(1).toString() ;
			cupos  = obj.getJSONArray("data").getJSONArray(i).get(2).toString() ;
			precio  = obj.getJSONArray("data").getJSONArray(i).get(3).toString() ;
			res.add ( new RespuestaFavs(precio, cupos, parqueadero, favorito)) ; 
		 }
//		 	int precio = 0  , cupos = 0 ; 
//			
//			
//				// TODO Auto-generated catch block
			
//			Parcados.darInstancia(MyApplication.getAppContext()).actualizarParqueadero(nombre, precio,cupos) ;
			inRequest = false ; 
		 return res ;
				 

	}
	
	public static ArrayList<RespuestaReservas> consultarReservas  () throws JSONException, ClientProtocolException, IOException {
		
		 inRequest = true ; 

		 
		 ArrayList<RespuestaReservas> res = new ArrayList<RespuestaReservas>() ; 
		 String count = HttpHandler.cypher_query
	        		
				  
				 ( "MATCH (a:User { nombre: 'test' })-[:RESERVA]->(x) RETURN count(x)" );
		 
		 
		 System.out.println( count );
			JSONObject obj;
				obj = new JSONObject(count);
				int n  = Integer.parseInt(obj.getJSONArray("data").getJSONArray(0)
						.get(0).toString()) ;
		
				
		 
		 String result = HttpHandler.cypher_query
	        		
				  
				 ( "match (a:User {nombre:'test'})-[r:RESERVA]->(b) return r.fecha, b.nombre" );
		 
		 System.out.println( result );
		 
		 for (  int i = 0  ;i < n  ; i ++ ) { 
			 String fecha , parqueadero ;  
		 	obj = new JSONObject(result);
		 	fecha  = obj.getJSONArray("data").getJSONArray(i).get(0).toString() ;
			parqueadero  = obj.getJSONArray("data").getJSONArray(i).get(1).toString() ;
			res.add(new RespuestaReservas(parqueadero, fecha)) ; 
		 }
		inRequest = false ;
		
		 return res ;
	}
	
}



package db_remote;

import org.json.JSONException;
import org.json.JSONObject;

public class DB_Queries {
	public static String whereDidTheyAct () {
		 String ret = ""  ; 
		
		 String result = HttpHandler.cypher_query
		 ( "match (a:Person) -[r:ACTED_IN]-> (movie:Movie) return a.name , " +
		 		"collect( movie.title) order by a.name limit 20" );
		 
		System.out.println( result );
		 
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

package db_remote;

import android.os.AsyncTask;
import android.widget.Toast;

public class HttpAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
    	System.out.println( params[0] );
    	
    	if( params [0 ] == "1") { 
    		String res = DB_Queries.whereDidTheyAct() ;
        	System.out.println( res );
    	}
    	
        
        return "algo" ;  
    }
    
}
package db_remote;

import android.os.AsyncTask;
import android.widget.Toast;

public class HttpAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
    	System.out.println( params[0] );
    	
    	if( params [0 ] == "1") {
//    		String res = DB_Queries.consultarParqueadero("IPark Calle 70") ; 
//        	System.out.println( res );
    	}
    	else if ( params[0] =="2") { 
//    		DB_Queries.consultarParqueadero( params[1] ) ;
    	}
    	
        return "algo" ;  
    }
    
}
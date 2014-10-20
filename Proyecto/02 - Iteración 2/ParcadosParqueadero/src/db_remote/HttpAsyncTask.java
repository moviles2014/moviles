package db_remote;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.example.parcadosparqueadero.MainActivity;
import com.example.parcadosparqueadero.MyApplication;

import android.os.AsyncTask;
import android.widget.Toast;

public class HttpAsyncTask extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... params) {
		
		try {
			String res = DB_Queries.actualizarParqueadero(params[0], params[1], params[2]) ;
			if ( res.equals( "{  \"columns\" : [ \"a\" ],  \"data\" : [ ]}")) { 
				
			}
			else { 
				Toast toast = Toast.makeText(MyApplication.getAppContext() , "Se han actualizado los datos", Toast.LENGTH_LONG);
				toast.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast toast = Toast.makeText(MyApplication.getAppContext() , "no se ha podido conectar", Toast.LENGTH_LONG);
			toast.show();
		} 
		
		return "algo";
	}
}
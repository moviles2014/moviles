package com.example.parcadosparqueadero;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;


import db_remote.DB_Queries;
import db_remote.HttpAsyncTask;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public void actualizar ( View v ) {
		TextView tx  = ((TextView) findViewById( R.id.editText1) )  ;
		final String nombre = tx.getText().toString() ;
		tx = ((TextView) findViewById( R.id.editText2) )  ;
		final String precio_s =  tx.getText().toString() ;  
		
		tx = ((TextView) findViewById( R.id.editText3) )  ;
		final String cupos_s =  tx.getText().toString() ;
		
		if ( ( nombre == null || nombre.isEmpty() ) ||  ( precio_s == null || precio_s.isEmpty() ) || 
				( cupos_s == null || cupos_s.isEmpty() ) ) {
			Toast toast = Toast.makeText(MyApplication.getAppContext() , "Debe ingresar datos en todos los campos", Toast.LENGTH_LONG);
			toast.show();
		}
		else { 
			System.out.println( nombre + " " + precio_s + "  " + cupos_s );
			
			final AlertDialog dialog4 = new AlertDialog.Builder(this).setTitle("Error").setMessage("El parqueadero no existe").setIcon(android.R.drawable.ic_dialog_alert).show();
			final AlertDialog dialog3 = new AlertDialog.Builder(this).setTitle(nombre).setMessage("Los datos fueron actualizados").setIcon(android.R.drawable.checkbox_on_background).show();
			final AlertDialog dialog2 = new AlertDialog.Builder(this).setTitle("Parcados no se pudo conectar").setMessage("Asegúrese de tener una conexión a internet").setIcon(android.R.drawable.ic_dialog_alert).show();
			final ProgressDialog dialog = ProgressDialog.show(this, "Actualizando Parqueadero", "Por favor espere...", true);
			
			try {

				new Thread(new Runnable() {
					@Override
					public void run() {
						try
						{
							String res = DB_Queries.actualizarParqueadero(nombre, precio_s, cupos_s) ;
							int i = 0;
							while ( i < 10 && DB_Queries.inRequest)
							{
								i++;
								Thread.sleep(1000);
								System.out.println( i );
							}
							
							if ( res.equals("{  \"columns\" : [ \"a\" ],  \"data\" : [ ]}")) {
								dialog.dismiss();	
								dialog2.dismiss() ;
								dialog3.dismiss()  ; 
								Thread.sleep(3000) ; 
								dialog4.dismiss() ; 
							}
							else { 
								dialog.dismiss();	
								dialog2.dismiss() ;
								dialog4.dismiss()  ; 
								Thread.sleep(3000) ; 
								dialog3.dismiss() ; 
							}
							
							
							
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									if (DB_Queries.inRequest)
									{
										new AlertDialog.Builder(MainActivity.this).setTitle("Parcados Time Out")
										.setMessage("No se pudo consultar el parqueadero") 
										.setIcon(android.R.drawable.ic_dialog_alert)
										.show();
									}
									else
									{
//										setCuposYPrecio();
										System.out.println( "success");
									}										
								}
							});
						} catch (Exception e) {
							dialog.dismiss();
							dialog3.dismiss() ; 
							dialog4.dismiss() ; 
							
							System.out.println( "parcados no se pudo conectar ");
							try {
								Thread.sleep(3000) ;
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} 
							dialog2.dismiss() ; 
						}		

					}
				}).start();

			} catch (Exception e) {
				new AlertDialog.Builder(this)
				.setTitle("Parcados")
				.setMessage("Parcados no se pudo conectar") 
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();
			}
			
			
		}
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}

package parcados.activities;

import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;
import com.parcados.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetalleParqueaderoActivity extends Activity {

	
	public final static String NUMEROSMS = "3017786524";
	private Parqueadero actual;	
	private String idparq;
	private String idzona;



	/**
	 * Cuando se crea la aplicación
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_parqueadero) ;
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		Intent intent = getIntent(); 		
		idparq = intent.getStringExtra("idparq");
		idzona = intent.getStringExtra("idzona");

		actual = Parcados.darInstancia(getApplicationContext()).darParqueaderosDeZona(Integer.parseInt(idzona)).get(Integer.parseInt(idparq));

		TextView tx1 = (TextView) findViewById(R.id.textView1) ;
		tx1.setText(actual.darNombre()) ;		
		setCuposYPrecio();
		TextView tx4 = (TextView) findViewById(R.id.textView4) ;
		tx4.setText(actual.darHorario()) ; 
		TextView tx5 = (TextView) findViewById(R.id.textView5) ; 
		tx5.setText(actual.darCaracteristicas()) ; 
		TextView tx6 = (TextView) findViewById(R.id.textView6) ;
		tx6.setText(actual.darDireccion()) ; 

	}

	/**
	 * Método que agrega items al action bar si se encuentran
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parqueadero_detalle, menu);
		return true;
	}

	public void consultarCupos( View v ) { 
		consultarCupos() ; 
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setCuposYPrecio();
	}

	
	public void setCuposYPrecio()
	{
		if(actual.darCupos() == -1)
		{
			TextView tx2 = (TextView) findViewById(R.id.textView2) ;
			tx2.setText("No hay información disponible") ;

		}
		else
		{
			TextView tx2 = (TextView) findViewById(R.id.textView2) ;
			tx2.setText(Integer.toString(actual.darCupos())) ;
		}

		if(actual.darPrecio() == -1)
		{
			TextView tx3 = (TextView) findViewById(R.id.textView3) ;
			tx3.setText("No hay información disponible") ; 
		}
		else
		{
			TextView tx3 = (TextView) findViewById(R.id.textView3) ;
			tx3.setText(Integer.toString(actual.darPrecio())) ; 
		}
	}
	public void consultarCupos() { 
		final SmsManager manejador = SmsManager.getDefault();
		try {
			//TODO

			//			new AlertDialog.Builder(this)
			//		    .setTitle("Parcados")
			//		    .setMessage("Se envió el mensaje correctamente") 
			//		    .setIcon(android.R.drawable.ic_dialog_alert)
			//		     .show();

			final ProgressDialog dialog = ProgressDialog.show(this, "Enviando SMS", "Por favor espere...", true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try
					{
						manejador.sendTextMessage(NUMEROSMS, null, actual.darNombre()+","+ (int)(Math.random()*40+60)+","+ (int)(Math.random()*100)+"" , null, null);
						SmsReceiver.recibiendo = true;
						int i = 0;
						while ( i < 10 && SmsReceiver.recibiendo)
						{
							i++;
							Thread.sleep(1000);
						}			
						dialog.dismiss();	
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								if (SmsReceiver.recibiendo)
								{
									new AlertDialog.Builder(DetalleParqueaderoActivity.this).setTitle("Parcados Time Out")
									.setMessage("No se pudo enviar el mensaje") 
									.setIcon(android.R.drawable.ic_dialog_alert)
									.show();
								}
								else
								{
									setCuposYPrecio();
								}										
							}
						});
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		

				}
			}).start();

		} catch (Exception e) {
			new AlertDialog.Builder(this)
			.setTitle("Parcados")
			.setMessage("No se pudo enviar el mensaje") 
			.setIcon(android.R.drawable.ic_dialog_alert)
			.show();
		}
	}

	/**
	 * Maneja el evento si de si selencciona un item en el action bar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		if ( item.getItemId() == android.R.id.home ){
			finish() ; 
		}

		int id = item.getItemId();
		if (id == R.id.consultar_cupos) {
			consultarCupos() ; 
		}
		else if (id == R.id.reiniciar_precio) {
			Parcados.darInstancia(getApplicationContext()).actualizarParqueadero(actual.darNombre(), -1 , -1 ) ;
			setCuposYPrecio();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * selecciona el parqueadero actual para considerarlo en la calculadora
	 */
	public void seleccionarParqueadero ( View v ) {
		Intent intent = new Intent(this, CalculadoraActivity.class) ;
		intent.putExtra("precio", actual.darPrecio()) ;
		intent.putExtra("NombreParqueadero", actual.darNombre() ) ;
		startActivity(intent) ; 
	} 

}

package parcados.activities;

import java.util.Timer;
import java.util.TimerTask;

import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;
import parcados.receivers.SmsReceiver;
import com.parcados.R;
import db_remote.DB_Queries;
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

	//--------------------------------------------------------------------------------------
	// Constantes
	//--------------------------------------------------------------------------------------
	public final static String NUMEROSMS = "3167443740";

	
	private Timer myTimer;
	//--------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------
	/**
	 * El parqueadero actual
	 */
	private Parqueadero actual;
	/**
	 * El id del parqueadero actual
	 */
	private String idparq;

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------


	/**
	 * Cuando se crea la aplicación
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_parqueadero) ;
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		Intent intent = getIntent(); 		
		idparq = intent.getStringExtra("idparq");

		actual = Parcados.darInstancia(getApplicationContext()).darParqueaderoPorNombre(idparq);

		TextView tx1 = (TextView) findViewById(R.id.textView1) ;
		tx1.setText(actual.darNombre()) ;		
		setCuposYPrecio();
		TextView tx4 = (TextView) findViewById(R.id.textView4) ;
		tx4.setText(actual.darHorario()) ; 
		TextView tx5 = (TextView) findViewById(R.id.textView5) ; 
		tx5.setText(actual.darCaracteristicas()) ; 
		TextView tx6 = (TextView) findViewById(R.id.textView6) ;
		tx6.setText(actual.darDireccion()) ; 
		
		myTimer = new Timer();
		
		myTimer.schedule(new TimerTask() {          
			@Override
			public void run() {
				TimerMethod();
			}

		}, 0, 1000);
	}

	private void TimerMethod()
	{
		this.runOnUiThread(Timer_Tick);

	}


	/**
	 * Runnable con el tick del timer
	 */
	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			setCuposYPrecio()  ; 
		}
	};

	/**
	 * Método que agrega items al action bar si se encuentran
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parqueadero_detalle, menu);
		return true;
	}

	/**
	 * Cuando se resume la aplicación
	 */
	@Override
	protected void onResume() {
		super.onResume();
		setCuposYPrecio();
	}


	/**
	 * Actualiza el cupo y el precio
	 */
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
	
	/**
	 * Consulta los cupos por medio de SMS y actualiza el activity
	 */
	public void consultarCupos() { 
		final SmsManager manejador = SmsManager.getDefault();
		try {

			final ProgressDialog dialog = ProgressDialog.show(this, "Enviando SMS", "Por favor espere...", true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try
					{
						manejador.sendTextMessage(NUMEROSMS, null, "Parcados:"+actual.darNombre()+","+ (int)(Math.random()*40+60)+","+ (int)(Math.random()*100)+"" , null, null);
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
		if (id == R.id.actualizar) {
			actualizar(getWindow().getDecorView().findViewById(R.layout.activity_detalle_parqueadero)) ; 
		}
		else if (id == R.id.reiniciar_precio) {
			Parcados.darInstancia(getApplicationContext()).actualizarParqueadero(actual.darNombre(), -1 , -1 ) ;
			setCuposYPrecio();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void actualizar ( View v ) {
		final AlertDialog dialog2 = new AlertDialog.Builder(this).setTitle("Parcados no se pudo conectar").setMessage("Asegúrese de tener una conexión a internet").setIcon(android.R.drawable.ic_dialog_alert).show();
		final ProgressDialog dialog = ProgressDialog.show(this, "Consultando parqueadero", "Por favor espere...", true);
		
		try {

			new Thread(new Runnable() {
				@Override
				public void run() {
					try
					{
						System.out.println( "llego ");
						DB_Queries.consultarParqueadero( actual.darNombre() ) ;
						int i = 0;
						while ( i < 10 && DB_Queries.inRequest)
						{
							i++;
							Thread.sleep(1000);
							System.out.println( i );
						}
						
						dialog.dismiss();	
						dialog2.dismiss() ;
						
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if (DB_Queries.inRequest)
								{
									new AlertDialog.Builder(DetalleParqueaderoActivity.this).setTitle("Parcados Time Out")
									.setMessage("No se pudo consultar el parqueadero") 
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
						dialog.dismiss();
						
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
	/**
	 * Selecciona al parqueadero y llama a la calculadora
	 * @param v el view
	 */
	public void seleccionarParqueadero ( View v ) {
		Intent intent = new Intent(this, CalculadoraActivity.class) ;
		intent.putExtra("precio", actual.darPrecio()) ;
		intent.putExtra("NombreParqueadero", actual.darNombre() ) ;
		startActivity(intent) ; 
	} 

}

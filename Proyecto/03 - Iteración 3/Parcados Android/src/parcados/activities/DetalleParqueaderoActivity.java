package parcados.activities;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import parcados.mundo.Parcados;
import parcados.mundo.Parqueadero;
import parcados.receivers.SmsReceiver;
import parcados.services.UpdaterServiceManager;

import com.parcados.R;
import db_remote.DB_Queries;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

@SuppressLint("NewApi")
public class DetalleParqueaderoActivity extends DrawerActivity {

	//--------------------------------------------------------------------------------------
	// Constantes
	//--------------------------------------------------------------------------------------
	public final static String NUMEROSMS = "3167443740";

	private static String nombreFavorito ;
	private static String fecha_reserva ;
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
	
	private ScrollView scroll ; 

	
	private TimePicker tpicker ; 
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

		getActionBar().setTitle(actual.darNombre());
		
		tpicker= (TimePicker) findViewById(R.id.timePicker1) ; 
		scroll = (ScrollView) findViewById(R.id.scroll) ;
		
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
		if (id == R.id.agregarAFavoritos) {
//			actualizar(getWindow().getDecorView().findViewById(R.layout.activity_detalle_parqueadero)) ;
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Ingresar referencia:");

			// Set up the input
			final EditText input = new EditText(this);
			// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
			input.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
			builder.setView(input);

			// Set up the buttons
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				@Override
				public void onClick(DialogInterface dialog, int which) {
					nombreFavorito = input.getText().toString();
					agregarAFavoritos() ;  
				}
			});
			builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});

			builder.show();
			
			
		}
		else if (id == R.id.reiniciar_precio) {
			Parcados.darInstancia(getApplicationContext()).actualizarParqueadero(actual.darNombre(), -1 , -1 ) ;
			setCuposYPrecio();
			return true;
		}
		else if (id == R.id.actulizar) {
			actualizar() ; 
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void agregarReserva ()	 { 
		final AlertDialog dialog2 = new AlertDialog.Builder(this).setTitle("Parcados no se pudo conectar").setMessage("Asegúrese de tener una conexión a internet").setIcon(android.R.drawable.ic_dialog_alert).show();
		final ProgressDialog dialog = ProgressDialog.show(this, "Creando Reserva", "Por favor espere...", true);
		
		try {

			new Thread(new Runnable() {
				@Override
				public void run() {
					try
					{
						DB_Queries.agregarReserva( fecha_reserva , actual.darNombre()  ) ;
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
	
	
	public void agregarAFavoritos ( ) {  
		final AlertDialog dialog2 = new AlertDialog.Builder(this).setTitle("Parcados no se pudo conectar").setMessage("Asegúrese de tener una conexión a internet").setIcon(android.R.drawable.ic_dialog_alert).show();
		final ProgressDialog dialog = ProgressDialog.show(this, "Agregando a favoritos", "Por favor espere...", true);
		
		try {

			new Thread(new Runnable() {
				@Override
				public void run() {
					try
					{
						DB_Queries.agregarAFavoritos( nombreFavorito , actual.darNombre()  ) ;
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
	
	
	public void actualizar ( ) {
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
	

	@SuppressWarnings("deprecation")
	public void reservar ( View v ) {
	    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llayout);
	    if(linearLayout.getMeasuredHeight() <= scroll.getScrollY() +
	           scroll.getHeight()) {
	    	int hour = tpicker.getCurrentHour() ;
	    	int min = tpicker.getCurrentMinute() ;
	    	
	    	Date ref  = new Date ( System.currentTimeMillis() ) ;
	    	Calendar cal = Calendar.getInstance(); // locale-specific
	    	cal.setTime(ref);
	    	cal.set(Calendar.HOUR_OF_DAY, hour);
	    	cal.set(Calendar.MINUTE, min);
	    	long time = cal.getTimeInMillis();
	    	Date d2 = new Date ( time ) ;
	    	cal.setTime (d2) ; 
	    	
	    	fecha_reserva = Long.toString(time ) ; 
	    	agregarReserva() ;  
	    	
	    	
	    	String fechaF = ""+cal.get (cal.YEAR) + "-" + (cal.get( cal.MONTH )+1)  +  "-" + 
	    			 cal.get( cal.DAY_OF_MONTH) + "  " +  cal.get( cal.HOUR_OF_DAY )  + ":" + cal.get(cal.MINUTE) ;   
	    	
//	    	System.out.println( fechaF ) ;
	    	
	    	
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Se ha creado una nueva reserva")
			       .setCancelable(false)
			       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
			
	    }
	    else {
	    	scroll.fullScroll(View.FOCUS_DOWN) ; 
	    }
		
	} 

}

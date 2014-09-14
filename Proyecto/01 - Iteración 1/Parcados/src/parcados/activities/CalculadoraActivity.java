package parcados.activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import parcados.mundo.Parcados;
import parcados.sqlite.DAO;

import com.example.parcados.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.PendingIntent.OnFinished;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;


public class CalculadoraActivity extends Activity {
	
	/*
	 * precio actual luego de iniciar la calculadora  
	 */
	private int precioActual ;
	
	/*
	 * texto que muestra el precioActual 
	 */
	private TextView precioActual_txt ; 
	
	/*
	 * permite actualizar la vista despues de cada 30 segundos
	 */
	Handler handler ; 
	/*
	 * boton para cambiar el estado de la calculadora
	 */
	private Button btn  ; 
	/*
	 * Maneja la creación de la activity 
	 */
	
	private Timer myTimer;
	
	private CalculadoraActivity yo ; 
	private int precio ; 
	
	private String m_Text = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		yo = this ; 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculadora);
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		
		Intent intent = getIntent() ;
		precio = intent.getIntExtra("precio" , -1 ) ;
		System.out.println( " el precio es " + precio );
		
		precioActual_txt = (TextView) findViewById(R.id.textView2) ;
		precioActual_txt.setText("$0.0" ) ;
		precioActual = 0 ; 
		btn = (Button) findViewById(R.id.button2) ; 
		
		if ( !UpdaterServiceManager.isRunning() )
			btn.setText( R.string.inciarTiempo ) ;
		else 
			btn.setText(R.string.finalizarTiempo) ; 
		
	    myTimer = new Timer();
	    
	    
    	myTimer.schedule(new TimerTask() {          
	        @Override
	        public void run() {
	            TimerMethod();
	        }

	    }, 0, 1000);
//		handler = new Handler();
//		handler.postDelayed(updateData , 10000) ;
	}
	
	private void TimerMethod()
	{
	    //This method is called directly by the timer
	    //and runs in the same thread as the timer.

	    //We call the method that will work with the UI
	    //through the runOnUiThread method.
	    this.runOnUiThread(Timer_Tick);
	    
	}


	private Runnable Timer_Tick = new Runnable() {
	    public void run() {

	    //This method runs in the same thread as the UI.               

	    //Do something to the UI thread here
	    	if ( UpdaterServiceManager.isRunning() ) { 
//	    		precioActual = ThreadCalculadora.getPrecio() ;
	    		precioActual = UpdaterServiceManager.getPrecioActual () ; 
	    		precioActual_txt.setText("$"+ Double.toString(precioActual)) ;
	    	}
	    	else {
	    		precioActual_txt.setText("$"+ "0.0") ;
	    	}
//	         handler.postDelayed(updateData,1000);
	    }
	};
	
//	/**
//	 * Método que agrega items al action bar si se encuentran
//	 */
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.calculadora, menu);
//		return true;
//	}
	
//	/**
//	 * Maneja el evento si de si selencciona un item en el action bar
//	 */
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		if ( item.getItemId() == android.R.id.home ){
//			finish() ; 
//		}
//		int id = item.getItemId();
//		if (id == R.id.ingresar_precio) {
//			System.out.println( " ingresar precio");
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
	
	/*
	 * empieza a calcular el precio del parqueadero seleccionado según
	 * el tiempo que transcurre
	 */
	public void cambiarEstadoCalculadora ( View v ) { 
		
//		Parcados.darInstancia(getApplicationContext()).toggleEstadoCalculadora() ; 
		
		if ( !UpdaterServiceManager.isRunning() ) {
			btn.setText(R.string.finalizarTiempo) ;
			iniciarCalculadora() ; 
		}
		else { 
			precioActual_txt.setText("$"+ "0.0") ;
			btn.setText( R.string.inciarTiempo ) ; 
			finalizarCalculadora() ; 
		}
	}
		
	
	/*
	 * inicia registro del precio actual considerando el tiempo trasncurrido en parqueadero
	 */
	public void iniciarCalculadora (){
		  
//	    ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
//	    boolean runningService = false ;
//        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (UpdaterServiceManager.class.getName().equals(service.service.getClassName())) {
//                runningService = true ; 
//            }
//        }
		
		if ( precio == -1 ){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Title");
	
			// Set up the input
			final EditText input = new EditText(this);
			// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
			input.setInputType(InputType.TYPE_CLASS_TEXT );
			builder.setView(input);
	
			// Set up the buttons
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        m_Text = input.getText().toString();
			        UpdaterServiceManager.setPrecio(Integer.parseInt(m_Text)) ; 
			        startService(new Intent ( yo , UpdaterServiceManager.class));  
			    }
			});
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        dialog.cancel();
			    }
			});
	
			builder.show();
		}
		else {
			UpdaterServiceManager.setPrecio(precio) ; 
			startService(new Intent ( this , UpdaterServiceManager.class));
		}
	}
	
	
	/*
	 * restablece el estado de la calculadora para que el usuario pueda iniciar 
	 * calculos en otros parqueaderos
	 */
	public void finalizarCalculadora(){
		
		UpdaterServiceManager.stopService() ; 
//		stopService(new Intent(this ,UpdaterServiceManager.class));
	}

	/*
	 * abre ventana modal para que el usuario ingrese la hora de inicio
	 */
	public void ingresarPrecio (View v){
		
		System.out.println( " hello colo ");
		AlertDialog.Builder builder = new AlertDialog.Builder(yo);
		builder.setTitle("Title");

		// Set up the input
		final EditText input = new EditText(yo);
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        m_Text = input.getText().toString();
		    }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});

		builder.show();
	
	}
}

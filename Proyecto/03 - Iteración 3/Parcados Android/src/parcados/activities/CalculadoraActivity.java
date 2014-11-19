package parcados.activities;

import java.util.Timer;
import java.util.TimerTask;
import parcados.mundo.Parcados;
import parcados.services.UpdaterServiceManager;

import com.parcados.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CalculadoraActivity extends DrawerActivity {

	//--------------------------------------------------------------------------------------
	// Atributos
	//--------------------------------------------------------------------------------------

	/**
	 * Precio despu�s de iniciar la calculadora
	 */
	private int precioActual ;

	/**
	 * Texto que muestra el precio
	 */
	private TextView precioActual_txt ; 

	/**
	 * Actualiza la vista
	 */
	Handler handler ; 
	/**
	 * Bot�n para cambiar el �stado de la calculadora
	 */
	private Button btn  ; 

	/**
	 * Maneja la creaci�n del activity
	 */
	private Timer myTimer;

	/**
	 * El precio
	 */
	private int precio ; 

	/**
	 * Texto mostrado
	 */
	private String m_Text = "";

	/**
	 * Nombre del parqueadero
	 */
	private String nombreParqueadero ;

	//--------------------------------------------------------------------------------------
	// M�todos
	//--------------------------------------------------------------------------------------

	/**
	 * Creaci�n de la activity
	 * @param savedInstanceState el estado de la instancia
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculadora);
		getActionBar().setDisplayHomeAsUpEnabled(true) ;
		getActionBar().setTitle("Calculadora");

		Intent intent = getIntent() ;
		precio = intent.getIntExtra("precio" , -1 ) ;
		nombreParqueadero = intent.getStringExtra("NombreParqueadero" ) ;


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
		
		final Typeface mFont = Typeface.createFromAsset(getAssets(),
				"fonts/Oxygen-Regular.ttf"); 
				final ViewGroup mContainer = (ViewGroup) findViewById(
				android.R.id.content).getRootView();
				MyApplication.setAppFont(mContainer, mFont ,true );
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


	/**
	 * Empieza a calcular el precio del parqueadero seleccionado seg�n el tiempo que transcurre
	 * @param v la view
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


	/**
	 * El registro del precio actual considerando el tiempo transcurrido en el parqueadero
	 */

	public void iniciarCalculadora (){

		if ( precio == -1 ){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Precio por minuto del parqueadero:");

			// Set up the input
			final EditText input = new EditText(this);
			// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
			input.setInputType(InputType.TYPE_CLASS_NUMBER );
			builder.setView(input);

			// Set up the buttons
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				@Override
				public void onClick(DialogInterface dialog, int which) {
					m_Text = input.getText().toString();
					try {
						precio = Integer.parseInt(m_Text) ; 
						if (nombreParqueadero != null )Parcados.darInstancia(getApplicationContext()).actualizarParqueadero(nombreParqueadero, precio , Parcados.darInstancia(getApplicationContext()).darParqueaderoPorNombre(nombreParqueadero).darCupos()) ;
						UpdaterServiceManager.setPrecio(precio) ; 
						startService(new Intent ( CalculadoraActivity.this , UpdaterServiceManager.class)); 					
					}
					catch (NumberFormatException e)
					{
						precioActual_txt.setText("$"+ "0.0") ;
						btn.setText( R.string.inciarTiempo ) ; 
						dialog.cancel();
						new AlertDialog.Builder(CalculadoraActivity.this).setTitle("Parcados")
						.setMessage("Ingrese un n�mero v�lido") 
						.setIcon(android.R.drawable.ic_dialog_alert)
						.show();
					}				

				}
			});
			builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					precioActual_txt.setText("$"+ "0.0") ;
					btn.setText( R.string.inciarTiempo ) ; 
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


	/**
	 * Restablece el estado de la calculadora para que el usuario pueda iniciar calculos en otros parqueaderos
	 */
	public void finalizarCalculadora(){

		UpdaterServiceManager.stopService() ; 
		//		stopService(new Intent(this ,UpdaterServiceManager.class));
	}

	/**
	 * Abre ventana modal para que el usuario ingrese la hora de inicio
	 */
	public void ingresarPrecio (View v){
		AlertDialog.Builder builder = new AlertDialog.Builder(CalculadoraActivity.this);
		builder.setTitle("Title");

		// Set up the input
		final EditText input = new EditText(CalculadoraActivity.this);
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
		return super.onOptionsItemSelected(item);
	}
}

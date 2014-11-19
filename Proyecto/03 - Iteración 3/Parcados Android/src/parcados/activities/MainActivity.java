package parcados.activities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import parcados.services.BackgroundService;
import com.parcados.R;

import db_remote.DB_Queries;
import db_remote.RespuestaFavs;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends DrawerActivity {

	TextToSpeech tts ; 
	static final int check = 111 ;

	private static Object cola  ;  

	private static boolean flag  ; 
	private static String parqueaderoInput ;
	private static String parqueaderoInput2 ; 
	private static String nombreParqueadero ; 
	private static String fecha_reserva ; 
	private static boolean reservando ;

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------
	/**
	 * Cuando se crea la aplicación
	 */
	@Override 
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		reservando = false ; 
		flag =false ; 
		setContentView(R.layout.activity_main_con);	
		getActionBar().setTitle("Parcados");


		Typeface tf = Typeface.createFromAsset(MyApplication.getAppContext().getAssets(), "fonts/Oxygen-Regular.ttf");
		Button btn = (Button) findViewById(R.id.button1) ; 
		btn.setTypeface(tf)  ; 


		tts = new TextToSpeech( this , new TextToSpeech.OnInitListener( ) { 

			@Override
			public void onInit(int status) {
				// TODO Auto-generated method stub
				if  ( status != TextToSpeech.ERROR ) { 
					Locale locSpanish = new Locale("es");
					tts.setLanguage(locSpanish) ; 

				}
			}
		}) ; 

		if ( !BackgroundService.running )
			MyApplication.getAppContext().startService(new Intent(MyApplication.getAppContext(), BackgroundService.class));


		final Typeface mFont = Typeface.createFromAsset(getAssets(),
				"fonts/Oxygen-Regular.ttf"); 
		final ViewGroup mContainer = (ViewGroup) findViewById(
				android.R.id.content).getRootView();
		MyApplication.setAppFont(mContainer, mFont ,true );


	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if ( BackgroundService.inSpeechRecognition ) { 
			Intent i = new Intent ( RecognizerIntent.ACTION_RECOGNIZE_SPEECH ) ;
			i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
			i.putExtra(RecognizerIntent.EXTRA_PROMPT, "¿Qué quieres hacer?" )  ;
			startActivityForResult(i, check ) ;
		}

	}




	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if ( requestCode == check && resultCode == RESULT_OK) {
			ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) ;
			System.out.println( "Resultado " +  results );
			if ( results.get(0).equals("reservar parqueadero")) {
				tts.speak("Parqueadero reservado" , TextToSpeech.QUEUE_FLUSH, null);
			}
			else if ( results.get(0).startsWith("información de parqueadero") ) {
				parqueaderoInput = results.get(0).substring(26).trim() ;
				consultarParqueadero() ; 
			}
			else if (results.get(0).startsWith("reservar parqueadero")) { 
				reservando = true ; 
				try {

					parqueaderoInput2 = results.get(0).substring(20).trim() ;
					String inputs[] = parqueaderoInput2.split(" a las ") ;
					String nombreFav = inputs[0] ; 
					String inputs2[] = inputs[1].split(" ") ;

					int hour  = Integer.parseInt(inputs2[0]) ; 
					int min = Integer.parseInt(inputs2[1]) ; 
					if (inputs2[2].equals("pm") ){ 
						hour +=12 ;  
					}

					Date ref  = new Date ( System.currentTimeMillis() ) ;
					Calendar cal = Calendar.getInstance(); // locale-specific
					cal.setTime(ref);
					cal.set(Calendar.HOUR_OF_DAY, hour);
					cal.set(Calendar.MINUTE, min);
					long time = cal.getTimeInMillis();
					Date d2 = new Date ( time ) ;
					cal.setTime (d2) ; 

					fecha_reserva = Long.toString(time ) ; 


					parqueaderoInput = nombreFav ;

					consultarParqueadero() ; 

					agregarReserva() ; 

					tts.speak( "reservado parqueadero"+ parqueaderoInput + " a las " + hour + " " + min , TextToSpeech.QUEUE_FLUSH, null);
					reservando = false ; 
				}
				catch (Exception e) { 
					System.out.println( " error  ");
					System.out.println(results.get(0));
					tts.speak( "no te entiendo"  , TextToSpeech.QUEUE_FLUSH, null);
					reservando = false ;
				}
			}			
			else if ( results.get(0).equals("abrir calculadora"))
			{
				tts.speak("Abriendo calculadora" , TextToSpeech.QUEUE_FLUSH, null);
				super.abrirCalculadora(findViewById(R.layout.activity_main_con));
			}                       
			else if (results.get(0).equals("abrir mapa")){
				tts.speak("Abriendo mapa" , TextToSpeech.QUEUE_FLUSH, null);
				super.abrirMapa(findViewById(R.layout.activity_main_con));
			}	                	
			else  
				tts.speak("no te entiendo" , TextToSpeech.QUEUE_FLUSH, null);
		}
		super.onActivityResult(requestCode, resultCode, data);

		if ( !flag ) { 
			BackgroundService.inSpeechRecognition = false ;       
			BackgroundService.startAccelerometer() ;
		}
	}


	private void consultarParqueadero () { 

		cola = new Object() ; 


		final AlertDialog dialog2 = new AlertDialog.Builder(this).setTitle("Parcados no se pudo conectar").setMessage("Asegúrese de tener una conexión a internet").setIcon(android.R.drawable.ic_dialog_alert).show();
		final ProgressDialog dialog = ProgressDialog.show(this, "Consultando parqueadero", "Por favor espere...", true);

		try {

			new Thread(new Runnable() {
				@Override
				public void run() {
					try
					{
						ArrayList<RespuestaFavs> res =  DB_Queries.consultarFavoritos() ;
						System.out.println( "size de res "  + res.size () );
						for ( int i = 0 ;i < res.size() ; i ++ ) { 
							System.out.println(  res.get(i).favorito + " " + parqueaderoInput);
							if ( res.get(i).favorito.equals(parqueaderoInput)) {
								System.out.println( " llego con " + parqueaderoInput ); 
								if ( !reservando ) { 
									String speech = "El precio por minuto para el parqueadero " + parqueaderoInput 
											+" es de " + res.get(i).precio + " pesos" + 
											" y actualmente cuenta con " + res.get(i).cupos + 
											" cupos" ; 
									tts.speak(speech , TextToSpeech.QUEUE_FLUSH, null);
								}
								else { 
									System.out.println( "llego 43434");
									nombreParqueadero = res.get(i).parqueadero ; 
								}
							}
						}

						synchronized (MainActivity.cola) {
							MainActivity.cola.notifyAll() ;	
						}
						//						 
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
								synchronized (MainActivity.cola) {
									MainActivity.cola.notifyAll() ;	
								}
								if (DB_Queries.inRequest)
								{
									new AlertDialog.Builder(MainActivity.this).setTitle("Enhorabuena!")
									.setMessage("Se ha reservado el parqueadero") 
									.setIcon(android.R.drawable.ic_dialog_alert)
									.show();
								}
								else
								{
									//									setCuposYPrecio();
								}										
							}
						});
					} catch (Exception e) {
						synchronized (MainActivity.cola) {
							MainActivity.cola.notifyAll() ;	
						}
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
			synchronized (MainActivity.cola) {
				MainActivity.cola.notifyAll() ;	
			}
			new AlertDialog.Builder(this)
			.setTitle("Parcados")
			.setMessage("Parcados no se pudo conectar") 
			.setIcon(android.R.drawable.ic_dialog_alert)
			.show();
		}



		synchronized (cola) {
			try {
				cola.wait() ;
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 
		}
	}


	/**
	 * Método que agrega items al action bar si se encuentran
	 */
	//	@Override
	//	public boolean onCreateOptionsMenu(Menu menu) {
	//		getMenuInflater().inflate(R.menu.main, menu);
	//		return true;
	//	}

	public void agregarReserva ()	 { 
		final AlertDialog dialog2 = new AlertDialog.Builder(this).setTitle("Parcados no se pudo conectar").setMessage("Asegúrese de tener una conexión a internet").setIcon(android.R.drawable.ic_dialog_alert).show();
		final ProgressDialog dialog = ProgressDialog.show(this, "Creando Reserva", "Por favor espere...", true);

		try {

			new Thread(new Runnable() {
				@Override
				public void run() {
					try
					{
						DB_Queries.agregarReserva( fecha_reserva , nombreParqueadero  ) ;
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
									new AlertDialog.Builder(MainActivity.this).setTitle("Parcados Time Out")
									.setMessage("No se pudo consultar el parqueadero") 
									.setIcon(android.R.drawable.ic_dialog_alert)
									.show();
								}
								else
								{
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
	 * Maneja el evento si de si selencciona un item en el action bar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.comandoDeVoz) {

			flag = true ; 
			Intent i = new Intent ( RecognizerIntent.ACTION_RECOGNIZE_SPEECH ) ;
			i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
			i.putExtra(RecognizerIntent.EXTRA_PROMPT, "¿Qué quieres hacer?" )  ;
			startActivityForResult(i, check ) ;
		}
		else if ( id == R.id.habilitarAcc) { 
			BackgroundService.startAccelerometer() ;  
		}
		else if ( id == R.id.deshabilitarAcc) {
			BackgroundService.pauseAccelerometer() ; 
		}
		else if ( id == R.id.ajustarAcc) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Ingresar sensibilidad:");

			// Set up the input
			final EditText input = new EditText(this);
			// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
			input.setInputType(InputType.TYPE_CLASS_NUMBER);
			builder.setView(input);

			// Set up the buttons
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int thresh = Integer.parseInt(input.getText().toString() ) ; 
					BackgroundService.setSHAKE_THRESHOLD(thresh) ; 
				}
			});
			builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});

			builder.show();

		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Lanza la activity de zonas
	 * @param v - el view
	 */
	public void buscarParqueadero ( View v ) { 
		super.buscarParqueadero(v);

	}

	/**
	 * Lanza la activity para calcular el precio del parqueadero seleccionado
	 * @param v - el view
	 */
	public void abrirCalculadora ( View v ) {
		super.abrirCalculadora(v);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		super.onDestroy();
		BackgroundService.inSpeechRecognition = false ;       
		BackgroundService.startAccelerometer() ;
		System.out.println( "entro en destroy ");
	}
}

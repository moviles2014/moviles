package parcados.activities;

import java.util.ArrayList;
import java.util.Locale;
import parcados.services.BackgroundService;
import com.parcados.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends DrawerActivity {

	TextToSpeech tts ; 
	static final int check = 111 ;
	
	private static boolean flag  ; 
	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------
	/**
	 * Cuando se crea la aplicación
	 */
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		flag =false ; 
		setContentView(R.layout.activity_main_con);	
		getActionBar().setTitle("Parcados");

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
			if ( results.get(0).equals("reservar parqueadero"))
				tts.speak("Parqueadero reservado" , TextToSpeech.QUEUE_FLUSH, null);
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

	/**
	 * Método que agrega items al action bar si se encuentran
	 */
	//	@Override
	//	public boolean onCreateOptionsMenu(Menu menu) {
	//		getMenuInflater().inflate(R.menu.main, menu);
	//		return true;
	//	}


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

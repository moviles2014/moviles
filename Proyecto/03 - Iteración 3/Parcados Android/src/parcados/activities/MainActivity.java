package parcados.activities;

import java.util.ArrayList;
import java.util.Locale;

import parcados.services.BackgroundService;
import com.parcados.R;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends DrawerActivity {


	TextToSpeech tts ; 
       static final int check = 111 ;
	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------
	/**
	 * Cuando se crea la aplicación
	 */
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_con);	
		getActionBar().setTitle("Parcados");
		
		if ( BackgroundService.inSpeechRecognition ) { 
	    	  Intent i = new Intent ( RecognizerIntent.ACTION_RECOGNIZE_SPEECH ) ;
	          i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
	          i.putExtra(RecognizerIntent.EXTRA_PROMPT, "¿Qué quieres hacer?" )  ;
	          startActivityForResult(i, check ) ;
		}
		
		 tts = new TextToSpeech( this , new TextToSpeech.OnInitListener( ) { 
 			
 			@Override
 			public void onInit(int status) {
 				// TODO Auto-generated method stub
 				if  ( status != TextToSpeech.ERROR ) { 
 					Locale locSpanish = new Locale("spa", "MEX");
 					tts.setLanguage(locSpanish) ; 
 					
 				}
 			}
 		}) ; 
		
		if ( !BackgroundService.running )
			MyApplication.getAppContext().startService(new Intent(MyApplication.getAppContext(), BackgroundService.class));
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	   @Override
	    protected void onActivityResult( int requestCode, int resultCode, Intent data) {
	          // TODO Auto-generated method stub
	          if ( requestCode == check && resultCode == RESULT_OK) {
	                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) ;
	                System.out.println( results );
	                if ( results.get(0).equals("reservar parqueadero"))
	                	tts.speak("Parqueadero reservado" , TextToSpeech.QUEUE_FLUSH, null);
	                else if ( results.get(0).equals("información de aplicación"))
	                	tts.speak("parcados es firme" , TextToSpeech.QUEUE_FLUSH, null);
	                else  
	                	tts.speak("no te entiendo" , TextToSpeech.QUEUE_FLUSH, null);
//	                lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , results)) ;
	          }
	          super.onActivityResult(requestCode, resultCode, data);
	          BackgroundService.inSpeechRecognition = false ;         
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
}

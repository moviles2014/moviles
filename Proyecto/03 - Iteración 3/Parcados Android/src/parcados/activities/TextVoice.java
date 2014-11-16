package parcados.activities;

import java.util.Locale;

import com.parcados.R;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;

public class TextVoice extends Activity {
	
	static final String [] texts = { 
		"parqueadero Reservado " , 
		"probando esta vuelta " , 
		"parcados es firme"
	} ;
	
	TextToSpeech tts ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_voice) ;
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
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (tts !=null ) { 
			tts.stop () ; 
			tts.shutdown() ; 
		}
		super.onPause();
	} 
	
	public void test (View v ) { 
		 System.out.println(" colo ");
//		 tts.speak("probando esta vaina" , TextToSpeech.QUEUE_ADD, null);
		 tts.speak("yo no saludo a ese man por que tiene vainas como de marica" , TextToSpeech.QUEUE_FLUSH, null);
	}
}

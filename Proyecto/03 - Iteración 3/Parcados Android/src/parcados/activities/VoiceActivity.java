package parcados. activities ;

import java . util. ArrayList;
import java.util.Locale;

import parcados.services.BackgroundService;

import android. app .Activity ;
import android.content.Context;
import android . content. DialogInterface;
import android . content. Intent;
import android . content. DialogInterface .OnClickListener ;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android. os .Bundle ;
import android . speech. RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android . view. View;
import android . widget. ArrayAdapter;
import android . widget. Button;
import android . widget. ListView;
import android.widget.Toast;

import com. parcados .R ;

public class VoiceActivity extends Activity {
	 /* put this into your activity class */
	  private SensorManager mSensorManager;
	  private float mAccel; // acceleration apart from gravity
	  private float mAccelCurrent; // current acceleration including gravity
	  private float mAccelLast; // last acceleration including gravity
	  private boolean inSpeechRecognition = false ; 
	  
	  private final SensorEventListener mSensorListener = new SensorEventListener() {

	    public void onSensorChanged(SensorEvent se) {
	      float x = se.values[0];
	      float y = se.values[1];
	      float z = se.values[2];
	      mAccelLast = mAccelCurrent;
	      mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
	      float delta = mAccelCurrent - mAccelLast;
	      mAccel = mAccel * 0.9f + delta; // perform low-cut filter
	      
	      
	      if (mAccel > 12) {
//	    	  if ( !inSpeechRecognition ) { 
//	    		  inSpeechRecognition=true ; 
//		    	  Intent i = new Intent ( RecognizerIntent.ACTION_RECOGNIZE_SPEECH ) ;
//		          i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
//		          i.putExtra(RecognizerIntent.EXTRA_PROMPT, " Habla colo " )  ;
//		          startActivityForResult(i, check ) ;
//	    	  }
	    	}
	      
	    }

	    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    }
	  };

	  @Override
	  protected void onResume() {
	    super.onResume();
	    mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	  }

	  @Override
	  protected void onPause() {
	    mSensorManager.unregisterListener(mSensorListener);
	    super.onPause();
	  }
	
	TextToSpeech tts ; 
       static final int check = 111 ;
       @Override
       protected void onCreate( Bundle savedInstanceState) {
             // TODO Auto-generated method stub
    	   
             super .onCreate ( savedInstanceState);
            setContentView ( R.layout.activity_voice ) ;
            
            inSpeechRecognition=true ; 
	    	  Intent i = new Intent ( RecognizerIntent.ACTION_RECOGNIZE_SPEECH ) ;
	          i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
	          i.putExtra(RecognizerIntent.EXTRA_PROMPT, "¿Qué quieres hacer?" )  ;
	          startActivityForResult(i, check ) ;
            
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            mAccel = 0.00f;
            mAccelCurrent = SensorManager.GRAVITY_EARTH;
            mAccelLast = SensorManager.GRAVITY_EARTH;
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
//            getActionBar (). setDisplayHomeAsUpEnabled( true ) ;
       }
      

    public void test ( View v ) {
          Intent i = new Intent ( RecognizerIntent.ACTION_RECOGNIZE_SPEECH ) ;
          i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
          i.putExtra(RecognizerIntent.EXTRA_PROMPT, " Habla colo " )  ;
          startActivityForResult(i, check ) ;
    }
      
//    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {
          // TODO Auto-generated method stub
          if ( requestCode == check && resultCode == RESULT_OK) {
        	   inSpeechRecognition = false ; 
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) ;
                System.out.println( results );
                if ( results.get(0).equals("reservar parqueadero"))
                	tts.speak("Parqueadero reservado" , TextToSpeech.QUEUE_FLUSH, null);
                else if ( results.get(0).equals("información de aplicación"))
                	tts.speak("parcados es firme" , TextToSpeech.QUEUE_FLUSH, null);
                else  
                	tts.speak("no te entiendo" , TextToSpeech.QUEUE_FLUSH, null);
//                lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , results)) ;
          }
          super.onActivityResult(requestCode, resultCode, data);
          BackgroundService.setInSpeechRecognition(false) ;         
    }

}

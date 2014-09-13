package parcados.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneReceiver extends BroadcastReceiver {
	// se registra en app manifest
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras() ; 
		if ( extras != null ){
			String state = extras.getString(TelephonyManager.EXTRA_STATE)  ;
//			Log.d("PohneReceiver" , state) ; 
			
			System.out.println( state  );
			if  ( state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
				String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER) ;
//				Toast.makeText(this, phoneNumber, 10).show() ;
				System.out.println(phoneNumber);
			}
		}
	}

}

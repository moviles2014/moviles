package parcados.activities;

import parcados.mundo.Parcados;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
	
	//--------------------------------------------------------------------------------------
	// Atributos estáticos
	//--------------------------------------------------------------------------------------
	/**
	 * Indica si se encuentra recibiendo un mensaje
	 */
	public static boolean recibiendo = false;

	/**
	 * Modela el proveedor
	 */
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

	//--------------------------------------------------------------------------------------
	// Métodos
	//--------------------------------------------------------------------------------------
    /**
     * Maneja la recepción del mensaje
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }
                // large message might be broken into many
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }
                String message = sb.toString();
                String msg[] = message.split(",") ; 
                String nombre = msg[0] ; 
                int precio = Integer.parseInt(msg[1] ) ;
                int cupos = Integer.parseInt(msg[2] ) ;
                Parcados.darInstancia(context).actualizarParqueadero(nombre, precio,cupos) ;
                SmsReceiver.recibiendo = false;
            }
        }
    }
}
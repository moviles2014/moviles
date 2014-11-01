package parcados.receivers;

import parcados.services.BackgroundService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver {

    final static String TAG = "BootCompletedReceiver";

    @Override
    public void onReceive(Context context, Intent arg1) {
       System.out.println( "staring service ... ");
        context.startService(new Intent(context, BackgroundService.class));
    }
}
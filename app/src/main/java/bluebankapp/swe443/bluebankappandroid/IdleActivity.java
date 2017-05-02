/**
 * Created by sarab on 5/2/2017.
 */
package bluebankapp.swe443.bluebankappandroid;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

public class IdleActivity extends Activity {
    public static final long DISCONNECT_TIMEOUT = 300000; // 5 min = 5 * 60 * 1000 ms

    private static Handler disconnectHandler = new Handler(){
        public void handleMessage(NotificationCompat.MessagingStyle.Message msg) {
            // TODO: provide idle message
        }
    };

    private static Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {
            // TODO: logout the account
        }
    };

    public void resetDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction(){
        resetDisconnectTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }
}


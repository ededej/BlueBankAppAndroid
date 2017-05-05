/**
 * Created by sarab on 5/2/2017.
 */
package bluebankapp.swe443.bluebankappandroid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

public class IdleActivity extends Activity {
    public static final long DISCONNECT_TIMEOUT = 900000; // 15 min = 15 * 60 * 1000 ms

    private static Handler disconnectHandler = new Handler(){
        public void handleMessage(NotificationCompat.MessagingStyle.Message msg) {
            // TODO: provide idle message
        }
    };

    private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {
            SharedPreferences.Editor editor = getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
            // To log out, reset all locally stored account information.
            // Leave the username so that the field auto-populates on the login screen.
            // Similarly, leave the IP so that the user doesn't have to re-type it.
            editor.putString("password", "");
            editor.putFloat("balance", 0);
            editor.putString("ssn", "");
            editor.putString("dob", "");
            editor.putString("email", "");
            editor.putString("fullname", "");
            editor.apply();

            // back to login.
            finish();
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
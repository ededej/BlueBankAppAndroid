package bluebankapp.swe443.bluebankappandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    EditText Username, Password, IPBox;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/HH/mm/ss");

    private Spass mSpass;
    private SpassFingerprint mSpassFingerprint;
    private boolean isFeatureEnabled;
    private SpassFingerprint.IdentifyListener listener =
            new SpassFingerprint.IdentifyListener() {
                @Override
                public void onFinished(int eventStatus) {
                    if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS) {
                        bioLogin();
                    }
                }
                @Override
                public void onReady() { }
                @Override
                public void onStarted() { }
                @Override
                public void onCompleted() { }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
        rl.bringToFront();

        // Unpack SharedPrefs to check for a pre-populated username.
        if (getSharedPreferences("bluebank", MODE_PRIVATE).contains("username")) {
            Username = (EditText) findViewById(R.id.userNameInput);
            Username.setText(getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", ""));
        }
        if (getSharedPreferences("bluebank", MODE_PRIVATE).contains("ip")) {
            IPBox = (EditText) findViewById(R.id.ipAddrInput);
            IPBox.setText(getSharedPreferences("bluebank", MODE_PRIVATE).getString("ip", ""));
        }

        // Biometrics on all qualifying devices.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mSpass = new Spass();
            try {
                mSpass.initialize(MainActivity.this);
            } catch (Exception e) {
                Toast.makeText(this, "Fingerprint scanner init failure: " + e.toString(), Toast.LENGTH_LONG).show();
            }
            isFeatureEnabled = mSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);

            if (isFeatureEnabled) {
                mSpassFingerprint = new SpassFingerprint(MainActivity.this);
                mSpassFingerprint.startIdentify(listener);
            } else {
                Toast.makeText(this, "Fingerprint service is not supported in the device.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void createAccountClick(View v){
        IPBox = (EditText) findViewById(R.id.ipAddrInput);

        if(TextUtils.isEmpty(IPBox.getText().toString())){
            IPBox.setError("Please enter an IP address");
            return;
        }

        // Save IP address to SharedPrefs.
        SharedPreferences.Editor editor = getApplicationContext()
                .getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
        editor.putString("ip", IPBox.getText().toString());
        editor.apply();

        // Send intent to move to CreateAccountActivity.
        Intent createAccountIntent = new Intent(MainActivity.this,CreateAccountActivity.class);
        startActivity(createAccountIntent);
    }

    public void submitClick(View v){
        // Hard coded dummy username/password:  "dummy / Password1"

        Username = (EditText) findViewById(R.id.userNameInput);
        Password = (EditText) findViewById(R.id.passwordInput);
        IPBox = (EditText) findViewById(R.id.ipAddrInput);

        if(TextUtils.isEmpty(Username.getText().toString())){
            Username.setError("Please username");
            return;
        }

        if(TextUtils.isEmpty(Password.getText().toString())){
            Password.setError("Please enter password");
            return;
        }

        if(TextUtils.isEmpty(IPBox.getText().toString())){
            IPBox.setError("Please enter an IP address");
            return;
        }

        if (Username.getText().toString().equals("dummy") &&
                Password.getText().toString().equals("Password1")){
            startActivity(new Intent(getBaseContext(), BankMainActivity.class));
        }

        // Save IP address to SharedPrefs.
        SharedPreferences.Editor editor = getApplicationContext()
                .getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
        editor.putString("ip", IPBox.getText().toString());
        editor.putString("password", Password.getText().toString());
        editor.apply();

        // Pull invalid attempt data and do a calculation to get the time delta.
        int numInvalid = 0;
        Date lastInvalid = new Date();
        try {
            numInvalid = getSharedPreferences("bluebank", MODE_PRIVATE).getInt("numInvalid", 0);
            lastInvalid = sdf.parse(getSharedPreferences("bluebank", MODE_PRIVATE).getString("lastInvalid", "01/01/1970/00/00/00"));
        } catch (Exception e){
            Toast.makeText(this, "Something may have gone wrong.", Toast.LENGTH_LONG).show();
        }
        long delta = new Date().getTime() - lastInvalid.getTime();

        // Check if the client is locked out because of failed logins.
        if ((numInvalid >= ClientLogic.LOCKOUT_THRESHOLD) && (delta < ClientLogic.LOCKOUT_DURATION)) {
            // Client is locked out.  Toast with info.
            Toast.makeText(this,
                    "Client is locked due to an excess of failed logins. Please try back later. "+
                            "The lockout duration is: " + ClientLogic.LOCKOUT_DURATION/1000 + " seconds.",
                    Toast.LENGTH_LONG).show();
            return;
        } else if (delta >= ClientLogic.LOCKOUT_DURATION) {
            // If we've gone past the duration, reset the counter.  Then send the request.
            numInvalid = 0;
            editor = getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
            editor.putInt("numInvalid", numInvalid);
            editor.apply();
        }

        //Send LOGIN request to server.
        StringBuilder req = new StringBuilder();

        // Create the request string
        // op code | username | password
        // 0#1  #2
        // l#jlm#letmein0
        req.append("l" + ClientLogic.DELIM); // OP CODE
        req.append(Username.getText().toString() + ClientLogic.DELIM); // USERNAME
        req.append(Password.getText().toString()); // PASSWORD

        editor = getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
        editor.putString("BIOMETRIC_STORE_"+Username.getText().toString(), Password.getText().toString());
        editor.apply();

        // Send the request string and get the response.
        new ClientLogic.LoginRequest().execute(this, req.toString(), IPBox.getText().toString());
    }

    public void bioLogin(){
        Username = (EditText) findViewById(R.id.userNameInput);
        IPBox = (EditText) findViewById(R.id.ipAddrInput);
        SharedPreferences.Editor editor;

        if(TextUtils.isEmpty(Username.getText().toString())){
            Username.setError("Please username");
            return;
        }

        if(TextUtils.isEmpty(IPBox.getText().toString())){
            IPBox.setError("Please enter an IP address");
            return;
        }

        // Pull invalid attempt data and do a calculation to get the time delta.
        int numInvalid = 0;
        Date lastInvalid = new Date();
        try {
            numInvalid = getSharedPreferences("bluebank", MODE_PRIVATE).getInt("numInvalid", 0);
            lastInvalid = sdf.parse(getSharedPreferences("bluebank", MODE_PRIVATE).getString("lastInvalid", "01/01/1970/00/00/00"));
        } catch (Exception e){
            Toast.makeText(this, "Something may have gone wrong.", Toast.LENGTH_LONG).show();
        }
        long delta = new Date().getTime() - lastInvalid.getTime();

        // Check if the client is locked out because of failed logins.
        if ((numInvalid >= ClientLogic.LOCKOUT_THRESHOLD) && (delta < ClientLogic.LOCKOUT_DURATION)) {
            // Client is locked out.  Toast with info.
            Toast.makeText(this,
                    "Client is locked due to an excess of failed logins. Please try back later. "+
                            "The lockout duration is: " + ClientLogic.LOCKOUT_DURATION/1000 + " seconds.",
                    Toast.LENGTH_LONG).show();
            return;
        } else if (delta >= ClientLogic.LOCKOUT_DURATION) {
            // If we've gone past the duration, reset the counter.  Then send the request.
            numInvalid = 0;
            editor = getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
            editor.putInt("numInvalid", numInvalid);
            editor.apply();
        }

        // Set the password from
        Password = (EditText) findViewById(R.id.passwordInput);
        Password.setText(getSharedPreferences("bluebank", MODE_PRIVATE)
                .getString("BIOMETRIC_STORE_"+Username.getText().toString(), ""));

        //Send LOGIN request to server.
        StringBuilder req = new StringBuilder();

        // Create the request string
        // op code | username | password
        // 0#1  #2
        // l#jlm#letmein0
        req.append("l" + ClientLogic.DELIM); // OP CODE
        req.append(Username.getText().toString() + ClientLogic.DELIM); // USERNAME
        req.append(Password.getText().toString()); // PASSWORD

        // Send the request string and get the response.
        new ClientLogic.LoginRequest().execute(this, req.toString(), IPBox.getText().toString());
    }

}

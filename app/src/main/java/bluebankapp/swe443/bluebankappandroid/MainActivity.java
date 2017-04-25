package bluebankapp.swe443.bluebankappandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {
    EditText Username, Password, IPBox;

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
        editor.apply();

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

package bluebankapp.swe443.bluebankappandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.User;


public class CreateAccountActivity extends AppCompatActivity {

    private EditText name;
    private EditText ssn;
    private EditText dob;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText initial;
    Bank blue;
    Account acct;
    User new_user;
    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        blue = (Bank) getIntent().getParcelableExtra("bank");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        name = (EditText) findViewById(R.id.fullNameEdit);
        ssn = (EditText) findViewById(R.id.ssnEdit);
        dob = (EditText) findViewById(R.id.birthdayEdit);
        email = (EditText) findViewById(R.id.emailEdit);
        username = (EditText) findViewById(R.id.usernameEdit);
        password = (EditText) findViewById(R.id.passwordEdit);
        initial = (EditText) findViewById(R.id.initialDepositEdit);
    }

    public void CreateAccountBtn(View v){
        Toast error;
        acct = new Account();
        new_user = new User();
        //boolean valid = false;
        String regexSSN = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
        String regexDOB = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        String regexNOTNAME = ".*\\d+.*";

        Pattern pattern = Pattern.compile(regexNOTNAME);
        Matcher matcher = pattern.matcher(name.getText().toString());
        if(TextUtils.isEmpty(name.getText().toString())){
            name.setError("Please enter name");
            return;
        }
        else if (matcher.matches()){
            name.setError("Name contains decimals, Please enter name without decimals.");
            return;
        }
        else {
            acct.setName(name.getText().toString());
            new_user.setUserName(name.getText().toString());
        }

        if(TextUtils.isEmpty(ssn.getText().toString())){
            ssn.setError("Please enter ssn");
            return;
        } else {
            acct.setSsn(Integer.parseInt(ssn.getText().toString()));
        }

        if(TextUtils.isEmpty(dob.getText().toString())){
            dob.setError("Please enter DOB");
            return;
        } else {
            acct.setDob(dob.getText().toString());
        }

        if(TextUtils.isEmpty(username.getText().toString())){
            username.setError("Please enter username");
            return;
        } else {
            acct.setUsername(username.getText().toString());
        }

        if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("Please enter password");
            return;
        } else if(password.length()<8) {
            password.setError("Password must be at least 8 characters");
            return;
        } else {
            acct.setPassword(password.getText().toString());
        }

        if(TextUtils.isEmpty(initial.getText().toString())){
            initial.setError("Please enter initial amount");
            return;
        } else {
            acct.setInitialAmount(Double.parseDouble(initial.getText().toString()));
            acct.setAccountBalance(Double.parseDouble(initial.getText().toString()));
        }

        // Unpack SharedPrefs to get the IP of the server.
        String ip = getSharedPreferences("bluebank", MODE_PRIVATE).getString("ip", "");

        // Send Create Account request to server.
        StringBuilder req = new StringBuilder();

        // Create the request string
        // op code | username | password | real name | email | ssn | dob | initial deposit
        // 0#1  #2      #3     #4          #5   #6       #7
        // c#jlm#letmein0#jmiers#j@gmail.com#1234#1/1/1995#500
        req.append("c" + ClientLogic.DELIM); // OP CODE
        req.append(username.getText().toString() + ClientLogic.DELIM); // USERNAME
        req.append(password.getText().toString() + ClientLogic.DELIM); // PASSWORD
        req.append(name.getText().toString() + ClientLogic.DELIM); // REAL NAME
        req.append(email.getText().toString() + ClientLogic.DELIM); // EMAIL
        req.append(ssn.getText().toString() + ClientLogic.DELIM); // SSN
        req.append(dob.getText().toString() + ClientLogic.DELIM); // DOB
        req.append(initial.getText().toString()); // INITIAL DEPOSIT

        // Send the request string and get the response.
        new ClientLogic.CreateAccountRequest().execute(this, req.toString(), ip);

        Toast.makeText(getBaseContext(), "Create request sent.", Toast.LENGTH_SHORT).show();

        /*
        blue.withAccount_Has(acct);
        acct.withBank_has(blue);
        new_user.withAccount_Has(acct);
        //Toast.makeText(this, blue.getAccount_Has().get(0).getUsername(), Toast.LENGTH_SHORT).show();
        Intent bankMainIntent = new Intent(CreateAccountActivity.this,BankMainActivity.class);
        bankMainIntent.putExtra("bank",blue);
        bankMainIntent.putExtra("current_acct",acct);
        startActivity(bankMainIntent);
        finish();
        */
    }
}

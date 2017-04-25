package bluebankapp.swe443.bluebankappandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;

public class BankMainActivity extends AppCompatActivity {
    Bank blue;
    Account current_acct;
    TextView acct_amount,current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //blue = (Bank) getIntent().getParcelableExtra("bank");
        //current_acct = (Account) getIntent().getParcelableExtra("current_acct");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_main);
        // setters
        acct_amount = (TextView) findViewById(R.id.currentBalanceTxt);
        current_user= (TextView) findViewById(R.id.currentUser);
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshValues();
    }

    public void refreshValues(){
        current_user.setText(getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", ""));
        acct_amount.setText("Current Balance: $" +
                getSharedPreferences("bluebank", MODE_PRIVATE).getFloat("balance", 0));
    }

    public void BalanceClickRefresh(View v){
        String u = getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", "");
        String p = getSharedPreferences("bluebank", MODE_PRIVATE).getString("password", "");
        String ip = getSharedPreferences("bluebank", MODE_PRIVATE).getString("ip", "");

        //Send REFRESH request to server.
        StringBuilder req = new StringBuilder();

        // Create the request string
        // op code | username | password | amount
        // 0  #1  #2       #3
        // w/d#jlm#letmein0#50.00
        req.append("r" + ClientLogic.DELIM); // OP CODE
        req.append(u + ClientLogic.DELIM); // USERNAME
        req.append(p); // PASSWORD

        // Send the request string and get the response.
        new ClientLogic.RefreshRequest().execute(this, req.toString(), ip);
    }

    //withdraw button amount
    public void WithdrawDepositBtnClick(View v){
        Intent depositWithdrawIntent = new Intent(BankMainActivity.this,WithdrawDepositActivity.class);
        startActivity(depositWithdrawIntent);
    }

    public  void transferMoneyClick(View v){
        Intent transferIntent = new Intent(BankMainActivity.this,TransferActivity.class);
        startActivity(transferIntent);
    }
    public void manageAccountClick(View v){
        Intent manageIntent = new Intent(BankMainActivity.this,ManageAccountActivity.class);
        startActivity(manageIntent);
    }
    public void supportClick(View v){
        Intent supportActivityIntent = new Intent(BankMainActivity.this,SupportActivity.class);
        startActivity(supportActivityIntent);
    }

    public void bindBalance(){
        acct_amount.setText("Balance: $" + Double.toString(current_acct.getAccountBalance()));
        current_user.setText(current_acct.getName()+"'s Account");
    }

    public void logOutClick(View v){
        SharedPreferences.Editor editor = getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
        // To log out, reset all locally stored account information.
        // Leave the username so that the field auto-populates on the login screen.
        // Similarly, leave the IP so that the user doesnt have to re-type it.
        editor.putString("password", "");
        editor.putFloat("balance", 0);
        editor.putString("ssn", "");
        editor.putString("dob", "");
        editor.putString("email", "");
        editor.putString("fullname", "");
        editor.apply();

        // Go back to login.
        finish();
    }
}

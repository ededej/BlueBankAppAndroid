package bluebankapp.swe443.bluebankappandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.User;

public class BankMainActivity extends AppCompatActivity {
    Bank blue;
    Account current_acct;
    TextView acct_amount,current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        blue = (Bank) getIntent().getParcelableExtra("bank");
        current_acct = (Account) getIntent().getParcelableExtra("current_acct");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_main);
        //gets balances and prints it out on screen
        acct_amount = (TextView) findViewById(R.id.currentBalanceTxt);
        current_user= (TextView) findViewById(R.id.currentUser);
        if(blue!=null){
            bindBalance();
        }else{
            Toast.makeText(this, "No Bank", Toast.LENGTH_LONG).show();
        }

    }

    //withdraw button amount
    public void DrawDepositBtnClick(View v){
        Intent depositWithdrawIntent = new Intent(BankMainActivity.this,WithdrawDepositActivity.class);
        depositWithdrawIntent.putExtra("bank",blue);
        depositWithdrawIntent.putExtra("current_acct",current_acct);
        startActivityForResult(depositWithdrawIntent,1);
    }
    //getting the result back if we need to do something here
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == BankMainActivity.RESULT_OK){
                String result=data.getStringExtra("result");
            }
            if (resultCode == BankMainActivity.RESULT_CANCELED) {
                //Write your code if there's no result
                Toast.makeText(this, "Successful Back", Toast.LENGTH_LONG).show();
                bindBalance();

            }
        }
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
    public void logOutClick(View v){
        Toast.makeText(this, blue.getAccount_Has().get(0).getUsername(), Toast.LENGTH_SHORT);
        /*Intent logOutActivityIntent = new Intent(BankMainActivity.this,MainActivity.class);
        logOutActivityIntent.putExtra("bank",blue);
        startActivity(logOutActivityIntent);
        finish();*/
    }

    public void bindBalance(){
        acct_amount.setText("Balance: $" + Double.toString(current_acct.getAccountBalance()));
        current_user.setText(current_acct.getName()+"'s Account");
    }
}

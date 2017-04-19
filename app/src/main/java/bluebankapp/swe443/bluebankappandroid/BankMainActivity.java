package bluebankapp.swe443.bluebankappandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.User;

public class BankMainActivity extends AppCompatActivity {
    Bank blue;
    Account current_acct;
    TextView acct_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        blue = (Bank) getIntent().getParcelableExtra("bank");
        current_acct = (Account) getIntent().getParcelableExtra("current_acct");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_main);
        //gets balances and prints it out on screen
        acct_amount = (TextView) findViewById(R.id.currentBalanceTxt);
       acct_amount.setText("Username: "+ current_acct.getName()+ " Balance: $" + Double.toString(current_acct.getAccountBalance()));

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
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
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
}

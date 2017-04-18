package bluebankapp.swe443.bluebankappandroid;

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
        acct_amount.setText("Balance: $" + Double.toString(current_acct.getAccountBalance()));

    }
    public void DrawDepositBtnClick(View v){
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
}

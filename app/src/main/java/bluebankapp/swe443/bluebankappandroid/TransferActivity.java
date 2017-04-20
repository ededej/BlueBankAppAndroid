package bluebankapp.swe443.bluebankappandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.User;

public class TransferActivity extends AppCompatActivity {

    Button submitBtn;
    TextView amountText;
    Bank blue;
    Account current_acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        blue = (Bank) getIntent().getParcelableExtra("bank");
        current_acct = (Account) getIntent().getParcelableExtra("current_acct");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        submitBtn=(Button) findViewById(R.id.submitBtn);
        //gets balances and prints it out on screen
        amountText = (TextView) findViewById(R.id.currentBalanceTxt);
        if(blue!=null){
            bindBalance();
        }else{
            Toast.makeText(this, "No Bank", Toast.LENGTH_LONG).show();
        }
    }



    public void bindBalance(){
        amountText.setText("Balance: $" + Double.toString(current_acct.getAccountBalance()));
    }
}

package bluebankapp.swe443.bluebankappandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;

public class WithdrawDepositActivity extends AppCompatActivity {

    Button submitBtn;
    EditText amountText;
    String mode;
    Bank blue;
    Account current_acct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_deposit);
        blue = (Bank) getIntent().getParcelableExtra("bank");
        current_acct = (Account) getIntent().getParcelableExtra("current_acct");
        submitBtn=(Button) findViewById(R.id.submitBtn);
        amountText=(EditText) findViewById(R.id.editAmount);
        Toast.makeText(this, "Username: "+ current_acct.getName()+ " Balance: $" + Double.toString(current_acct.getAccountBalance()), Toast.LENGTH_LONG).show();

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.depositRadio:
                if (checked) {
//                    Toast.makeText(this, "Deposit", Toast.LENGTH_LONG).show();
                    submitBtn.setVisibility(View.VISIBLE);
                    submitBtn.setText("Deposit");
                    mode="Deposit";
                }
                break;
            case R.id.withDrawRadio:
                if (checked) {
//                    Toast.makeText(this, "Withdraw", Toast.LENGTH_LONG).show();
                    submitBtn.setVisibility(View.VISIBLE);
                    submitBtn.setText("Withdraw");
                    mode="Withdraw";

                }
                break;
        }
    }

    public void doTransactionClick(View v){
        if(validateInput()){
            Double amountDb= Double.parseDouble(amountText.getText().toString());
            if(mode.equals("Deposit")) {
                current_acct.deposit(amountDb);
                Toast.makeText(this, "Deposit"+current_acct.getAccountBalance(), Toast.LENGTH_LONG).show();

            }else{
                current_acct.withdraw(amountDb);
                Toast.makeText(this, "Withdraw"+current_acct.getAccountBalance(), Toast.LENGTH_LONG).show();
            }
        }

    }

    public boolean validateInput(){
        if(TextUtils.isEmpty(amountText.getText().toString())){
            amountText.setError("Insert Amount");
            return false;
        }
        return true;
    }
}

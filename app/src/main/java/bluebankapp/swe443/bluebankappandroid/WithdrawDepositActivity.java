package bluebankapp.swe443.bluebankappandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class WithdrawDepositActivity extends AppCompatActivity{

    Button submitBtn;
    EditText amountText;
    Switch wdSwitch;
    String mode;
    //Bank blue;
    //Account current_acct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_deposit);
        //blue = (Bank) getIntent().getParcelableExtra("bank");
        //current_acct = (Account) getIntent().getParcelableExtra("current_acct");
        submitBtn=(Button) findViewById(R.id.submitBtn);
        amountText=(EditText) findViewById(R.id.editAmount);
        wdSwitch=(Switch) findViewById(R.id.wdSwitch);
        //Toast.makeText(this, "Username: "+ current_acct.getName()+ " Balance: $" + Double.toString(current_acct.getAccountBalance()), Toast.LENGTH_LONG).show();

        wdSwitch.setText("Withdraw");
        submitBtn.setVisibility(View.VISIBLE);
        submitBtn.setText("Withdraw");
        mode="Withdraw";
    }

    public void onSwitchToggle(View view){
        boolean withdraw = ((Switch) view).isChecked();

        if (withdraw){
            ((Switch) view).setText("Withdraw");
            submitBtn.setVisibility(View.VISIBLE);
            submitBtn.setText("Withdraw");
            mode="Withdraw";
        } else {
            ((Switch) view).setText("Deposit");
            submitBtn.setVisibility(View.VISIBLE);
            submitBtn.setText("Deposit");
            mode="Deposit";
        }
    }

    public void doTransactionClick(View v){
        if(validateInput()){
            Double amountDb= Double.parseDouble(amountText.getText().toString());
            String u = getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", "");
            String p = getSharedPreferences("bluebank", MODE_PRIVATE).getString("password", "");
            String ip = getSharedPreferences("bluebank", MODE_PRIVATE).getString("ip", "");
            String opcode = (mode.equals("Deposit")) ? "d" : "w";

            //Send LOGIN request to server.
            StringBuilder req = new StringBuilder();

            // Create the request string
            // op code | username | password | amount
            // 0  #1  #2       #3
            // w/d#jlm#letmein0#50.00
            req.append(opcode + ClientLogic.DELIM); // OP CODE
            req.append(u + ClientLogic.DELIM); // USERNAME
            req.append(p + ClientLogic.DELIM); // PASSWORD
            req.append(Double.toString(amountDb)); // AMOUNT

            // Send the request string and get the response.
            new ClientLogic.ServerRequest().execute(this, req.toString(), ip);

            /*
            Intent goToMainIntent = new Intent(WithdrawDepositActivity.this,BankMainActivity.class);
            double temp= current_acct.getAccountBalance() +amountDb;// to check
            current_acct.deposit(amountDb);
            if ((temp)==current_acct.getAccountBalance()){
                Toast.makeText(this, "Successful Deposit", Toast.LENGTH_LONG).show();
                goToMainIntent.putExtra("bank",blue);
                goToMainIntent.putExtra("current_acct",current_acct);
                startActivity(goToMainIntent);
            }*/
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

package bluebankapp.swe443.bluebankappandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class TransferActivity extends AppCompatActivity {

    EditText editAmount;
    EditText personNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
    }

    public void doTransactionClick(View v){
        editAmount=(EditText) findViewById(R.id.editAmount);
        personNameEdit=(EditText) findViewById(R.id.personNameEdit);
        if(validateInput()){
            Double amountDb= Double.parseDouble(editAmount.getText().toString());
            String u = getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", "");
            String p = getSharedPreferences("bluebank", MODE_PRIVATE).getString("password", "");
            String ip = getSharedPreferences("bluebank", MODE_PRIVATE).getString("ip", "");

            // Send LOGIN request to server.
            StringBuilder req = new StringBuilder();

            // Create the request string
            // op code | username | password | amount
            // 0  #1  #2       #3  #4
            // w/d#jlm#letmein0#abc#50.00
            req.append("t" + ClientLogic.DELIM); // OP CODE
            req.append(u + ClientLogic.DELIM); // USERNAME
            req.append(p + ClientLogic.DELIM); // PASSWORD
            req.append(personNameEdit.getText().toString() + ClientLogic.DELIM); // DESTINATION
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
        if(TextUtils.isEmpty(editAmount.getText().toString())){
            editAmount.setError("Insert Amount");
            return false;
        }
        return true;
    }
}

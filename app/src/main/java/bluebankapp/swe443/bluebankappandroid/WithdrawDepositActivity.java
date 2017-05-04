package bluebankapp.swe443.bluebankappandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

public class WithdrawDepositActivity extends AppCompatActivity{

    Button submitBtn;
    EditText amountText;
    String mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Withdraw/Deposit");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_deposit);
        submitBtn=(Button) findViewById(R.id.submitBtn);
        amountText=(EditText) findViewById(R.id.editAmount);
        submitBtn.setVisibility(View.GONE);
    }

    public void onCheckedButton(View v){

    }

    public void onRadioButtonClicked(View view) {
        // Is the drawable.button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio drawable.button was clicked
        switch(view.getId()) {
            case R.id.withDrawBtn:
                if (checked)
                    submitBtn.setText("Withdraw");
                    submitBtn.setVisibility(View.VISIBLE);
                    break;
            case R.id.depositBtn:
                if (checked)
                    submitBtn.setText("Deposit");
                    submitBtn.setVisibility(View.VISIBLE);
                    break;
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

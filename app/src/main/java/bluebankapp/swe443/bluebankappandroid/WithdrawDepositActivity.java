package bluebankapp.swe443.bluebankappandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class WithdrawDepositActivity extends AppCompatActivity {

    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_deposit);
        submitBtn=(Button) findViewById(R.id.submitBtn);
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
                }
                break;
            case R.id.withDrawRadio:
                if (checked) {
//                    Toast.makeText(this, "Withdraw", Toast.LENGTH_LONG).show();
                    submitBtn.setVisibility(View.VISIBLE);
                    submitBtn.setText("Withdraw");
                }
                break;
        }
    }
}

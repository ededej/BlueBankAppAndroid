package bluebankapp.swe443.bluebankappandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;


public class ManageAccountActivity extends AppCompatActivity {

    EditText confirmType;
    EditText oldType;
    EditText newType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        confirmType= (EditText) findViewById(R.id.confirmEditText);
        oldType= (EditText) findViewById(R.id.oldEditText);
        newType= (EditText) findViewById(R.id.newEditText);

    }

    public void onRadioButtonClicked(View view) {
        // Is the drawable.button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio drawable.button was clicked
        switch (view.getId()) {
            case R.id.nameRadio:
                if (checked) {
//                    Toast.makeText(this, "Deposit", Toast.LENGTH_LONG).show();
                    //submitBtn.setVisibility(View.VISIBLE);
                    //submitBtn.setText("Deposit");
                    oldType.setHint("Your Old name");
                    newType.setHint("Your new name");
                    confirmType.setHint("Confirm name");

                }
                break;
            case R.id.emailRadio:
                if (checked) {
//                    Toast.makeText(this, "Withdraw", Toast.LENGTH_LONG).show();
                    oldType.setHint("Your Old email");
                    newType.setHint("Your new email");
                    confirmType.setHint("Confirm email");

                }
                break;
            case R.id.passwordRadio:
                if (checked) {
//                    Toast.makeText(this, "Withdraw", Toast.LENGTH_LONG).show();
                    oldType.setHint("Your Old password");
                    newType.setHint("Your new password");
                    confirmType.setHint("Confirm password");

                }
                break;
        }
    }
}

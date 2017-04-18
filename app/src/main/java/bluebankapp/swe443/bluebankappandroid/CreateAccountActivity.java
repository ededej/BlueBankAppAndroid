package bluebankapp.swe443.bluebankappandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.stefan.myapplication.resource.Account;
import com.example.stefan.myapplication.resource.Bank;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText name;
    private EditText ssn;
    private EditText dob;
    private EditText email;
    private EditText username;
    private EditText password;
    Bank blue;
    Account acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        blue = new Bank();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public boolean validate() {
        boolean valid = true;

        String name = this.name.getText().toString();
        String ssn = this.ssn.getText().toString();
        String dob = this.dob.getText().toString();
        String email = this.email.getText().toString();
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();

        if(name.isEmpty() || !name.matches("[ \\w]")) {
            return false;
        }

        if(ssn.isEmpty() || ssn.matches("[a-zA-Z]")) {
            return false;
        }

        if(dob.isEmpty() || dob.matches("[a-zA-Z]")) {
            return false;
        }

        if(email.isEmpty() || !email.matches("[@]")) {
            return false;
        }

        if(username.isEmpty()) {    //Are there any rules for usernames?
            return false;
        }

        if(password.isEmpty() || password.length() < 4) {
            return false;
        }

        return true;
    }
}

package bluebankapp.swe443.bluebankappandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.User;


public class CreateAccountActivity extends AppCompatActivity {

    private EditText name;
    private EditText ssn;
    private EditText dob;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText initial;
    Bank blue;
    Account acct;
    User new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        blue = (Bank) getIntent().getParcelableExtra("bank");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        name = (EditText) findViewById(R.id.fullNameEdit);
        ssn = (EditText) findViewById(R.id.ssnEdit);
        dob = (EditText) findViewById(R.id.birthdayEdit);
        email = (EditText) findViewById(R.id.emailEdit);
        username = (EditText) findViewById(R.id.usernameEdit);
        password = (EditText) findViewById(R.id.passwordEdit);
        initial = (EditText) findViewById(R.id.initialDepositEdit);

    }

    /*public boolean validate() {
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
    }*/

    public void CreateAccountBtn(View v){
        Toast error;
        acct = new Account();
        new_user = new User();
        boolean valid = false;

        if(TextUtils.isEmpty(name.getText().toString())){
            error = Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT);
            error.show();
            valid = false;
        } else {
            acct.setName(name.getText().toString());
            new_user.setUserName(name.getText().toString());
            valid = true;
        }

        if(TextUtils.isEmpty(ssn.getText().toString())){
            error = Toast.makeText(this, "Please enter ssn", Toast.LENGTH_SHORT);
            error.show();
            valid = false;
        } else {
            acct.setSsn(Integer.parseInt(ssn.getText().toString()));
            valid = true;
        }

        if(TextUtils.isEmpty(dob.getText().toString())){
            error = Toast.makeText(this, "Please enter DOB", Toast.LENGTH_SHORT);
            error.show();
            valid = false;
        } else {
            acct.setDob(dob.getText().toString());
            valid = true;
        }

        if(TextUtils.isEmpty(username.getText().toString())){
            error = Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT);
            error.show();
            valid = false;
        } else {
            acct.setUsername(username.getText().toString());
            valid = true;
        }

        if(TextUtils.isEmpty(password.getText().toString())){
            error = Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT);
            error.show();
            valid = false;
        } else {
            acct.setPassword(password.getText().toString());
            valid = true;
        }

        if(TextUtils.isEmpty(initial.getText().toString())){
            error = Toast.makeText(this, "Please enter initial amount", Toast.LENGTH_SHORT);
            error.show();
            valid = false;
        } else {
            acct.setInitialAmount(Double.parseDouble(initial.getText().toString()));
            acct.setAccountBalance(Double.parseDouble(initial.getText().toString()));
            valid = true;
        }


        if(valid == true){
            blue.withAccount_Has(acct);
            acct.withBank_has(blue);
            new_user.withAccount_Has(acct);

            Intent bankMainIntent = new Intent(CreateAccountActivity.this,BankMainActivity.class);
            bankMainIntent.putExtra("bank",blue);
            bankMainIntent.putExtra("current_acct",acct);
            startActivity(bankMainIntent);
        }
    }
}

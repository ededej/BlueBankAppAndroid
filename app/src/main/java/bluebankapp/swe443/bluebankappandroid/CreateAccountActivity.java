package bluebankapp.swe443.bluebankappandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.*;

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

    public void CreateAccountBtn(View v){
        Toast error;
        acct = new Account();
        new_user = new User();
        //boolean valid = false;
        String regexSSN = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
        String regexDOB = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";

        if(TextUtils.isEmpty(name.getText().toString())){
//            error = Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT);
//            error.show();
            //valid = false;
            name.setError("Please enter name");
            return;
        }
        //need to fix this regex
//        else if(name.getText().toString().matches("[a-zA-Z]")){
//            name.setError("Only characters!!");
//            return;
//        }
        else {
            acct.setName(name.getText().toString());
            new_user.setUserName(name.getText().toString());
            //valid = true;
        }

        if(TextUtils.isEmpty(ssn.getText().toString())){
//            error = Toast.makeText(this, "Please enter ssn", Toast.LENGTH_SHORT);
//            error.show();
//            //valid = false;
            ssn.setError("Please enter ssn");
            return;
        }
//        else if (ssn.getText().toString().matches(regexSSN)) {
//            ssn.setError("Not valid SSN");
//            return;
//        }
        else {
            acct.setSsn(Integer.parseInt(ssn.getText().toString()));
            //valid = true;
        }

        if(TextUtils.isEmpty(dob.getText().toString())){
//            error = Toast.makeText(this, "Please enter DOB", Toast.LENGTH_SHORT);
//            error.show();
            //valid = false;
            dob.setError("Please enter DOB");
            return;
        }
//        else if (dob.getText().toString().matches(regexDOB)) {
//            dob.setError("Not valid Date!! mm/dd/yyyy");
//            return;
//        }
        else {
            acct.setDob(dob.getText().toString());
            //valid = true;
        }

        if(TextUtils.isEmpty(username.getText().toString())){
//            error = Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT);
//            error.show();
            //valid = false;
            username.setError("Please enter username");
            return;
        } else {
            acct.setUsername(username.getText().toString());
            //valid = true;
        }

        if(TextUtils.isEmpty(password.getText().toString())){
//            error = Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT);
//            error.show();
//            //valid = false;
            password.setError("Please enter password");
            return;
        } else if(password.length()<8) {
            password.setError("Password must be at least 8 characters");
            return;
        }else {
            acct.setPassword(password.getText().toString());
            //valid = true;
        }

        if(TextUtils.isEmpty(initial.getText().toString())){
//            error = Toast.makeText(this, "Please enter initial amount", Toast.LENGTH_SHORT);
//            error.show();
            //valid = false;
            initial.setError("Please enter initial amount");
            return;
        } else {
            acct.setInitialAmount(Double.parseDouble(initial.getText().toString()));
            acct.setAccountBalance(Double.parseDouble(initial.getText().toString()));
            //valid = true;
        }


        //if(valid == true){
        blue.withAccount_Has(acct);
        acct.withBank_has(blue);
        new_user.withAccount_Has(acct);

        Intent bankMainIntent = new Intent(CreateAccountActivity.this,BankMainActivity.class);
        bankMainIntent.putExtra("bank",blue);
        bankMainIntent.putExtra("current_acct",acct);
        startActivity(bankMainIntent);
        //}
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


}

package bluebankapp.swe443.bluebankappandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.stefan.myapplication.resource.Bank;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createAccountClick(View v){
        Intent createAccountIntent = new Intent(MainActivity.this,CreateAccountActivity.class);
        startActivity(createAccountIntent);
    }
    public void submitClick(View v){
        Bank blue = new Bank();
        Intent bankMainIntent = new Intent(MainActivity.this,BankMainActivity.class);
        startActivity(bankMainIntent);
    }

}

package bluebankapp.swe443.bluebankappandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;


public class MainActivity extends AppCompatActivity {
    Bank blue = new Bank();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void createAccountClick(View v){
        blue.setBankName("Blue Bank");
        Intent createAccountIntent = new Intent(MainActivity.this,CreateAccountActivity.class);
        createAccountIntent.putExtra("bank",blue);
        startActivity(createAccountIntent);
    }
    public void submitClick(View v){
        Intent bankMainIntent = new Intent(MainActivity.this,BankMainActivity.class);
        startActivity(bankMainIntent);
    }

}

package bluebankapp.swe443.bluebankappandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.User;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.util.BankCreator;
import de.uniks.networkparser.IdMap;
import de.uniks.networkparser.json.JsonArray;


public class MainActivity extends AppCompatActivity {
    Bank blue;
    Account toLogin;
    EditText Username, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //testing persistency
        String filename = "myfile";
        FileInputStream inputStream;

        try {
            inputStream = openFileInput(filename);
            String jsonText = new Scanner(inputStream).useDelimiter("\\Z").next();
            IdMap readerMap = BankCreator.createIdMap("demo");
            Object rootObject = readerMap.decode(jsonText);
            blue = (Bank) rootObject;
//            Toast.makeText(this, jsonText, Toast.LENGTH_LONG).show();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //end test
    }

    public void createAccountClick(View v){
        if(blue == null){
            blue = new Bank();
            blue.setBankName("Blue Bank");
        }
        //test 2
        IdMap idMap = BankCreator.createIdMap("demo");
        JsonArray jsonArray = idMap.toJsonArray(blue);
        String jsonText = jsonArray.toString();

        String filename = "myfile";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonText.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //test end
        //Intent createAccountIntent = new Intent(MainActivity.this,CreateAccountActivity.class);
        //createAccountIntent.putExtra("bank",blue);
        //startActivity(createAccountIntent);
    }
    public void submitClick(View v){
        Username = (EditText) findViewById(R.id.userNameInput);
        Password = (EditText) findViewById(R.id.passwordInput);
        if(TextUtils.isEmpty(Username.getText().toString())){
            Username.setError("Please username");
            return;
        }

        if(TextUtils.isEmpty(Password.getText().toString())){
            Password.setError("Please enter password");
            return;
        }
        Toast.makeText(this, blue.getBankName(), Toast.LENGTH_SHORT).show();

        /*if (blue.getAccount_Has().filterUsername(Username.getText().toString()).getUsername().toString().equals("(" + Username.getText().toString() + ")") == false) {   //searches to see if the username exist
            Toast.makeText(this, blue.getAccount_Has().filterUsername(Username.getText().toString()).getUsername().toString(), Toast.LENGTH_SHORT).show();
        }*/

        //Intent bankMainIntent = new Intent(MainActivity.this,BankMainActivity.class);
        //startActivity(bankMainIntent);
    }

}

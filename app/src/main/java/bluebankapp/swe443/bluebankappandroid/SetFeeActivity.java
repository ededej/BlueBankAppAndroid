package bluebankapp.swe443.bluebankappandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetFeeActivity extends AppCompatActivity {

    Button submitBtn;

    EditText dfText;
    EditText wfText;
    EditText tfText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Set Fees");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_fee);
        submitBtn=(Button) findViewById(R.id.makeChanges);
        dfText=(EditText) findViewById(R.id.DepPercent);
        wfText=(EditText) findViewById(R.id.WithPercent);
        tfText=(EditText) findViewById(R.id.TranPercent);
    }

    public void doMakeChanges(View v){
        Double dfee=0.0;
        Double wfee=0.0;
        Double tfee=0.0;
        boolean check=false;
        if(!(dfText.getText().toString().isEmpty())){
             dfee= Double.parseDouble(dfText.getText().toString());
            check=true;
        }
        if(!(wfText.getText().toString().isEmpty())){
             wfee= Double.parseDouble(wfText.getText().toString());
            check=true;

        }
        if(!(tfText.getText().toString().isEmpty())){
             tfee= Double.parseDouble(tfText.getText().toString());
             check=true;
        }
        if(check) {
            String u = getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", "");
            String p = getSharedPreferences("bluebank", MODE_PRIVATE).getString("password", "");
            String ip = getSharedPreferences("bluebank", MODE_PRIVATE).getString("ip", "");

            //Send REFRESH request to server.
            StringBuilder req = new StringBuilder();

            // Create the request string
            req.append("f" + ClientLogic.DELIM);
            req.append(u + ClientLogic.DELIM); // USERNAME
            req.append(p); // PASSWORD
            req.append(ClientLogic.DELIM);
            req.append(Double.toString(dfee));
            req.append(ClientLogic.DELIM);
            req.append(Double.toString(wfee));
            req.append(ClientLogic.DELIM);
            req.append(Double.toString(tfee));
            req.append(ClientLogic.DELIM);


            // Send the request string.  No response string needed for this request.
            new ClientLogic.GenericRequest().execute(this, req.toString(), ip);

        } else {
            Toast.makeText(this, "You need to change one of the fees.", Toast.LENGTH_SHORT).show();
        }
    }
}

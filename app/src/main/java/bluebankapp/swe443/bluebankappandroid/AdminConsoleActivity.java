package bluebankapp.swe443.bluebankappandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminConsoleActivity extends AppCompatActivity {
    Button fees, dispute, trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Admin Console");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_console);
        fees = (Button) findViewById(R.id.setFee);
        dispute = (Button) findViewById(R.id.DisputeTrans);
        trans = (Button) findViewById(R.id.Transaction);
    }

    public void feeClick(View view){
        Intent feeIntent = new Intent(AdminConsoleActivity.this, SetFeeActivity.class);
        startActivity(feeIntent);
    }

    public void transClick(View view){
        Intent transIntent = new Intent(AdminConsoleActivity.this, TransactionActivity.class);
        startActivity(transIntent);

    }
}

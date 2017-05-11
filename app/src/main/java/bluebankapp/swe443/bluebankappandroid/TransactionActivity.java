package bluebankapp.swe443.bluebankappandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {
    boolean isAdmin;
    TextView balance;
    TextView header;
    ListView transList;
    ArrayList<Transaction> recentTransactions;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        balance = (TextView) findViewById(R.id.balanceString);
        header = (TextView) findViewById(R.id.listHeader);
        transList = (ListView) findViewById(R.id.transactionList);
        String u = getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", "");
        String p = getSharedPreferences("bluebank", MODE_PRIVATE).getString("password", "");
        String ip = getSharedPreferences("bluebank", MODE_PRIVATE).getString("ip", "");
        c = this;

        if(isAdmin){
            balance.setVisibility(View.GONE);
            header.setText("All Transactions (incl. disputed)");
        }

        // This listens for dispute long press and does the dispute.
        transList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionActivity.this);
                builder.setMessage("Do you want to dispute this transaction?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Send a dispute request.
                                // x|uname|pass|type|acc1|acc2|fee|amount
                                Transaction t = recentTransactions.get(pos);
                                String req = "x#" + u +"#"+ p +"#"+ t.type +"#"+ t.acc1 +"#"+ t.acc2
                                        +"#"+ Double.toString(t.fee) +"#"+ Double.toString(t.amount);
                                new ClientLogic.DisputeRequest().execute(c, req, ip);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // Create the AlertDialog object and return it
                AlertDialog alert1 = builder.create();
                alert1.show();
                return true;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        // This will be called every time the app reaches this screen to refresh the transaction list.
        PageRefresh(null);
    }

    // This is a function to refresh the balance and the transaction list.
    // It is called from the refresh button callback, and from the onResume.
    public void refreshPage(){
        TextView balanceText = (TextView) findViewById(R.id.balanceString);
        recentTransactions = new ArrayList<>();

        // Unpack SharedPrefs to check for a pre-populated username.
        if (getSharedPreferences("bluebank", MODE_PRIVATE).contains("balance")) {
            float currBalance = getSharedPreferences("bluebank", MODE_PRIVATE).getFloat("balance", 0);
            balanceText.setText(String.format("Current Balance: $%.2f", currBalance));
        }
        if (getSharedPreferences("bluebank", MODE_PRIVATE).contains("transactions")) {
            String trans = getSharedPreferences("bluebank", MODE_PRIVATE).getString("transactions", "");
            String[] transactions = trans.split("#");
            for (String t : transactions){
                recentTransactions.add(new Transaction(t));
            }
        }

        Transaction[] arr = new Transaction[recentTransactions.size()];
        arr = recentTransactions.toArray(arr);

        TransactionAdapter adapt = new TransactionAdapter(this,
                R.layout.transaction_list_item, arr);

        // Set the list adapter to be shown.
        transList.setAdapter(adapt);
    }

    public void PageRefresh(View v){
        String u = getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", "");
        String p = getSharedPreferences("bluebank", MODE_PRIVATE).getString("password", "");
        String ip = getSharedPreferences("bluebank", MODE_PRIVATE).getString("ip", "");

        //Send REFRESH request to server.
        StringBuilder req = new StringBuilder();

        // Create the request string
        // op code | username | password | amount
        // 0  #1  #2       #3
        // r#jlm#letmein0#50.00

        if(!isAdmin){
            req.append("h" + ClientLogic.DELIM); // OP CODE
        } else {
            req.append("a" + ClientLogic.DELIM); // OP CODE
        }
        req.append(u + ClientLogic.DELIM); // USERNAME
        req.append(p); // PASSWORD

        // Send the request string and get the response.
        new ClientLogic.TransactionRefreshRequest().execute(this, req.toString(), ip);
    }
}

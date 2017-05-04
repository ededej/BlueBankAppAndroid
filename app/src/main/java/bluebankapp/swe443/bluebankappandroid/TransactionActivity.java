package bluebankapp.swe443.bluebankappandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {
    boolean isAdmin;
    TextView balance;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        balance = (TextView) findViewById(R.id.balanceString);
        header = (TextView) findViewById(R.id.listHeader);
        if(isAdmin == true){
            balance.setVisibility(View.GONE);
            header.setText("All Transaction");
        }
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
        ListView transList = (ListView) findViewById(R.id.transactionList);
        ArrayList<Transaction> recentTransactions = new ArrayList<>();

        // Unpack SharedPrefs to check for a pre-populated username.
        if (getSharedPreferences("bluebank", MODE_PRIVATE).contains("balance")) {
            float currBalance = getSharedPreferences("bluebank", MODE_PRIVATE).getFloat("balance", 0);
            balanceText.setText(String.format("Current Balance: $%.2f", currBalance));
        }
        if (getSharedPreferences("bluebank", MODE_PRIVATE).contains("transactions")) {
            String trans = getSharedPreferences("bluebank", MODE_PRIVATE).getString("transactions", "");
            System.out.println("TRANSACTIONS: "+trans);
            String[] transactions = trans.split("#");
            for (String t : transactions){
                recentTransactions.add(new Transaction(t));
            }
        }

        //TODO: Modify the server to return the most recent X transactions with each user state update.
        //      This will involve modifying the Dude class to hold the most recent X transactions.
        //      In addition, you will need to modify the toString() and the string constructor.  These are
        //      the functions that allow us to send "Dude" objects over the network.
        //      Consider sending each transaction on a new line following the transmission of the original Dude object.
        //TODO: Modify the response handler in ClientLogic.TransactionRefreshRequest.onPostExecute to receive the transactions
        //      and store them in SharedPreferences.
        //TODO: Fill in code just below this to pull the Transactions from SharedPreferences.
        //TODO: TransactionAdapter.java line 48: Add logic to go from a Transaction object to the correct display strings.

        // Create list adapter here:
        // Pull most recent X transactions from sharedPrefs and put them in recentTransactions.
        //Transaction[] recentTransactions = new Transaction[5];  //populate me!

        // Encapsulate recentTransactions in an ArrayAdapter subclass custom for displaying Transactions.
        // This class is transaction_list_item.xml.  Observe the four fields that we care about displaying.
        // The TransactionAdapter deals with turning a list of Transaction objects into an adapter for the
        //       List Adapter.  This way we can repeatedly generate a new list every time we refresh.

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

        if(isAdmin == false){
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

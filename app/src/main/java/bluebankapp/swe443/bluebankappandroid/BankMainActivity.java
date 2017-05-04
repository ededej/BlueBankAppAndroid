package bluebankapp.swe443.bluebankappandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class BankMainActivity extends AppCompatActivity {
    TextView acct_amount,current_user;
    public final static String adminUser="ulno443Admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //blue = (Bank) getIntent().getParcelableExtra("bank");
        //current_acct = (Account) getIntent().getParcelableExtra("current_acct");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_main);
        // setters
        acct_amount = (TextView) findViewById(R.id.currentBalanceTxt);
        current_user= (TextView) findViewById(R.id.currentUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.bankmain_menu, menu);
        if((current_user.getText().toString().equals(adminUser))||(current_user.getText().toString().equals(""))){
            menu.getItem(1).setVisible(true);
        }else{
            menu.getItem(1).setVisible(false);
        }
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshValues();
    }

    public void refreshValues(){
        current_user.setText(getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", ""));

        // Show balance as $0.00 format
        double currBalance = getSharedPreferences("bluebank", MODE_PRIVATE).getFloat("balance", 0);
        currBalance = Math.round(currBalance * 100);
        currBalance = currBalance / 100;
        String balanceString = String.format("Current Balance: $%.2f", currBalance);
        acct_amount.setText(balanceString);
        invalidateOptionsMenu();

    }

    public void BalanceClickRefresh(View v){
        String u = getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", "");
        String p = getSharedPreferences("bluebank", MODE_PRIVATE).getString("password", "");
        String ip = getSharedPreferences("bluebank", MODE_PRIVATE).getString("ip", "");

        //Send REFRESH request to server.
        StringBuilder req = new StringBuilder();

        // Create the request string
        // op code | username | password | amount
        // 0  #1  #2       #3
        // w/d#jlm#letmein0#50.00
        req.append("r" + ClientLogic.DELIM); // OP CODE
        req.append(u + ClientLogic.DELIM); // USERNAME
        req.append(p); // PASSWORD

        // Send the request string and get the response.
        new ClientLogic.RefreshRequest().execute(this, req.toString(), ip);
    }

    //withdraw drawable.button amount
    public void WithdrawDepositBtnClick(View v){
        Intent depositWithdrawIntent = new Intent(BankMainActivity.this,WithdrawDepositActivity.class);
        startActivity(depositWithdrawIntent);
    }

    public void transferMoneyClick(View v){
        Intent transferIntent = new Intent(BankMainActivity.this,TransferActivity.class);
        startActivity(transferIntent);
    }
    public void manageAccountClick(View v){
        Intent manageIntent = new Intent(BankMainActivity.this,ManageAccountActivity.class);
        startActivity(manageIntent);
    }
    public void TransActionClick(View v){
        Intent supportActivityIntent = new Intent(BankMainActivity.this,TransactionActivity.class);
        startActivity(supportActivityIntent);
    }

    public void bindBalance(){

    }

    public void qrClick(View v){
        showAlertQr(current_user.getText().toString());
    }

    public void logOutClick(View v){
        SharedPreferences.Editor editor = getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
        // To log out, reset all locally stored account information.
        // Leave the username so that the field auto-populates on the login screen.
        // Similarly, leave the IP so that the user doesnt have to re-type it.
        editor.putString("password", "");
        editor.putFloat("balance", 0);
        editor.putString("ssn", "");
        editor.putString("dob", "");
        editor.putString("email", "");
        editor.putString("fullname", "");
        editor.apply();

        // Go back to login.
        finish();
    }

    private Bitmap generateQR(String s) throws WriterException {
        BitMatrix result;
        int width=700;
        int height=700;
        try {
            result = new MultiFormatWriter().encode(s, BarcodeFormat.QR_CODE, width, height, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }

    private void showAlertQr(String transactionId) {
        ImageView imageForQr = new ImageView(this);
        try {
            imageForQr.setImageBitmap(generateQR(transactionId));
        } catch (WriterException e) {
            e.printStackTrace();
        }

        AlertDialog builder =
                new AlertDialog.Builder(this).
                        setNeutralButton("Done", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).
                        setView(imageForQr).create();
        TextView title = new TextView(this);
        title.setText("SCAN QR CODE ABOVE");
        title.setTextColor(BLACK);
        title.setTextSize(20);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        builder.setCustomTitle(title);
        //trying to put the done drawable.button on center
        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {                    //
                Button positiveButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setGravity(Gravity.CENTER_HORIZONTAL);
                positiveButton.setTextSize(50);
            }
        });
        builder.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.menu1){ //logout
            SharedPreferences.Editor editor = getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
            // To log out, reset all locally stored account information.
            // Leave the username so that the field auto-populates on the login screen.
            // Similarly, leave the IP so that the user doesnt have to re-type it.
            editor.putString("password", "");
            editor.putFloat("balance", 0);
            editor.putString("ssn", "");
            editor.putString("dob", "");
            editor.putString("email", "");
            editor.putString("fullname", "");
            editor.apply();
            // Go back to login.
            finish();

            return true;

        } else if(id == R.id.menu2){ //admin
            Intent AdminIntent = new Intent(BankMainActivity.this, AdminConsoleActivity.class);
            startActivity(AdminIntent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

}

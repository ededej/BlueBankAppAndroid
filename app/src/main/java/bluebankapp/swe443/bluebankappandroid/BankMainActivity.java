package bluebankapp.swe443.bluebankappandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BankMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_main);
    }
    public void DrawDepositBtnClick(View v){
        Intent depositWithdrawIntent = new Intent(BankMainActivity.this,WithdrawDepositActivity.class);
        startActivity(depositWithdrawIntent);
    }
    public  void transferMoneyClick(View v){
        Intent transferIntent = new Intent(BankMainActivity.this,TransferActivity.class);
        startActivity(transferIntent);
    }
    public void manageAccountClick(View v){
        Intent manageIntent = new Intent(BankMainActivity.this,ManageAccountActivity.class);
        startActivity(manageIntent);
    }
    public void supportClick(View v){
        Intent supportActivityIntent = new Intent(BankMainActivity.this,SupportActivity.class);
        startActivity(supportActivityIntent);
    }
}

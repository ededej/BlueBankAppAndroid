package bluebankapp.swe443.bluebankappandroid;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DialogTitle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.MultiFormatWriter;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class TransferActivity extends AppCompatActivity {

    EditText editAmount;
    EditText personNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
    }

    public void doTransactionClick(View v){

        editAmount=(EditText) findViewById(R.id.editAmount);
        personNameEdit=(EditText) findViewById(R.id.personNameEdit);
        if(validateInput()){
            Double amountDb= Double.parseDouble(editAmount.getText().toString());
            String u = getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", "");
            String p = getSharedPreferences("bluebank", MODE_PRIVATE).getString("password", "");
            String ip = getSharedPreferences("bluebank", MODE_PRIVATE).getString("ip", "");

            // Send LOGIN request to server.
            StringBuilder req = new StringBuilder();

            // Create the request string
            // op code | username | password | amount
            // 0  #1  #2       #3  #4
            // w/d#jlm#letmein0#abc#50.00
            req.append("t" + ClientLogic.DELIM); // OP CODE
            req.append(u + ClientLogic.DELIM); // USERNAME
            req.append(p + ClientLogic.DELIM); // PASSWORD
            req.append(personNameEdit.getText().toString() + ClientLogic.DELIM); // DESTINATION
            req.append(Double.toString(amountDb)); // AMOUNT

            // Send the request string and get the response.
            new ClientLogic.ServerRequest().execute(this, req.toString(), ip);
        }
        showAlertQr(personNameEdit.getText().toString() );
        //alertdialgo(personNameEdit.getText().toString() );

    }

    private Bitmap generateQR(String s) throws WriterException{
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
        //trying to put the done button on center
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

    public boolean validateInput(){
        if(TextUtils.isEmpty(editAmount.getText().toString())){
            editAmount.setError("Insert Amount");
            return false;
        }
        return true;
    }
}

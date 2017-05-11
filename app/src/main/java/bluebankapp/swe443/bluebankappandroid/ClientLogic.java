package bluebankapp.swe443.bluebankappandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.content.Context.MODE_PRIVATE;

public class ClientLogic {

    static int PORT = 1337;
    static String DELIM = "#";
    static int LOCKOUT_THRESHOLD = 3;
    static long LOCKOUT_DURATION = 10000; //360000;  // 60min * 60s/min * 1000 ms/s = 3,600,000 ms lockout
    static long LOCKOUT_TIMER = 300000;

    // End to end encryption function
    static String encrypt(String plaintext){
        byte key = 'x';
        String ciphertext = "";
        try {
            byte[] cipherbyte = plaintext.getBytes("UTF-8");
            for (int i = 0; i < cipherbyte.length; i++){
                cipherbyte[i] = (byte)(((int)cipherbyte[i]) ^ ((int)key));
            }
            ciphertext = new String(cipherbyte, "UTF-8");
        } catch (Exception e){
            System.out.println("ENC Error: "+e.toString());
        }

        StringBuilder ciph = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++){
            if (ciphertext.charAt(i) == '&'){
                ciph.append("&amp;");
            } else if (ciphertext.charAt(i) == '\n'){
                ciph.append("&new;");
            } else {
                ciph.append(ciphertext.charAt(i));
            }
        }
        return plaintext;
    }

    // End to end encryption function
    static String decrypt(String ciphertext){
        byte key = 'x';
        String plaintext = "";
        StringBuilder escaped = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++){
            if (ciphertext.charAt(i) == '&'){
                if (ciphertext.charAt(i+1) == 'a'){
                    escaped.append('&');
                } else {
                    escaped.append('\n');
                }
                i += 4;
            } else {
                escaped.append(ciphertext.charAt(i));
            }
        }
        ciphertext = escaped.toString();
        try {
            byte[] plainbyte = ciphertext.getBytes("UTF-8");
            for (int i = 0; i < plainbyte.length; i++){
                plainbyte[i] = (byte)(((int)plainbyte[i]) ^ ((int)key));
            }
            plaintext = new String(plainbyte, "UTF-8");
        } catch (Exception e){
            System.out.println("DEC Error: "+e.toString());
        }
        return ciphertext;
    }

    // Method to toast if network errors occur.
    static boolean errorToast(Context c, Exception e, String SERVER) {
        if (e != null) {
            if (e instanceof UnknownHostException) {
                Toast.makeText(c, "Could not establish connection to host: " + SERVER + ", " + PORT, Toast.LENGTH_LONG).show();
            } else if (e instanceof IOException) {
                Toast.makeText(c, "IO Error: The hamsters are on strike." + e.toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(c, "Something went wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return false;
    }

    // GENERALIZED SERVER REQUEST
    static class GenericRequest extends AsyncTask<Object, Void, String> {
        Context c;
        Exception ex;
        String SERVER;

        protected String doInBackground(Object... req) {
            // Server response string and socket vars.
            String res = "";
            Socket clientSocket;
            PrintStream os;
            BufferedReader is;
            c = (Context) req[0];
            SERVER = (String) req[2];

            try { // Init socket variables.
                clientSocket = new Socket(SERVER, PORT);
                os = new PrintStream(clientSocket.getOutputStream());
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Send the request.
                os.println(encrypt((String) req[1]));

                // Read the server's response.
                res = is.readLine();

            } catch (Exception e){
                ex = e;
            }

            return res;
        }

        // Callback that happens in the UI thread once the async op/server call is done.
        protected void onPostExecute(String res) {
            // Check for network exceptions.
            if (errorToast(c, ex, SERVER)) { return; }

            Toast.makeText(c, "Request Completed Successfully." , Toast.LENGTH_SHORT).show();

            ((Activity) c).finish();
        }
    }

    static class CreateAccountRequest extends AsyncTask<Object, Void, String> {

        Context c;
        Exception ex;
        String SERVER;

        protected String doInBackground(Object... req) {
            // Server response string and socket vars.
            String res = "";
            Socket clientSocket;
            PrintStream os;
            BufferedReader is;
            c = (Context) req[0];
            SERVER = (String) req[2];

            try { // Init socket variables.
                clientSocket = new Socket(SERVER, PORT);
                os = new PrintStream(clientSocket.getOutputStream());
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Send the request.
                os.println(encrypt((String) req[1]));

                // Read the server's response.
                res = is.readLine();

            } catch (Exception e){
                ex = e;
            }

            return res;
        }

        // Callback that happens in the UI thread once the async op/server call is done.
        protected void onPostExecute(String res) {
            // Check for network exceptions.
            if (errorToast(c, ex, SERVER)) { return; }

            // Parse the state.
            String[] fields = decrypt(res).split("#");

            // Check for errors and Toast accordingly.  Return.
            if (fields[0].equals("Error")){
                if(fields[1].toLowerCase().contains("already exists")) {
                    EditText username = (EditText) ((Activity) c).findViewById(R.id.usernameEdit);
                    username.setError("Username already in use.");
                    return;
                }
                Toast.makeText(c, "Errors on account creation: "+fields[1], Toast.LENGTH_SHORT).show();
                return;
            }

            // Otherwise, unpack the server response and save to SharedPrefs.
            SharedPreferences.Editor editor = c.getSharedPreferences("bluebank", MODE_PRIVATE).edit();
            // Response syntax:
            // USERNAME | PASSWORD | BALANCE | SSN | DOB | EMAIL | FULL NAME
            // 0        | 1        | 2       | 3   | 4   | 5     | 6
            editor.putString("username", fields[0]);
            //editor.putString("password", fields[1]);
            editor.putFloat("balance", (float) Double.parseDouble(fields[2]));
            editor.putString("ssn", fields[3]);
            editor.putString("dob", fields[4]);
            editor.putString("email", fields[5]);
            editor.putString("fullname", fields[6]);
            editor.apply();

            Toast.makeText(c, "Account created successfully.", Toast.LENGTH_LONG).show();
            ((Activity) c).finish();
        }
    }

    static class LoginRequest extends AsyncTask<Object, Void, String> {

        Context c;
        Exception ex;
        String SERVER;

        protected String doInBackground(Object... req) {
            // Server response string and socket vars.
            String res = "";
            Socket clientSocket;
            PrintStream os;
            BufferedReader is;
            c = (Context) req[0];
            SERVER = (String) req[2];

            try { // Init socket variables.
                clientSocket = new Socket(SERVER, PORT);
                os = new PrintStream(clientSocket.getOutputStream());
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Send the request.
                os.println(encrypt((String) req[1]));

                // Read the server's response.
                res = is.readLine();

            } catch (Exception e){
                ex = e;
            }

            return res;
        }

        // Callback that happens in the UI thread once the async op/server call is done.
        protected void onPostExecute(String res) {
            // Check for network exceptions.
            if (errorToast(c, ex, SERVER)) { return; }

            // Parse the state.
            String[] fields = decrypt(res).split("#");

            // Check for errors and Toast accordingly.  Return.
            if (fields[0].equals("Error")){

                // Check for error other than invalid login attempt
                if (!fields[1].contains("Invalid username or password")){
                    Toast.makeText(c, "Error: "+fields[1], Toast.LENGTH_SHORT).show();
                    return;
                }

                // This is an invalid login attempt. Pull invalid attempt data and get time delta.
                int numInvalid = 0;
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/HH/mm/ss");
                try {
                    numInvalid = c.getSharedPreferences("bluebank", MODE_PRIVATE).getInt("numInvalid", 0) + 1;
                } catch (Exception e){ System.out.println("Something bad happened here."); }

                // Save values and notify user of remaining tries.
                SharedPreferences.Editor editor = c.getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
                editor.putInt("numInvalid", numInvalid);
                editor.putString("lastInvalid", sdf.format(new Date()));
                editor.apply();

                Toast.makeText(c,
                        "Invalid login attempt: "+numInvalid+" of "+ClientLogic.LOCKOUT_THRESHOLD+".",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // Otherwise, unpack the server response and save to SharedPreferences.
            SharedPreferences.Editor editor = c.getSharedPreferences("bluebank", MODE_PRIVATE).edit();
            // Response syntax:
            // USERNAME | PASSWORD | BALANCE | SSN | DOB | EMAIL | FULL NAME
            // 0        | 1        | 2       | 3   | 4   | 5     | 6
            editor.putString("username", fields[0]);
            //editor.putString("password", fields[1]);
            editor.putFloat("balance", (float) Double.parseDouble(fields[2]));
            editor.putString("ssn", fields[3]);
            editor.putString("dob", fields[4]);
            editor.putString("email", fields[5]);
            editor.putString("fullname", fields[6]);
            editor.putInt("numInvalid", 0); // Kill all stored failed attempts
            editor.apply();

            EditText Password = (EditText) ((Activity) c).findViewById(R.id.passwordInput);
            Password.setText("");

            // Go to BankMainActivity now that they're logged in.
            Intent i = new Intent(c, BankMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(i);
        }
    }

    static class ServerRequest extends AsyncTask<Object, Void, String> {

        Context c;
        Exception ex;
        String SERVER;

        protected String doInBackground(Object... req) {
            // Server response string and socket vars.
            String res = "";
            Socket clientSocket;
            PrintStream os;
            BufferedReader is;
            c = (Context) req[0];
            SERVER = (String) req[2];

            try { // Init socket variables.
                clientSocket = new Socket(SERVER, PORT);
                os = new PrintStream(clientSocket.getOutputStream());
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Send the request.
                os.println(encrypt((String) req[1]));

                // Read the server's response.
                res = is.readLine();

            } catch (Exception e){
                ex = e;
            }

            return res;
        }

        // Callback that happens in the UI thread once the async op/server call is done.
        protected void onPostExecute(String res) {
            // Check for network exceptions.
            if (errorToast(c, ex, SERVER)) { return; }

            // Parse the state.
            String[] fields = decrypt(res).split("#");

            // Check for errors and Toast accordingly.  Return.
            if (fields[0].equals("Error")){
                Toast.makeText(c, "Error: "+fields[1], Toast.LENGTH_SHORT).show();
                return;
            }

            // Otherwise, unpack the server response and save to SharedPreferences.
            SharedPreferences.Editor editor = c.getSharedPreferences("bluebank", MODE_PRIVATE).edit();
            // Response syntax:
            // USERNAME | PASSWORD | BALANCE | SSN | DOB | EMAIL | FULL NAME
            // 0        | 1        | 2       | 3   | 4   | 5     | 6
            editor.putString("username", fields[0]);
            //editor.putString("password", fields[1]);
            editor.putFloat("balance", (float) Double.parseDouble(fields[2]));
            editor.putString("ssn", fields[3]);
            editor.putString("dob", fields[4]);
            editor.putString("email", fields[5]);
            editor.putString("fullname", fields[6]);
            editor.apply();

            // Go back once.
            ((Activity) c).finish();
        }
    }

    static class RefreshRequest extends AsyncTask<Object, Void, String> {

        Context c;
        Exception ex;
        String SERVER;

        protected String doInBackground(Object... req) {
            // Server response string and socket vars.
            String res = "";
            Socket clientSocket;
            PrintStream os;
            BufferedReader is;
            c = (Context) req[0];
            SERVER = (String) req[2];

            try { // Init socket variables.
                clientSocket = new Socket(SERVER, PORT);
                os = new PrintStream(clientSocket.getOutputStream());
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Send the request.
                os.println(encrypt((String) req[1]));

                // Read the server's response.
                res = is.readLine();

            } catch (Exception e){
                ex = e;
            }

            return res;
        }

        // Callback that happens in the UI thread once the async op/server call is done.
        protected void onPostExecute(String res) {
            // Check for network exceptions.
            if (errorToast(c, ex, SERVER)) { return; }

            // Parse the state.
            String[] fields = decrypt(res).split("#");

            // Check for errors and Toast accordingly.  Return.
            if (fields[0].equals("Error")){
                Toast.makeText(c, "Error: "+fields[1], Toast.LENGTH_SHORT).show();
                return;
            }

            // Otherwise, unpack the server response and save to SharedPreferences.
            SharedPreferences.Editor editor = c.getSharedPreferences("bluebank", MODE_PRIVATE).edit();
            // Response syntax:
            // USERNAME | PASSWORD | BALANCE | SSN | DOB | EMAIL | FULL NAME
            // 0        | 1        | 2       | 3   | 4   | 5     | 6
            editor.putString("username", fields[0]);
            //editor.putString("password", fields[1]);
            editor.putFloat("balance", (float) Double.parseDouble(fields[2]));
            editor.putString("ssn", fields[3]);
            editor.putString("dob", fields[4]);
            editor.putString("email", fields[5]);
            editor.putString("fullname", fields[6]);
            editor.apply();

            // Trigger refresh
            ((BankMainActivity) c).refreshValues();
        }
    }

    static class TransactionRefreshRequest extends AsyncTask<Object, Void, String> {

        Context c;
        Exception ex;
        String SERVER;

        protected String doInBackground(Object... req) {
            // Server response string and socket vars.
            String res = "";
            Socket clientSocket;
            PrintStream os;
            BufferedReader is;
            c = (Context) req[0];
            SERVER = (String) req[2];

            try { // Init socket variables.
                clientSocket = new Socket(SERVER, PORT);
                os = new PrintStream(clientSocket.getOutputStream());
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Send the request.
                os.println(encrypt((String) req[1]));

                // Read the server's response.
                res = is.readLine();

            } catch (Exception e){
                ex = e;
            }

            return res;
        }

        // Callback that happens in the UI thread once the async op/server call is done.
        protected void onPostExecute(String res) {
            // Check for network exceptions.
            if (errorToast(c, ex, SERVER)) { return; }

            // Parse the state.
            String[] fields = decrypt(res).split("#");

            // Check for errors and Toast accordingly.  Return.
            if (fields[0].equals("Error")) {
                Toast.makeText(c, "Error: "+fields[1], Toast.LENGTH_SHORT).show();
                return;
            }

            // Otherwise, unpack the transactions and save to SharedPreferences.
            SharedPreferences.Editor editor = c.getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
            editor.putFloat("balance", (float) Double.parseDouble(fields[0]));
            editor.putString("transactions", res.substring(fields[0].length()+1));
            editor.apply();

            // Trigger refresh
            ((TransactionActivity) c).refreshPage();
        }
    }

    static class DisputeRequest extends AsyncTask<Object, Void, String> {
        Context c;
        Exception ex;
        String SERVER;

        protected String doInBackground(Object... req) {
            // Server response string and socket vars.
            String res = "";
            Socket clientSocket;
            PrintStream os;
            BufferedReader is;
            c = (Context) req[0];
            SERVER = (String) req[2];

            try { // Init socket variables.
                clientSocket = new Socket(SERVER, PORT);
                os = new PrintStream(clientSocket.getOutputStream());
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Send the request.
                os.println(encrypt((String) req[1]));

                // Read the server's response.
                res = is.readLine();

            } catch (Exception e){
                ex = e;
            }

            return res;
        }

        // Callback that happens in the UI thread once the async op/server call is done.
        protected void onPostExecute(String res) {
            // Check for network exceptions.
            if (errorToast(c, ex, SERVER)) { return; }
            Toast.makeText(c, "Dispute action successful." , Toast.LENGTH_SHORT).show();
        }
    }

    static class DisputeRefreshRequest extends AsyncTask<Object, Void, String> {

        Context c;
        Exception ex;
        String SERVER;

        protected String doInBackground(Object... req) {
            // Server response string and socket vars.
            String res = "";
            Socket clientSocket;
            PrintStream os;
            BufferedReader is;
            c = (Context) req[0];
            SERVER = (String) req[2];

            try { // Init socket variables.
                clientSocket = new Socket(SERVER, PORT);
                os = new PrintStream(clientSocket.getOutputStream());
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Send the request.
                os.println(encrypt((String) req[1]));

                // Read the server's response.
                res = is.readLine();

            } catch (Exception e){
                ex = e;
            }

            return res;
        }

        // Callback that happens in the UI thread once the async op/server call is done.
        protected void onPostExecute(String res) {
            // Check for network exceptions.
            if (errorToast(c, ex, SERVER)) { return; }

            // Parse the state.
            String[] fields = decrypt(res).split("#");

            // Check for errors and Toast accordingly.  Return.
            if (fields[0].equals("Error")) {
                Toast.makeText(c, "Error: "+fields[1], Toast.LENGTH_SHORT).show();
                return;
            }

            // Otherwise, unpack the transactions and save to SharedPreferences.
            SharedPreferences.Editor editor = c.getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
            editor.putFloat("balance", (float) Double.parseDouble(fields[0]));
            editor.putString("transactions", res.substring(fields[0].length()+1));
            editor.apply();

            // Trigger refresh
            ((DisputeActivity) c).refreshPage();
        }
    }
}

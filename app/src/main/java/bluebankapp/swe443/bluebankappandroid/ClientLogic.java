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

public class ClientLogic {

    public static int PORT = 1337;
    public static String DELIM = "#";

    // Method to toast if network errors occur.
    public static boolean errorToast(Context c, Exception e, String SERVER) {
        if (e != null) {
            if (e instanceof UnknownHostException) {
                Toast.makeText(c, "Could not establish connection to host: " + SERVER + ", " + PORT, Toast.LENGTH_LONG).show();
            } else if (e instanceof UnknownHostException) {
                Toast.makeText(c, "IO Error: The hamsters are on strike." + e.toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(c, "Something went wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return false;
    }

    public static class CreateAccountRequest extends AsyncTask<Object, Void, String> {

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
                os.println((String) req[1]);

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
            String[] fields = res.split("#");

            // Check for errors and Toast accordingly.  Return.
            if (fields[0].equals("Error")){
                Toast.makeText(c, "Errors on account creation: "+fields[1], Toast.LENGTH_SHORT).show();
                return;
            }

            // Otherwise, unpack the server response and save to SharedPrefs.
            SharedPreferences.Editor editor = c.getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
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

    public static class LoginRequest extends AsyncTask<Object, Void, String> {

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
                os.println((String) req[1]);

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
            String[] fields = res.split("#");

            // Check for errors and Toast accordingly.  Return.
            if (fields[0].equals("Error")){
                Toast.makeText(c, "Error: "+fields[1], Toast.LENGTH_SHORT).show();
                return;
            }

            // Otherwise, unpack the server response and save to SharedPreferences.
            SharedPreferences.Editor editor = c.getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
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

            EditText Password = (EditText) ((Activity) c).findViewById(R.id.passwordInput);
            Password.setText("");

            // Go to BankMainActivity now that they're logged in.
            Intent i = new Intent(c, BankMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(i);
        }
    }

    public static class ServerRequest extends AsyncTask<Object, Void, String> {

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
                os.println((String) req[1]);

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
            String[] fields = res.split("#");

            // Check for errors and Toast accordingly.  Return.
            if (fields[0].equals("Error")){
                Toast.makeText(c, "Error: "+fields[1], Toast.LENGTH_SHORT).show();
                return;
            }

            // Otherwise, unpack the server response and save to SharedPreferences.
            SharedPreferences.Editor editor = c.getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
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

    public static class RefreshRequest extends AsyncTask<Object, Void, String> {

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
                os.println((String) req[1]);

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
            String[] fields = res.split("#");

            // Check for errors and Toast accordingly.  Return.
            if (fields[0].equals("Error")){
                Toast.makeText(c, "Error: "+fields[1], Toast.LENGTH_SHORT).show();
                return;
            }

            // Otherwise, unpack the server response and save to SharedPreferences.
            SharedPreferences.Editor editor = c.getSharedPreferences("bluebank", Context.MODE_PRIVATE).edit();
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
}

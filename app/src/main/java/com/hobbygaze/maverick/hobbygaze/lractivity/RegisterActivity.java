/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package com.hobbygaze.maverick.hobbygaze.lractivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hobbygaze.maverick.hobbygaze.*;
import com.hobbygaze.maverick.hobbygaze.helper.SQLiteHandler;
import com.hobbygaze.maverick.hobbygaze.helper.SessionManager;
import com.hobbygaze.maverick.hobbygaze.oldfiles.MainActivity;
import com.hobbygaze.maverick.hobbygaze.oldfiles.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import xdroid.toaster.Toaster;

public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    public String name=null;
    public String email=null;
    public String password=null;
String url=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        inputFullName.setError(null);
        inputEmail.setError(null);
        inputPassword.setError(null);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    BaseActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                 name = inputFullName.getText().toString().trim();
                 email = inputEmail.getText().toString().trim();
                 password = inputPassword.getText().toString().trim();

                if(!(email.contains("@"))) {
                    //Toaster.toastLong("Email id invalid");
                    inputEmail.setError(getString(R.string.error_invalid_email));

                }



                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                 //   url="http://www.hobbygaze.com/api/user/register/?username="+email+"&email="+email+"&nonce=2b8de96566"+"&user_pass="+password+"&display_name="+name+"&notify=no";
                   // url="http://www.hobbygaze.com/api/user/register/?username=anand.abhishek@live.com&email=anand.abhishek@live.com&nonce=2b8de96566&display_name=Abhishek Anand&user_pass=sumit123&notify=both";
                    //System.out.println("URL= "+url);
                    new RegisterResults().execute();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }




/**
 * Async task class to get json by making HTTP call
 * */
public class RegisterResults extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();
        String company_email=null;
        String jsonStr = null;
        String nonce=null;
        String key=null;
        try {
            nonce=sh.run("http://www.hobbygaze.com/api/get_nonce/?controller=user&method=register");
            if (nonce != null) {
                try {
                    JSONObject j = new JSONObject(nonce);
                    String status=j.getString("status");

                    if (status.contentEquals("ok")) {
                        key=j.getString("nonce");
                        System.out.println("key="+key);

                    } else {
                        // Error in login. Get the error message
                        String error=j.getString("error");
                        System.out.println("error=" + error);
                        //showerror(LoginActivity.this, error, new ErrormsgHandler());
                        //errormsg.setVisibility(View.VISIBLE);

                        //errormsg.setText(error);
                        Toaster.toastLong(error);
                        //  Toast.makeText(getApplicationContext(),
                        //       error, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toaster.toastLong("Unable to register...Please retry");
                }

            }
            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            url="http://www.hobbygaze.com/api/user/register/?username="+email+"&email="+email+"&nonce="+key+"&user_pass="+password+"&display_name="+name+"&notify=both";
            System.out.println("URL=" + url);
            jsonStr = sh.run(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (jsonStr != null) {
            try {
                JSONObject jObj = new JSONObject(jsonStr);
                //boolean error = jObj.getBoolean("error");
                String status=jObj.getString("status");
                // Check for error node in json
                if (status.contentEquals("ok")) {

                    String cookie=jObj.getString("cookie");
                    String id = jObj.getString("user_id");
                    System.out.println(cookie);
                    System.out.println(id);
                    //int i = Integer.parseInt(id);
                    Toaster.toastLong("User successfully registered");
                    session.setLogin(true);
                    //db.addUser(name,email,id);
                    db.addUser(name, email,id);
                    Intent intent = new Intent(RegisterActivity.this,
                            BaseActivity.class);
                    //intent.putExtra("cookie", cookie);
                    //intent.putExtra("username", email);
                    //intent.putExtra("displayname", name);
                    startActivity(intent);
                    finish();
                } else {
                    // Error in login. Get the error message
                    String error=jObj.getString("error");
                    System.out.println("error=" + error);
                    //showerror(LoginActivity.this, error, new ErrormsgHandler());
                    //errormsg.setVisibility(View.VISIBLE);

                    //errormsg.setText(error);
                   // Toaster.toastLong(error);
                    Toaster.toastLong("Unable to register...Please retry");
                    //  Toast.makeText(getApplicationContext(),
                    //       error, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
                Toaster.toastLong(e.getMessage());
               // Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
        //System.out.println(response);

        //Log.d("Response: ", "> " + response);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}







}
/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package com.hobbygaze.maverick.hobbygaze.lractivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hobbygaze.maverick.hobbygaze.BaseActivity;
import com.hobbygaze.maverick.hobbygaze.oldfiles.MainActivity;
import com.hobbygaze.maverick.hobbygaze.oldfiles.ServiceHandler;
import com.hobbygaze.maverick.hobbygaze.R;
import com.hobbygaze.maverick.hobbygaze.helper.SQLiteHandler;
import com.hobbygaze.maverick.hobbygaze.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import xdroid.toaster.Toaster;

public class LoginActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private Button btnLinkToForgotPassword;
    private EditText inputEmail;
    private EditText inputUsername;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    public String url=null;
    public TextView errormsg;
    String result;

    //Facebook
    LoginButton loginButton;
    CallbackManager mCallbackManager;

    private ProgressDialog mProgressDialog;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]
    public class ErrormsgHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    result = bundle.getString("error");
                    break;
                default:
                    result = null;
            }
            // replace by what you need to do
            //System.out.println(result);
            errormsg.setText(result);

        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);



        if(isNetworkStatusAvialable (getApplicationContext())) {

            //ourBrow.clearCache(true);
            //Toast.makeText(getApplicationContext(), "internet available", Toast.LENGTH_SHORT).show();
        } else {
            /*Dialog d1 = new Dialog(this);

            d1.setTitle("Failed to connect");
            TextView tv = new TextView(this);
            d1.setContentView(tv);
            d1.show();
            */

            AlertDialog alertDialog = new AlertDialog.Builder(
                    LoginActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("No internet ");

            // Setting Dialog Message
            alertDialog.setMessage("Please check your internet connectivity");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.warning);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                }
            });

            // Showing Alert Message
            alertDialog.show();
            // Toast.makeText(getApplicationContext(), "internet is not available", Toast.LENGTH_SHORT).show();

        }

        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions("email");

        inputUsername=(EditText) findViewById(R.id.username);
        //inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        btnLinkToForgotPassword = (Button) findViewById(R.id.btnLinkToForgotPasswordScreen);
       errormsg=(TextView)findViewById(R.id.error);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        inputUsername.setError(null);
        inputPassword.setError(null);

        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };

        mCallbackManager = CallbackManager.Factory.create();
        //LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });


        // [START on_start_add_listener]



        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this,BaseActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username=inputUsername.getText().toString().trim();
                //String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if(!(username.contains("@"))) {
                    //Toaster.toastLong("Email id invalid");
                    inputUsername.setError(getString(R.string.error_invalid_email));

                }
                // Check for empty data in the form
                if (!username.isEmpty() && !password.isEmpty()) {
                    url="https://www.hobbygaze.com/api/user/generate_auth_cookie/?username="+username+"&password="+password;
                    System.out.println("URL= "+url);
                    new GetResults().execute();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });


        // Link to Register Screen
        btnLinkToForgotPassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        ForgotPassword.class);
                startActivity(i);
                finish();
            }
        });

    }











    /**
     * Async task class to get json by making HTTP call
     * */
    public class GetResults extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginActivity.this);
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
            try {
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
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite

                        String cookie=jObj.getString("cookie");
                        JSONObject user = jObj.getJSONObject("user");

                        String id = user.getString("id");
                        String username = user.getString("username");
                        String displayname = user.getString("displayname");


                        System.out.println(cookie);
                        System.out.println(id);
                        System.out.println(username);
                        System.out.println(displayname);
                        session.userDetails(cookie, username, displayname);
                        db.addUser(displayname, username,id);
                        // Inserting row in users table
                        // db.addUser(cookie, id, username, displayname);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                BaseActivity.class);
                        intent.putExtra("cookie", cookie);
                        intent.putExtra("username", username);
                        intent.putExtra("displayname", displayname);


                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String error=jObj.getString("error");
                        System.out.println("error=" + error);
                        //showerror(LoginActivity.this, error, new ErrormsgHandler());
                        //errormsg.setVisibility(View.VISIBLE);

                       //errormsg.setText(error);
                        //Toaster.toastLong(error);
                        Toaster.toastLong("Unable to Login...Please retry");
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



   public void  showerror(final Context context, final String error, final Handler handler) {

       Thread thread = new Thread() {
           @Override
           public void run() {
               String result = new String(error);
               Message msg = Message.obtain();
               msg.setTarget(handler);
               if (result != null) {
                   msg.what = 1;
                   Bundle bundle = new Bundle();
                   bundle.putString("address", result);
                   msg.setData(bundle);
               } else
                   msg.what = 0;
               msg.sendToTarget();

           }
       };
       thread.start();
   }

    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
           mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });

    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            System.out.println("FB user name"+user.getDisplayName());
            System.out.println("FB uid"+user.getUid());
            //System.out.println("FB uid"+user.getEmail());
        } else {

        }
    }
}

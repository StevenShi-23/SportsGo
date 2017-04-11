package com.example.sportsgo.sportsgo.Activities;

/**
 * Created by gongzhen on 8/4/17.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportsgo.sportsgo.MyApp;
import com.example.sportsgo.sportsgo.R;
import com.example.sportsgo.sportsgo.model.FacilityList;
import com.example.sportsgo.sportsgo.model.User;
import com.example.sportsgo.sportsgo.utilities.Favorites_get;
import com.example.sportsgo.sportsgo.utilities.NetworkUtils;
import com.example.sportsgo.sportsgo.utilities.RefreshService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.Bind;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private AsyncHttpClient client = new AsyncHttpClient();
    private ProgressDialog progressDialog;
    private android.os.Handler mHandler;
    @Bind(R.id.input_username) EditText _usernameText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.sportsgo.sportsgo.R.layout.activity_login);
        ButterKnife.bind(this);
        mHandler = new Handler();
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
        progressDialog = new ProgressDialog(LoginActivity.this,
                com.example.sportsgo.sportsgo.R.style.AppTheme_Dialog);
        startService(new Intent(MyApp.getContext(), RefreshService.class));
    }

    public void login() {
        Log.d(TAG, "Login here");

        if (!validate()) {
            onLoginFailed("");
            return;
        }

        _loginButton.setEnabled(false);

        final String username = _usernameText.getText().toString();
        final String password = _passwordText.getText().toString();
        // TODO: Implement our own authentication logic here.
        String login_url = NetworkUtils.buildLoginUrl(username, password);
        new LoginTask().execute(login_url);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(int id) {
        _loginButton.setEnabled(true);
        User.getInstance().setID(id);

        new Favorites_get(User.getInstance().get_id()).execute();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        //finish();
    }

    public void onLoginFailed(String msg) {
        Toast.makeText(getBaseContext(), "Login failed " + msg, Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 2 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String searchUrl = params[0];
                String LoginhResults = null;
                try {
                    LoginhResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                    Log.d("In backgroud","LoginActivity");
                    return LoginhResults;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch(Exception e){
                Log.d("Exception","LoginTask");
            }
            return "";
        }

        @Override
        protected void onPostExecute(String LoginResults) {

                final String loginResults = LoginResults;
                mHandler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        progressDialog.hide();
                        try {
                            JSONObject result = new JSONObject(loginResults);
                            Boolean status = result.getBoolean("Login");
                            if (status) {
                                int id = result.getInt("msg");
                                onLoginSuccess(id);
                            } else {
                                String msg = result.getString("msg");
                                onLoginFailed(msg);
                            }
                        }
                        catch(Exception e){
                            Log.d("Exception","LoginTask");
                        }
                    }
                }, 1500);


        }
    }
}

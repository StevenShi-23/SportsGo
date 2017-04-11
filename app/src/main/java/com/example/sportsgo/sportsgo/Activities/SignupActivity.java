package com.example.sportsgo.sportsgo.Activities;

/**
 * Created by gongzhen on 8/4/17.
 */

import android.app.ProgressDialog;
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
import com.example.sportsgo.sportsgo.R;
import com.example.sportsgo.sportsgo.utilities.NetworkUtils;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private android.os.Handler mHandler;
    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mHandler = new Handler();
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d("Signup", "Here");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

       final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Welcome to SportsGO");
        progressDialog.show();

        String name = _nameText.getText().toString();

        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        //String login_url = NetworkUtils.buildSignupUrl(username, password);
        new SignupTask().execute();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                 //       progressDialog.dismiss();
                    }
                }, 3000);

    }


    public void onSignupSuccess() {
        Log.d("SignupSuccess","Here");
        finish();
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public class SignupTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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

                    try {
                        JSONObject result = new JSONObject(loginResults);
                        Boolean status = result.getBoolean("Login");
                        if (status) {
                            int id = result.getInt("msg");
                            //onLoginSuccess(id);
                        } else {
                            String msg = result.getString("msg");
                            //onLoginFailed(msg);
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

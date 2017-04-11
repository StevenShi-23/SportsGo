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
    private ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dialog);

    }

    public void signup() {
        Log.d("Signup", "Here");

        if (!validate()) {
            onSignupFailed("");
            return;
        }

        _signupButton.setEnabled(false);

        String name = _nameText.getText().toString();

        String password = _passwordText.getText().toString();

        new SignupTask(name, password).execute();
        /*
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
        */
    }


    public void onSignupSuccess() {
        Log.d("SignupSuccess","Here");
        finish();
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed(String msg) {
        Toast.makeText(getBaseContext(), "SignUp failed " + msg, Toast.LENGTH_LONG).show();

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

    private class SignupTask extends AsyncTask<String, Void, String> {
        private String user_name, password;
        public SignupTask(String user_name, String password){
            this.user_name = user_name;
            this.password = password;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Welcome to SportsGO");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String SignUpResults = null;
                SignUpResults = NetworkUtils.signUp(user_name, password);
                Log.d("In backgroud","SignUpActivity");
                return SignUpResults;
            }
            catch(IOException e){
                Log.d("Exception","LoginTask");
            }
            return "";
        }

        @Override
        protected void onPostExecute(String SignUpResults) {

            final String loginResults = SignUpResults;
            Log.d("SignUpActivity", loginResults);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.hide();
                    try {
                        JSONObject result = new JSONObject(loginResults);
                        Boolean status = result.getBoolean("Signup");
                        if (status) {
                            onSignupSuccess();
                        } else {
                            String msg = result.getString("msg");
                            onSignupFailed(msg);
                        }
                    }
                    catch(Exception e){
                        Log.d("Exception","LoginTask");
                    }
                }
            },1000);
        }
    }
}

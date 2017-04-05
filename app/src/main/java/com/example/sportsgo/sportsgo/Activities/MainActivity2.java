/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.sportsgo.sportsgo.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sportsgo.sportsgo.R;
import com.example.sportsgo.sportsgo.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity2 extends AppCompatActivity {

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;

    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Start","Here");
        setContentView(R.layout.activitiy_search);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_online_search_results_json);

        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }

    /**
     * This method retrieves the search text from the EditText, constructs the
     * URL (using {@link NetworkUtils}) for the Online repository you'd like to find, displays
     * that URL in a TextView, and finally fires off an AsyncTask to perform the GET request using
     * our {@link OnlineQueryTask}
     */
    private void makeOnlineSearchQuery() {
        String OnlineQuery = mSearchBoxEditText.getText().toString();
        URL OnlineSearchUrl = NetworkUtils.buildUrl(OnlineQuery);
        mUrlDisplayTextView.setText(OnlineSearchUrl.toString());
        new OnlineQueryTask().execute(OnlineSearchUrl);
    }

    private void showJsonDataView(){
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
    }

    public class OnlineQueryTask extends AsyncTask<URL, Void, String> {


        @Override
        protected void onPreExecute() {
            MainActivity2.this.mLoadingIndicator.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String OnlineSearchResults = null;
            try {
                OnlineSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return OnlineSearchResults;
        }

        @Override
        protected void onPostExecute(String OnlineSearchResults) {
            MainActivity2.this.mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (OnlineSearchResults != null && !OnlineSearchResults.equals("")) {
                mSearchResultsTextView.setText(OnlineSearchResults);
                MainActivity2.this.showJsonDataView();
            }
            else{
                MainActivity2.this.showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeOnlineSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

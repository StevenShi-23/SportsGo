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
// Created by Long 18/3, inorder to to searchView
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
import android.webkit.WebView;

import com.example.sportsgo.sportsgo.R;
import com.example.sportsgo.sportsgo.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity1 extends AppCompatActivity {

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;

    private mSearchView srView;

    private WebView myBrowser;
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Start","Here");
        setContentView(R.layout.activitiy_search);
        myBrowser = (WebView)findViewById(R.id.mybrowser);

        //final MyJavaScriptInterface myJavaScriptInterface
        //        = new MyJavaScriptInterface(this);
        //myBrowser.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");

        //myBrowser.getSettings().setJavaScriptEnabled(true);
        myBrowser.loadUrl("https://data.gov.sg/dataset/realtime-weather-readings/resource/17494bed-23e9-4b3b-ae89-232f87987163/view/81d91c06-158d-4f01-abd9-12108d954847");
        srView = new mSearchView();
        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_online_search_results_json);

        // TODO (13) Get a reference to the error TextView using findViewById
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);
        // TODO (25) Get a reference to the ProgressBar using findViewById
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }

    /**
     * This method retrieves the search text from the EditText, constructs the
     * URL (using {@link NetworkUtils}) for the Online repository you'd like to find, displays
     * that URL in a TextView, and finally fires off an AsyncTask to perform the GET request using
     * our {@link OnlineQueryTask}
     */
    private void makeOnlineSearchQuery() {
        String Query = mSearchBoxEditText.getText().toString();

        //URL OnlineSearchUrl = NetworkUtils.buildUrl(OnlineQuery);
        mUrlDisplayTextView.setText(srView.filterResult(Query));
        //new OnlineQueryTask().execute(OnlineSearchUrl);
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
            MainActivity1.this.mLoadingIndicator.setVisibility(View.VISIBLE);
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
            // TODO (27) As soon as the loading is complete, hide the loading indicator
            MainActivity1.this.mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (OnlineSearchResults != null && !OnlineSearchResults.equals("")) {
                // TODO (17) Call showJsonDataView if we have valid, non-null results
                mSearchResultsTextView.setText(OnlineSearchResults);
                MainActivity1.this.showJsonDataView();
            }
            else{
                MainActivity1.this.showErrorMessage();
            }
            // TODO (16) Call showErrorMessage if the result is null in onPostExecute
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
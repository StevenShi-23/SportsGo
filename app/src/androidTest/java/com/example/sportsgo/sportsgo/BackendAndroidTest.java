package com.example.sportsgo.sportsgo;

import android.app.Application;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;
import android.util.Log;

import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.model.FacilityList;
import com.example.sportsgo.sportsgo.model.User;
import com.example.sportsgo.sportsgo.utilities.NetworkUtils;
import com.example.sportsgo.sportsgo.utilities.UserLocation;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * Created by apple on 6/4/17.
 */

@RunWith(AndroidJUnit4.class)
public class BackendAndroidTest {
    /*
    public BackendAndroidTest() {
        super(Application.class);
    }
    */
    private static String BACKEND_BASE_URL = "http://sports-go.cloudapp.net";
    @Test
    public void main() throws Exception{
        Uri builtUri = Uri.parse(BACKEND_BASE_URL);
        String facility_json =  NetworkUtils.getAllFacilities();
        //System.out.println(builtUri);
        Log.d("Backend TEST:", " starts");
        Log.d("Backend TEST:", facility_json);
        JSONArray facilities_array = new JSONArray(facility_json);
        ArrayList<Facility> Facility_list = new ArrayList<Facility>();
        for (int i = 0; i < facilities_array.length(); i++) {
            JSONObject jsonobject = facilities_array.getJSONObject(i);
            String description = jsonobject.getString("description");
            String weather_status = jsonobject.getString("weather_status");
            double longitude = jsonobject.getDouble("longitude");
            double latitude = jsonobject.getDouble("latitude");
            double temperature = jsonobject.getDouble("temperature");
            int id = jsonobject.getInt("id");
            String name = jsonobject.getString("name");
            int psi = jsonobject.getInt("psi");
            Facility_list.add(new Facility(id, name, longitude, latitude, description, temperature, weather_status,psi));
        }
        FacilityList.getInstance().set_all_facilities(Facility_list);
        Log.d("Backend TEST:", " ends");
    }
    /*
    @Test
    public void network() throws Exception{
        NetworkUtils.GetLogin("haha","xixi",new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // Initiated the request
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // Successfully got a response
                //onLoginSuccess();
                Log.d("Success","Test");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // Response failed :(
                Log.d("Failed","Test");
            }

            @Override
            public void onFinish() {
                // Completed the request (either success or failure)
            }
        });
    }

    */
    @Test
    public void getLocation(){
        UserLocation loc = new UserLocation();
        loc.getLocation();
        Location l = User.getInstance().getLocation();
        Log.d("getLocation","pass");
    }
    public class EditFavoritesTask extends AsyncTask<String, Void, String> {

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

        }
    }

    @Test
    public void editFavorite() {
        new EditFavoritesTask().execute("");
    }

}


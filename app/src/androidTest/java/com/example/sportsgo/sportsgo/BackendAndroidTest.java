package com.example.sportsgo.sportsgo;

import android.app.Application;
import android.net.Uri;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;
import android.util.Log;

import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.model.FacilityList;
import com.example.sportsgo.sportsgo.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


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
            Facility_list.add(new Facility(id, name, longitude, latitude, description, temperature, weather_status));
        }
        FacilityList.getInstance().set_all_facilities(Facility_list);
        Log.d("Backend TEST:", " ends");
    }

}
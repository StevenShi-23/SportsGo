package com.example.sportsgo.sportsgo.utilities;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.sportsgo.sportsgo.model.User;
import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.model.FacilityList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.sportsgo.sportsgo.utilities.NetworkUtils.getAllFacilities;

/**
 * Created by apple on 5/4/17.
 */

public class RefreshService extends Service {
    // constant
    public static final long NOTIFY_INTERVAL = 60 * 1000; // 60 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new getFacilityTask(), 0, NOTIFY_INTERVAL);
    }

    class getFacilityTask extends TimerTask {

        @Override
        public void run(){
            // run on timer thread
            String name = Thread.currentThread().getName();
            Log.d("Refresh Service at ",name);
            String facility_json =  NetworkUtils.getAllFacilities();
            Log.d("Backend TEST:", " starts");


            double usrLat = User.getInstance().getUserLatitude();
            double usrLng = User.getInstance().getUserLongitude();
            Log.d("User Location:", "Got User location : UsrLat = "+usrLat+", UsrLng = "+usrLng);
            try {
                JSONArray facilities_array = new JSONArray(facility_json);
                ArrayList<Facility> Facility_list = new ArrayList<Facility>();
                for (int i = 0; i < facilities_array.length(); i++) {
                    JSONObject jsonobject = facilities_array.getJSONObject(i);
                    String description = jsonobject.getString("description");
                    String weather_status = jsonobject.getString("weather_status");
                    double longitude = jsonobject.getDouble("longitude");
                    double latitude = jsonobject.getDouble("latitude");
                    double temperature = jsonobject.getDouble("temperature");
                    int psi = jsonobject.getInt("psi");
                    int id = jsonobject.getInt("id");
                    int popularity = jsonobject.getInt("popularity");
                    String fname = jsonobject.getString("name");
                    Facility_list.add(new Facility(id, fname, longitude, latitude, description, temperature, weather_status, psi, popularity,usrLat,usrLng ));
                }
                FacilityList.getInstance().set_all_facilities(Facility_list);
            }
            catch (Exception e){
                Log.d("Error RefreshService: ", e.toString());
            }
            Log.d("Backend TEST:", " ends");
        }

        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }
    }
}

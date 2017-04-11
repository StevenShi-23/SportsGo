package com.example.sportsgo.sportsgo.utilities;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.sportsgo.sportsgo.MyApp;
import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.model.FacilityList;
import com.example.sportsgo.sportsgo.model.User;

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
    private LocationManager mLocationManager;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            Log.d("LocationListener", "Changed");
        }
        @Override
        public void onStatusChanged(String a, int b, Bundle c){}

        @Override
        public void onProviderEnabled(String s){}

        @Override
        public void onProviderDisabled(String s){}
    };


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
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        /*
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(MyApp.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( MyApp.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("No permission", "RefreshSERVICE") ;
            return;
        }
        */
        if (ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("No permission", "RefreshSERVICE");
            return;
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10*1000,
                100, mLocationListener);
    }


    class getFacilityTask extends TimerTask {

        @Override
        public void run(){
            // run on timer thread
            String name = Thread.currentThread().getName();
            Log.d("Refresh Service at ",name);
            String facility_json =  NetworkUtils.getAllFacilities();
            Log.d("getFacilityTask:", " starts");
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
                    String fname = jsonobject.getString("name");
                    Facility_list.add(new Facility(id, fname, longitude, latitude, description, temperature, weather_status, psi));
                }
                FacilityList.getInstance().set_all_facilities(Facility_list);
            }
            catch (Exception e){
                Log.d("Error RefreshService: ", e.toString());
            }
            Log.d("getFacilityTask:", " ends");
        }

        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }
    }
}

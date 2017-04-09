package com.example.sportsgo.sportsgo.Activities;

/**
 * Created by StevenShi on 9/4/17.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import com.example.sportsgo.sportsgo.model.User;

import com.example.sportsgo.sportsgo.R;

public class UserLocationActivity extends Activity {
    /** Called when the activity is first created. */

    //create Location Manager
    private LocationManager manager;
    private User UserInstance = User.getInstance();
    private static final String TAG = "UserLocation ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brief_view);

        //get system service
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //get user location
        updateLocation(location, UserInstance);
        //update user location per 10 seconds, or on user moving more than 10 meters
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);

    }
    //pause listening to user location update when the  activity pauses
    public void onPause(){
        super.onPause();
        manager.removeUpdates(locationListener);
    }

    //create a location listener
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateLocation(location,UserInstance);
        }
        public void onProviderDisabled(String provider){
            updateLocation(null,UserInstance);
            Log.i(TAG, "Provider now is disabled..");
        }
        public void onProviderEnabled(String provider){
            Log.i(TAG, "Provider now is enabled..");
        }
        public void onStatusChanged(String provider, int status,Bundle extras){ }
    };

    //update userLocation
    private void updateLocation(Location location, User usrInstance) {
        String latLng;
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            LatLng LL  = new LatLng(lat,lng);

            usrInstance.updateUsrLocation(LL);

            latLng = "Latitude:" + lat + "  Longitude:" + lng;
        } else {
            latLng = "Can't access your location";
        }
        Log.i(TAG, "The location has changed..");
        Log.i( TAG,"Your Location:" +latLng);
    }


}
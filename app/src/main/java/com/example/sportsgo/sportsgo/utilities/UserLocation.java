package com.example.sportsgo.sportsgo.utilities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.sportsgo.sportsgo.MyApp;
import com.example.sportsgo.sportsgo.model.User;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apple on 11/4/17.
 */

public class UserLocation {
    private String TAG = "UserLocation";
    Timer timer;
    LocationManager locationManager;
    OnLocationResultListener listener;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private int LOCATION_RECHECK_TIME = 10000;

    public UserLocation setListener(OnLocationResultListener listener) {
        this.listener = listener;
        return this;
    }

    public boolean getLocation() {

        if (locationManager == null) {
            locationManager = (LocationManager) (MyApp.getContext()).getSystemService(Context.LOCATION_SERVICE);
        }

        //exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            Log.d(TAG, "Location GPS_PROVIDER is off");
            ex.printStackTrace();
        }
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            Log.d(TAG, "Location NETWORK_PROVIDER is off");
            ex.printStackTrace();
        }

        timer = new Timer();
        timer.schedule(new GetLastLocation(), 0, LOCATION_RECHECK_TIME);
        return true;
    }


    LocationListener gpslocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.e(TAG, "Calling gpslocationListener==> onLocationChanged");
            timer.cancel();

            listener.onGotLocation(location);
            if (ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(gpslocationListener);
            locationManager.removeUpdates(networklocationListener);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener networklocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.e(TAG, "Calling networklocationListener==> onLocationChanged");
            timer.cancel();
            listener.onGotLocation(location);
            if (ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(networklocationListener);
            locationManager.removeUpdates(gpslocationListener);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
            Log.d(TAG, "Getting last location in TimeTask ");
            locationManager.removeUpdates(gpslocationListener);
            if (ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return;
            }
            locationManager.removeUpdates(networklocationListener);

            Location net_loc = null, gps_loc = null;
            if (gps_enabled)
                gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (network_enabled)
                net_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            //if there are both values use the latest one
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.getTime() > net_loc.getTime())
                    listener.onGotLocation(gps_loc);
                else
                    listener.onGotLocation(net_loc);
                return;
            }

            if (gps_loc != null) {
                User.getInstance().setLocation(gps_loc);
                Log.d("Set Location","gps_loc");
                listener.onGotLocation(gps_loc);
                return;
            }
            if (net_loc != null) {
                User.getInstance().setLocation(net_loc);
                Log.d("Set Location","net_loc");
                listener.onGotLocation(net_loc);
                return;
            }
            if (gps_loc == null && net_loc == null) {
                Log.d(TAG, "tried to get location in Timetask but both location was null");
            } else if (gps_loc == null) {
                Log.d(TAG, "gps_loc is null in time task");
            } else {
                Log.d(TAG, "net_loc is null in time task");

            }
            listener.onGotLocation(null);
        }
    }

    public interface OnLocationResultListener {
        void onGotLocation(Location location);

        void onNoLocationProvider();
    }
}
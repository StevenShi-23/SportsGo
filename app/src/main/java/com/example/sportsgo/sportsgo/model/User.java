package com.example.sportsgo.sportsgo.model;
import com.google.android.gms.maps.model.LatLng;

import com.example.sportsgo.sportsgo.model.Facility;

import java.util.List;
/**
 * This class stores information of users. For use in User Controller
 * Created by StevenShi on 1/4/17.
 */

public class User {
    private static User ourInstance = new User();
    private int userID;
    private int age;
    private String username;
    private String password;
    private boolean gender;
    private String sportsPreference;
    //by default, user is in NTU
    private double Latitude = 1.3483;
    private double Longitude = 103.6831;
    private User(){
        userID = 1;
        username = "root";
        password = "root";
    }
    public static User getInstance(){
        return ourInstance;
    }
    public int get_id(){
        return userID;
    }

    public double  getUserLatitude() {
        return Latitude;
    }

    public double  getUserLongitude() {
        return Longitude;
    }

    public void updateUsrLocation(double  Lat, double  Lng){
        this.Latitude = Lat;
        this.Longitude = Lng;
    }


}



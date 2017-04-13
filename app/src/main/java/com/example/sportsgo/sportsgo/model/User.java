package com.example.sportsgo.sportsgo.model;
import android.location.Location;

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
    private LatLng userlocation;
    private User(){
        //userID = 1;
        username = "root";
        password = "root";
        userlocation = new LatLng(1.3483, 103.6831);
    }
    public static User getInstance(){
        return ourInstance;
    }
    public int get_id(){
        return userID;
    }

    public LatLng  getUserLocation() {
        return userlocation;
    }

    public void updateUsrLocation(LatLng LL){
        this.userlocation= LL;
    }
    public void setID(int id){
        userID = id;
    }
    public void setLocation(Location l){
        this.userlocation = new LatLng(l.getLatitude(),l.getLongitude());
    }

}



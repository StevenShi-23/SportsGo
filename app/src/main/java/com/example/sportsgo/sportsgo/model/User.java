package com.example.sportsgo.sportsgo.model;
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


}



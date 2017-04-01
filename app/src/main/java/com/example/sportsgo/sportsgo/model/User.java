package com.example.sportsgo.sportsgo.model;
import com.example.sportsgo.sportsgo.model.Facility;

import java.util.List;
/**
 * This class stores information of users. For use in User Controller
 * Created by StevenShi on 1/4/17.
 */

public class User {
    private int userID;
    private int age;
    private String username;
    private boolean gender;
    private String sportsPreference;
    //Use list<Facility> for faster access to the list of favourite facilities than using reference
    private List<Facility> favFacilityList;
}



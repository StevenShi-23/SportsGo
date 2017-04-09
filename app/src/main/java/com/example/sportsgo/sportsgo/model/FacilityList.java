package com.example.sportsgo.sportsgo.model;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 6/4/17.
 */
public class FacilityList {
    private static FacilityList ourInstance = new FacilityList();
    private static ArrayList<Facility> ALL_Facilities;
    public static FacilityList getInstance() {
        return ourInstance;
    }

    private FacilityList() {
    }
    public ArrayList<Facility> get_all_facilities(){
        return ALL_Facilities;
    }
    public boolean set_all_facilities(ArrayList<Facility> these_facilities){
        Log.d("setting facilities:", new Integer(these_facilities.size()).toString());
        try {
            ALL_Facilities = these_facilities;
            return true;
        }
        catch(Exception e){
            return false;
        }
    }


}

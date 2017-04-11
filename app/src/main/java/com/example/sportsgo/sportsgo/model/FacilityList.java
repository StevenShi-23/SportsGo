package com.example.sportsgo.sportsgo.model;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    public ArrayList<Facility> get_all_facilities_from_ids(int[] ids){
        Arrays.sort(ids);
        ArrayList<Facility> favorites = new ArrayList<Facility>();
        int start = 0;
        for(int id : ids){
            while(start < ALL_Facilities.size()){
                if(ALL_Facilities.get(start).facilityID == id){
                    favorites.add(ALL_Facilities.get(start));
                    start += 1;
                    break;
                }
                else{
                    start += 1;
                }
            }
        }
        return favorites;
    }
    public boolean set_all_facilities(ArrayList<Facility> these_facilities){
        Log.d("setting facilities:", new Integer(these_facilities.size()).toString());
        try {
            ALL_Facilities = these_facilities;
            Collections.sort(ALL_Facilities, new Comparator<Facility>() {
                @Override
                public int compare(Facility f1, Facility f2) {
                    return (f1.facilityID < f2.facilityID)?-1 : 1;
                }
            });
            return true;
        }
        catch(Exception e){
            return false;
        }
    }


}

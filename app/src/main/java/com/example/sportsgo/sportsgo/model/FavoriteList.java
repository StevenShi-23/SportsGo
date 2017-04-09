package com.example.sportsgo.sportsgo.model;

import android.util.Log;

import com.example.sportsgo.sportsgo.utilities.Favorites_update;
import com.example.sportsgo.sportsgo.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.Timer;

import static android.content.ContentValues.TAG;

/**
 * Created by NghiaNguyen on 4/8/2017.
 */

public class FavoriteList {
    private static FavoriteList ourInstance = new FavoriteList();
    private static ArrayList<Facility> favoriteList;
    public static FavoriteList getInstance() {
        return ourInstance;
    }

    private FavoriteList() {
        favoriteList = new ArrayList<Facility>();
        //favoriteList = FacilityList.getInstance().get_all_facilities();
    }
    public ArrayList<Facility> getFavoriteList(){
        return favoriteList;
    }

    public boolean inFavoriteList(Facility facility){
        if(favoriteList.indexOf(facility)==-1) return false;
        return true;
    };

    public boolean addToFavoriteList(Facility facility){
        try {
            favoriteList.add(facility);
            new Favorites_update(User.getInstance().get_id(),facility.facilityID, true).execute();
            return true;
        } catch (Exception e){
            Log.d(TAG, "addToFavoriteList: " + e);
            return false;
        }
    }
    public boolean removeFromFavoriteList(Facility facility){
        try {
            favoriteList.remove(facility);
            new Favorites_update(User.getInstance().get_id(),facility.facilityID, false).execute();
            return true;
        } catch (Exception e){
            Log.d(TAG, "addToFavoriteList: " + e);
            return false;
        }
    }
}

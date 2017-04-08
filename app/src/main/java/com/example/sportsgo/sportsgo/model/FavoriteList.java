package com.example.sportsgo.sportsgo.model;

import android.util.Log;

import java.util.ArrayList;

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
            return true;
        } catch (Exception e){
            Log.d(TAG, "addToFavoriteList: " + e);
            return false;
        }
    }
    public boolean removeFromFavoriteList(Facility f){
        try {
            //favoriteList.remove();
            return true;
        } catch (Exception e){
            Log.d(TAG, "addToFavoriteList: " + e);
            return false;
        }
    }
}

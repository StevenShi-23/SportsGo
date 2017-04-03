package com.example.sportsgo.sportsgo.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by StevenShi on 22/3/17.
 */

public class BriefView extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Log.d("BriefView on create", savedInstanceState.toString());
        }
        catch (Exception e){
            Log.d("BriefView Bundle", "cannot be converted to string");
        }
    }
}

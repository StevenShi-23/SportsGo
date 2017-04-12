package com.example.sportsgo.sportsgo.utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by apple on 9/4/17.
 */

public class Favorites_update extends AsyncTask<String, Void, String> {
    private int user_id, facility_id;
    private boolean like;
    public Favorites_update(int user_id, int facility_id, boolean like){
        this.user_id = user_id;
        this.facility_id = facility_id;
        this.like = like;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            //String searchUrl = params[0];
            String EditResults = null;
            try {
                EditResults  = NetworkUtils.editFavorite(user_id, facility_id, like);
                Log.d("In backgroud","Favorites update");
                return EditResults ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch(Exception e){
            Log.d("Exception","Favorites update");
        }
        return "";
    }

    @Override
    protected void onPostExecute(String editResults) {

        final String EditResults = editResults;
        Log.d("EditFavoritesTask",EditResults);

    }
}


package com.example.sportsgo.sportsgo.utilities;

import android.os.AsyncTask;

/**
 * Created by apple on 9/4/17.
 */

public class Favorites_update extends AsyncTask<Void, Void, Void> {
    private int user_id, facility_id;
    private boolean ADD;
    public Favorites_update(int user_id, int facility_id, boolean ADD){
        this.user_id = user_id;
        this.facility_id = facility_id;
        this.ADD = ADD;
    }

    // Runs in UI before background thread is called
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Do something like display a progress bar
    }

    // This is run in a background thread
    @Override
    protected Void doInBackground(Void... params) {
        // get the string from params, which is an array
        //String myString = params[0];
        NetworkUtils.editFavorite(user_id, facility_id, ADD);
        return null;
    }

    // This runs in UI when background thread finishes
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

    }
}


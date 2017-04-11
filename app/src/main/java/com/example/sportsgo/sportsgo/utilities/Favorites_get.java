package com.example.sportsgo.sportsgo.utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.model.FacilityList;
import com.example.sportsgo.sportsgo.model.FavoriteList;
import com.example.sportsgo.sportsgo.model.User;

import java.util.ArrayList;

/**
 * Created by apple on 11/4/17.
 */

public class Favorites_get extends AsyncTask<Void, Void, String> {
        private int user_id;
        public Favorites_get(int user_id){
            this.user_id = user_id;
        }

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(Void... params) {

            String url = NetworkUtils.buildFavoritesUrl(User.getInstance().get_id());
            try{
                String response = NetworkUtils.getResponseFromHttpUrl(url);
                return response;
            }
            catch(Exception e){
                Log.d("Favorites_get","Exception");
            }
            return "";
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String[] items = result.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
            Log.d("Favorites_get",new Integer(items.length).toString());
            int[] results = new int[items.length];

            for (int i = 0; i < items.length; i++) {
                try {
                    results[i] = Integer.parseInt(items[i]);
                } catch (NumberFormatException nfe) {
                    //NOTE: write something here if you need to recover from formatting errors
                };
            }
            ArrayList<Facility> all_favorites = FacilityList.getInstance().get_all_facilities_from_ids(results);
            FavoriteList.getInstance().set_all_facilities(all_favorites);
        }

}

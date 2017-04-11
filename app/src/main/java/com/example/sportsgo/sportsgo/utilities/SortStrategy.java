package com.example.sportsgo.sportsgo.utilities;
import android.location.Location;

import com.example.sportsgo.sportsgo.model.Facility;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by StevenShi on 11/4/17.
 */

public interface SortStrategy {
    public List<Facility> SortFacility (String cretaria, List<Facility> all_facilities, String DEFAULT_OPTION);
}

class sortByDistance implements SortStrategy{
    private double meterDistanceBetweenPoints(double usrLat, double usrLng, double fLat, double fLng) {
        Location locationUsr = new Location("point A");

        locationUsr.setLatitude(usrLat);
        locationUsr.setLongitude(usrLng);

        Location locationF = new Location("point B");

        locationF.setLatitude(fLat);
        locationF.setLongitude(fLng);

        double distance = locationUsr.distanceTo(locationF);
        return distance;
    }
    @Override
    public List<Facility> SortFacility (String option, List<Facility> all_facilities, String DEFAULT_OPTION){

    }
}

class sortByPopularity implements SortStrategy{
    @Override
    public List<Facility> SortFacility (String option, List<Facility> all_facilities, String DEFAULT_OPTION){

    }
}






package com.example.sportsgo.sportsgo;

/**
 * Created by Long on 3/18/2017.
 */

public class SearchController {
    FacilityController fctrl;

    public SearchController(){
        fctrl = new FacilityController();
    }

    public Facility[] FilterAndSortRequest(){
        return null;
    }

    Facility[] searchKeyword(String key){
        Facility MatchedFacilities[] = fctrl.GetMatchedFacilities(key);
        return MatchedFacilities;
    }
}

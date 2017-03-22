package com.example.sportsgo.sportsgo;

/**
 * Created by Long on 3/18/2017.
 */

public class SearchController {
    FacilityController fctrl;

    public SearchController(){
        fctrl = new FacilityController();
    }

    public Object FilterAndSortRequest(){
        return null;
    }

    Object searchKeyword(String key){
        Object MatchedFacilities = fctrl.GetMatchedFacilities(key);
        return MatchedFacilities;
    }
}

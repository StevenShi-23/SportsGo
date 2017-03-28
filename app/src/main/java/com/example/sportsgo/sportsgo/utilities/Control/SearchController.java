package com.example.sportsgo.sportsgo.utilities.Control;

import com.example.sportsgo.sportsgo.utilities.Control.FacilityController;
import com.example.sportsgo.sportsgo.utilities.Entity.Facility;

/**
 * This class handles the search query received from SearchView
 * @author Long
 */

public class SearchController {
    FacilityController fctrl;

    /**
     * Constructor
     */
    public SearchController(){
        fctrl = new FacilityController();
    }

    /**
     * This method return a sorted or filtered list of facility
     * based on the option that the caller chooses
     * @return Facility[]
     */
    public Facility[] FilterAndSortRequest(){
        return null;
    }

    /**
     * Caller passes in the key word to this method to get back the results of the query
     * @param key
     * @return Facility[] - the list of facilities that match the key
     */
    public Facility[] searchKeyword(String key){
        Facility MatchedFacilities[] = fctrl.GetMatchedFacilities(key);
        return MatchedFacilities;
    }
}

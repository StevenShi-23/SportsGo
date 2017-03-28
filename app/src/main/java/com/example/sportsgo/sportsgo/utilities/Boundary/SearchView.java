package com.example.sportsgo.sportsgo.utilities.Boundary;

import com.example.sportsgo.sportsgo.utilities.Control.SearchController;
import com.example.sportsgo.sportsgo.utilities.Entity.Facility;

/**
 * This class handles the UI of the search view
 * @author Long
 */

public class SearchView {
    private SearchController schctrl;
    private Facility[] Facilities;
    private String kw;

    /**
     * constructor
     */
    public SearchView(){
        schctrl = new SearchController();
        kw = "";
        Facilities = new Facility[3];
    }

    /**
     * This methods handles the search activity
     * @param key
     */
    public void search(String key){
        kw = key;
        Facilities = schctrl.searchKeyword(kw);
        PrepareBriefView();
    }

    /**
     * This method handles the filter activity
     * @param s
     */
    public String filterResult(String s){
        Facilities = schctrl.FilterAndSortRequest();
        PrepareBriefView();
        return "You searched for "+s;
    }

    /**
     * This method creates brief view base on facilities
     */
    public void PrepareBriefView(){

        System.out.print("ok");
    }

    /**
     * This method handles the event which a facility in brief view is clicked
     */
    public void clickFacility(){
        //
    }
}

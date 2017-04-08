package com.example.sportsgo.sportsgo.Activities;

import com.example.sportsgo.sportsgo.presenter.SearchController;
import com.example.sportsgo.sportsgo.model.Facility;

/**
 * This class handles the UI of the search view
 * @author Long
 */

public class mSearchView {
    private SearchController schctrl;
    private Facility[] Facilities;
    private String kw;

    /**
     * constructor
     */
    public mSearchView(){
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

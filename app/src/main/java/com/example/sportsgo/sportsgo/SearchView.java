package com.example.sportsgo.sportsgo;

/**
 * Created by Long on 3/18/2017.
 */

public class SearchView {
    private SearchController schctrl;
    private Object Facilities;
    private String kw;

    public SearchView(){ //constructor
        schctrl = new SearchController();
        kw = "";
        Object Facilities = new Object();
    }

    public void search(String key){ //enter keyword
        kw = key;
        Facilities = schctrl.searchKeyword(kw);
        PrepareBriefView();
    }

    public String filterResult(String s){
        Object Facilities = schctrl.FilterAndSortRequest();
        PrepareBriefView();
        return "You searched for "+s;
    }

    public void PrepareBriefView(){
        //createbriefview base on facilities
        System.out.print("ok");
    }

    public void clickFacility(){
        //
    }
}

package com.example.sportsgo.sportsgo.utilities;

import com.example.sportsgo.sportsgo.model.Facility;

import java.util.List;

/**
 * Created by StevenShi on 11/4/17.
 */

public class filterContext {

    public static List<Facility> setFilterByStrategy(String creteria, List<Facility> all_facilities, String DEFAULT_CRETARIA) {
        FilterStrategy filterBy;
        if (creteria == "dry") {
            filterBy = new FilterDrySports();
            return filterBy.FilterFacility(creteria, all_facilities, DEFAULT_CRETARIA);
        } else {
            filterBy = new FilterWaterSports();
            return filterBy.FilterFacility(creteria,all_facilities, DEFAULT_CRETARIA);
        }
    }
}
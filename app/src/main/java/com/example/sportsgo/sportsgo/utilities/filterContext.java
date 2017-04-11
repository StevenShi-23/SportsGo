package com.example.sportsgo.sportsgo.utilities;

import com.example.sportsgo.sportsgo.model.Facility;

import java.util.List;

/**
 * Created by StevenShi on 11/4/17.
 */

public class filterContext {
    private FilterStrategy filterBy;

    public List<Facility> setFilterByStrategy(String cretaria, List<Facility> all_facilities, String DEFAULT_CRETARIA) {
        if (cretaria == "dry") {
            this.filterBy = new FilterDrySports();
            return filterBy.FilterFacility(cretaria, all_facilities, DEFAULT_CRETARIA);
        } else {
            this.filterBy = new FilterDrySports();
            return filterBy.FilterFacility(cretaria,all_facilities, DEFAULT_CRETARIA);
        }
    }
}
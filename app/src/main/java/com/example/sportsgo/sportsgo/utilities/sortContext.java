package com.example.sportsgo.sportsgo.utilities;

import com.example.sportsgo.sportsgo.model.Facility;

import java.util.List;

/**
 * Created by StevenShi on 11/4/17.
 */

public class sortContext {
    private SortStrategy sortBy;
    public void setSortByStrategy (String option, List<Facility> all_facilities){
        if (option=="popularity"){
            this.sortBy = new sortByPopularity();
            sortBy.SortFacility(all_facilities);
        }
        else{
            // by default, sort by distance
            this.sortBy = new sortByDistance();
            sortBy.SortFacility(all_facilities);
        }
    }
}
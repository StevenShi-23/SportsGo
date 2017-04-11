package com.example.sportsgo.sportsgo.utilities;

import com.example.sportsgo.sportsgo.model.Facility;

import java.util.List;

/**
 * Created by StevenShi on 11/4/17.
 */

public class sortContext {

    public static void setSortByStrategy (String option, List<Facility> all_facilities){
        SortStrategy sortBy;
        if (option=="popularity"){
            sortBy = new sortByPopularity();
            sortBy.SortFacility(all_facilities);
        }
        else{
            // by default, sort by distance
            sortBy = new sortByDistance();
            sortBy.SortFacility(all_facilities);
        }
    }
}
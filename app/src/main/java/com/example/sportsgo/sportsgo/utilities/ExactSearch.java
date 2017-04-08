package com.example.sportsgo.sportsgo.utilities;

import com.example.sportsgo.sportsgo.model.Facility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 8/4/17.
 */

public class ExactSearch {
    public static List<Facility> exact_search(String keywords, List<Facility> all_facilities){
        List<Facility> results = new ArrayList<Facility>();
        keywords = keywords.toLowerCase();
        for (Facility f : all_facilities){
            if(f.facilityName.toLowerCase().contains(keywords)){
                results.add(f);
            }
        }
        return results;
    }

}

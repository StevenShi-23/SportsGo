package com.example.sportsgo.sportsgo.utilities;

import com.example.sportsgo.sportsgo.model.Facility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * Created by gongzhen on 11/4/17.
 */

public interface SortStrategy {
    public void SortFacility ( List<Facility> all_facilities);
}

class sortByDistance implements SortStrategy {

    @Override
    public void SortFacility(List<Facility> all_facilities) {
        Collections.sort(all_facilities, new Comparator<Facility>() {
            @Override
            public int compare(Facility fD1, Facility fD2) {
                // -1 - less than, 1 - greater than, all inversed for descending
                return (fD1.distance - fD2.distance)<0?-1:1;
            }
        });
    }
}

class sortByPopularity implements SortStrategy {
    @Override
    public void SortFacility(List<Facility> all_facilities) {
        Collections.sort(all_facilities, new Comparator<Facility>() {
            @Override
            public int compare(Facility fD1, Facility fD2) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return (fD1.popularity - fD2.popularity)<0?-1:1;
            }
        });
    }
}

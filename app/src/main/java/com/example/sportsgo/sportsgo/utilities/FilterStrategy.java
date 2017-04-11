package com.example.sportsgo.sportsgo.utilities;
import com.example.sportsgo.sportsgo.model.Facility;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by StevenShi on 11/4/17.
 */

public interface FilterStrategy {
    public List<Facility> FilterFacility (String cretaria, List<Facility> all_facilities, String DEFAULT_CRETARIA);
}

class FilterDrySports implements FilterStrategy{
    @Override
    public List<Facility> FilterFacility (String cretaria, List<Facility> all_facilities, String DEFAULT_CRETARIA){
        List<Facility> Land_results = new ArrayList<Facility>();

        if(cretaria == DEFAULT_CRETARIA){
            return all_facilities;
        }
        else {
            for (Facility f : all_facilities) {
                if (!f.facilityName.toLowerCase().contains("swimming")&&!f.facilityName.toLowerCase().contains("water")) {
                    Land_results.add(f);
                }
            }
                return Land_results;
        }
    }
}

class FilterWaterSports implements FilterStrategy{
    @Override
    public List<Facility> FilterFacility (String cretaria, List<Facility> all_facilities, String DEFAULT_CRETARIA){
        List<Facility> Water_results = new ArrayList<Facility>();

        if(cretaria == DEFAULT_CRETARIA){
            return all_facilities;
        }
        else {
            for (Facility f : all_facilities) {
                if (f.facilityName.toLowerCase().contains("swimming")||f.facilityName.toLowerCase().contains("water")) {
                    Water_results.add(f);
                }
            }
            return Water_results;
        }
    }
}

package com.example.sportsgo.sportsgo.presenter;

import com.example.sportsgo.sportsgo.model.Facility;

import java.io.BufferedReader;
import java.net.URLConnection;

/**
 * Created by Long on 3/18/2017.
 */
import java.net.*;
import java.io.*;

/**
 * This class implements all the computations on Facility list
 * @author
 */
public class FacilityController {
    /**
     * This method implements the logic of finding the all the facilities that
     * has name containing keyword
     * @param key
     * @return
     */
    public Facility[] GetMatchedFacilities(String key){
        URL query;
        URLConnection yc;
        Facility[] target = new Facility[3];
        try {
            query = new URL("http://www.oracle.com/");
            yc = query.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
            target = new Facility[3];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }
}

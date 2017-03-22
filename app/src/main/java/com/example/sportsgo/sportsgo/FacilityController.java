package com.example.sportsgo.sportsgo;

import java.io.BufferedReader;
import java.net.URLConnection;

/**
 * Created by Long on 3/18/2017.
 */
import java.net.*;
import java.io.*;

public class FacilityController {
    public Object GetMatchedFacilities(String key){
        URL query;
        URLConnection yc;
        Object target = new Object();
        try {
            query = new URL("http://www.oracle.com/");
            yc = query.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
            target = new Object();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }
}

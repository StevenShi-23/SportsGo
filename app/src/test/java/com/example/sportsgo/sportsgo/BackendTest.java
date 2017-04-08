package com.example.sportsgo.sportsgo;

import com.example.sportsgo.sportsgo.utilities.NetworkUtils;

import org.junit.Test;
import android.net.Uri;


/**
 * Created by apple on 6/4/17.
 */

public class BackendTest {
    private static String BACKEND_BASE_URL = "http://sports-go.cloudapp.net";
    @Test
    public void main(){
        Uri builtUri = Uri.parse(BACKEND_BASE_URL);
        //String facility_list =  NetworkUtils.getAllFacilities();
        System.out.println(builtUri);
    }

}

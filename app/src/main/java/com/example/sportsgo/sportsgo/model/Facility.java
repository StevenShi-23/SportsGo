package com.example.sportsgo.sportsgo.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * This class implements the Facility entity with
 * the attribute description, name, weather_status, longtitude, latitude, temperature, id
 *
 * @author Long and Shi Ziji
 */

public class Facility {
    public String facilityDescription;
    public double longitude, latitude;
    public double temperature;
    public int facilityID;
    public int PSI;
    // populariy is defined as the number of favourites, will use dummy data if favourite not implemented
    public int popularity;
    public double distance;
    public String facilityName;
    public String weather_status;
    public int psi;

    public Facility(int id, String name, double longitude, double latitude, String description, double temperature, String weather_status, int psi){
        this.facilityID = id;
        this.facilityName = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.facilityDescription = description;
        this.temperature = temperature;
        this.weather_status = weather_status;
        this.psi = psi;
    }

    public void distanceToUsr(User UsrInstance){

        double usrLat = UsrInstance.getUserLatitude();
        double usrLng = UsrInstance.getUserLongitude();

        double earthRadius = 6371; // 6371.0 kilometers
        double dLat = Math.toRadians(usrLat-this.latitude);
        double dLng = Math.toRadians(usrLng-this.longitude);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                    * Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(usrLat));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        this.distance = dist;
    }



}

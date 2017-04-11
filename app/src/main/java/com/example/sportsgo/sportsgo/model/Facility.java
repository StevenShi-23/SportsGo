package com.example.sportsgo.sportsgo.model;

import android.location.Location;

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
    // populariy is defined as the number of favourites, will use dummy data if favourite not implemented
    public int popularity = 0;
    public double distance = 0;
    public String facilityName;
    public String weather_status;
    public int psi;

    public Facility(int id, String name, double longitude, double latitude, String description, double temperature, String weather_status, int psi, int popularity, double usrLat, double usrLng){
        this.facilityID = id;
        this.facilityName = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.facilityDescription = description;
        this.temperature = temperature;
        this.weather_status = weather_status;
        this.psi = psi;
        this.popularity = popularity;
        this.distance = meterDistanceBetweenPoints(usrLat, usrLng, this.latitude,this.longitude);
    }

    private double meterDistanceBetweenPoints(double usrLat, double usrLng, double fLat, double fLng) {
        Location locationUsr = new Location("point A");

        locationUsr.setLatitude(usrLat);
        locationUsr.setLongitude(usrLng);

        Location locationF = new Location("point B");

        locationF.setLatitude(fLat);
        locationF.setLongitude(fLng);

        double distance = locationUsr.distanceTo(locationF);
        return distance;
    }



}

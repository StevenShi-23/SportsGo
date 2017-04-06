package com.example.sportsgo.sportsgo.model;

/**
 * This class implements the Facility entity with
 * the attribute description, name, weather_status, longtitude, latitude, temperature, id
 *
 * @author Long and Shi Ziji
 */

public class Facility {
    protected String facilityDescription;
    protected double longitude, latitude;
    protected double temperature;
    protected int facilityID;
    protected int PSI;
    // populariy is defined as the number of favourites, will use dummy data if favourite not implemented
    protected int popularity;
    protected double distance;
    protected String facilityName;
    protected String weather_status;
    public Facility(int id, String name, double longitude, double latitude, String description, double temperature, String weather_status){
        this.facilityID = id;
        this.facilityName = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.facilityDescription = description;
        this.temperature = temperature;
        this.weather_status = weather_status;
    }
}

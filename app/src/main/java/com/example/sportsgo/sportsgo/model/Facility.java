package com.example.sportsgo.sportsgo.model;

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
}

package de.goeuro.demo;

import java.io.Serializable;

public class GeoPosition implements Serializable {

    private double latitude;
    private double longitude;

    public GeoPosition(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

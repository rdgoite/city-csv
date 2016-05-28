package de.goeuro.demo;

import java.io.Serializable;

import static java.lang.String.format;

public class Location implements Serializable {

    private final long id;

    private final String type;
    private final String name;

    private final GeoPosition geoPosition;

    public Location(long id, String type, String name, double latitude, double longitude) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.geoPosition = new GeoPosition(latitude, longitude);
    }

    public String toCsv() {
        return format("%d,%s,%s,%s,%s", id, type, name, geoPosition.getLatitude(), geoPosition.getLongitude());
    }
}

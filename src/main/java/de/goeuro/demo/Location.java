package de.goeuro.demo;

import java.io.Serializable;

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
        return "airport,Berlin Sch\u00f6nefeld,52.388,13.5180874";
    }
}

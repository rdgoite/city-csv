package de.goeuro.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import static java.lang.String.format;

public class Location implements Serializable {

    private final long id;

    private final String type;
    private final String name;

    private final GeoPosition geoPosition;

    @JsonCreator
    public Location(@JsonProperty("_id") long id, @JsonProperty("type") String type,
            @JsonProperty("name") String name,
            @JsonProperty("geo_position") GeoPosition geoPosition) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.geoPosition = geoPosition;
    }

    public Location(long id, String type, String name, double latitude, double longitude) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.geoPosition = new GeoPosition(latitude, longitude);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public String toCsv() {
        return format("%d,%s,%s,%s,%s", id, type, name, geoPosition.getLatitude(),
                geoPosition.getLongitude());
    }

}

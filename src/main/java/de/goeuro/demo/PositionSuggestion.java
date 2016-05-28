package de.goeuro.demo;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class PositionSuggestion {

    private final List<Location> locations;

    public PositionSuggestion(List<Location> locations) {
        this.locations = unmodifiableList(locations);
    }

    public List<Location> getLocations() {
        return locations;
    }

}

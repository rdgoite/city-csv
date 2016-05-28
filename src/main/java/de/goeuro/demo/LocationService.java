package de.goeuro.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static java.lang.String.format;
import static java.util.Arrays.asList;

public class LocationService {

    @Autowired
    private RestTemplate restTemplate;

    public PositionSuggestion suggestPosition(String keyword) {
        String url = format("http://api.goeuro.com/api/v2/position/suggest/en/%s", keyword);
        Location[] locations = restTemplate.getForObject(url, Location[].class);
        return new PositionSuggestion(asList(locations));
    }

}

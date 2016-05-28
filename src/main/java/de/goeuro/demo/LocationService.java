package de.goeuro.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class LocationService {

    @Autowired
    private RestTemplate restTemplate;

    public PositionSuggestion suggestPosition(String keyword) {
        String url = format("http://api.goeuro.com/api/v2/position/suggest/en/%s", keyword);
        Location[] locations = new Location[0];
        try {
            locations = restTemplate.getForObject(url, Location[].class);
        } catch (HttpStatusCodeException exception) {
            if (NOT_FOUND != exception.getStatusCode()) {
                throw exception;
            }
        }
        return new PositionSuggestion(asList(locations));
    }

}

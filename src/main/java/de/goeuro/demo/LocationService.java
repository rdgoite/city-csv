package de.goeuro.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

public class LocationService {

    @Autowired
    private RestTemplate restTemplate;

    public PositionSuggestion suggestPosition(String keyword) {
        String url = format("http://api.goeuro.com/api/v2/position/suggest/en/%s", keyword);
        restTemplate.getForObject(url, String.class);
        return new PositionSuggestion();
    }

}

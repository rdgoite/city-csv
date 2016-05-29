package de.goeuro.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${goeuro.url.position.suggest:http://api.goeuro.com/api/v2/position/suggest/en}")
    private String suggestPositionBaseUrl;

    public PositionSuggestion suggestPosition(String keyword) {
        String url = format("%s/%s", suggestPositionBaseUrl, keyword);
        Location[] locations = new Location[0];
        try {
            locations = restTemplate.getForObject(url, Location[].class);
        } catch (HttpStatusCodeException exception) {
            LOGGER.debug(exception.getMessage());
            if (NOT_FOUND != exception.getStatusCode()) {
                throw exception;
            }
        }
        return new PositionSuggestion(asList(locations));
    }

}

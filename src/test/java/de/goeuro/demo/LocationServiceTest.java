package de.goeuro.demo;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={LocationTestConfiguration.class})
public class LocationServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LocationService service;

    private static final String SUGGEST_POSITION_RESPONSE;

    static {
        String suggestPositionResponse = "";
        try {
            URL suggestPositionJson = Resources.getResource("json/suggest-position.json");
            suggestPositionResponse = Resources.toString(suggestPositionJson, Charsets.UTF_8);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        SUGGEST_POSITION_RESPONSE = suggestPositionResponse;
    }

    @Test
    public void testSuggestPosition() {
        //given:
        String searchKey = "Berlin";

        //and:
        String uri = format("http://api.goeuro.com/api/v2/position/suggest/en/%s", searchKey);
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(requestTo(uri))
                .andExpect(method(GET))
                .andRespond(withSuccess(SUGGEST_POSITION_RESPONSE, APPLICATION_JSON));

        //when:
        PositionSuggestion suggestion = service.suggestPosition(searchKey);

        //then:
        server.verify();

        //and:
        assertNotNull(suggestion);
        assertEquals(8, suggestion.getLocations().size());

        //and:
        Optional<Location> findBerlin = suggestion.getLocations().stream()
                .filter(location -> 376217 == location.getId())
                .findFirst();
        Location berlin = findBerlin.get();
        assertNotNull(berlin);
        assertEquals("Berlin", berlin.getName());
        assertEquals("location", berlin.getType());
        assertTrue(new Double(52.52437).equals(berlin.getGeoPosition().getLatitude()));
        assertTrue(new Double(13.41053).equals(berlin.getGeoPosition().getLongitude()));
    }

}

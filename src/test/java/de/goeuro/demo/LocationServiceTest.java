package de.goeuro.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={LocationTestConfiguration.class})
public class LocationServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LocationService service;

    @Test
    public void testSuggestPosition() {
        //given:
        String searchKey = "Berlin";

        //and:
        String uri = format("http://api.goeuro.com/api/v2/position/suggest/en/%s", searchKey);
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(requestTo(uri))
                .andRespond(withSuccess());

        //when:
        PositionSuggestion suggestion = service.suggestPosition(searchKey);

        //then:
        server.verify();

        //and:
        assertNotNull(suggestion);
    }

}

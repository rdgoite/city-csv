package de.goeuro.demo.location;

import de.goeuro.demo.LocationService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.spy;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class LocationTestConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return spy(new RestTemplate());
    }

    @Bean
    public LocationService locationService() {
        return new LocationService();
    }

}

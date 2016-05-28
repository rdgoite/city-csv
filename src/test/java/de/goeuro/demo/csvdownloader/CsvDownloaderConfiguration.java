package de.goeuro.demo.csvdownloader;

import de.goeuro.demo.CsvDownloader;
import de.goeuro.demo.LocationService;
import de.goeuro.demo.LocationTestConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;

@Configuration
@Import({LocationTestConfiguration.class})
public class CsvDownloaderConfiguration {

    @Bean
    public LocationService locationService() {
        return mock(LocationService.class);
    }

    @Bean
    public CsvDownloader csvDownloader() {
        return new CsvDownloader();
    }

}

package de.goeuro.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CityCsvApplication {

    @Configuration
    protected static class ApplicationConfiguration {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @Bean
        public LocationService locationService() {
            return new LocationService();
        }

        @Bean
        public CsvDownloader csvDownloader() {
            return new CsvDownloader();
        }

        @Bean
        public CsvDownloadRunner csvDownloadRunner() {
            return new CsvDownloadRunner(csvDownloader());
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(CityCsvApplication.class, args);
    }

}

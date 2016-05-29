package de.goeuro.demo;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

public class CsvDownloader {

    @Autowired
    private LocationService locationService;

    public void download(String keyword) {
        try {
            File outputFile = new File("location.csv");
            if (outputFile.createNewFile()) {
                doDownload(keyword, outputFile);
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void doDownload(String keyword, File outputFile) throws Exception {
        PositionSuggestion suggestion = locationService.suggestPosition(keyword);
        FileWriter writer = new FileWriter(outputFile);
        List<Location> locations = suggestion.getLocations();
        try {
            for (Location location : locations) {
                writer.write(format("%s\n", location.toCsv()));
            }
        } finally {
            writer.close();
        }
    }

}

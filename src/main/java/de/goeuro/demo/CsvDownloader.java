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
        PositionSuggestion suggestion = locationService.suggestPosition(keyword);
        File outputFile = new File("location.csv");
        FileWriter writer = null;
        try {
            if (outputFile.createNewFile()) {
                writer = new FileWriter(outputFile);
                List<Location> locations = suggestion.getLocations();
                for (Location location : locations) {
                    writer.write(format("%s\n", location.toCsv()));
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        } finally {
            if (writer != null) try {
                writer.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

}

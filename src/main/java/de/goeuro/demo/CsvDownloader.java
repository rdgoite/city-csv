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
        try {
            File outputFile = new File("location.csv");
            outputFile.createNewFile();
            List<Location> locations = suggestion.getLocations();
            writeOutput(locations, outputFile);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void writeOutput(List<Location> locations, File outputFile) throws Exception {
        FileWriter writer = new FileWriter(outputFile);
        try {
            for (Location location : locations) {
                writer.write(format("%s\n", location.toCsv()));
            }
            if (locations.isEmpty()) writer.write("# no results found");
        } finally {
            writer.close();
        }
    }

}

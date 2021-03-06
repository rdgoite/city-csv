package de.goeuro.demo;

import de.goeuro.demo.exception.OutputFileExistsException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

public class CsvDownloader {

    @Autowired
    private LocationService locationService;
    
    public void download(String keyword, boolean override) {
        PositionSuggestion suggestion = locationService.suggestPosition(keyword);
        try {
            File outputFile = new File("location.csv");
            if (outputFile.createNewFile() || override) {
                List<Location> locations = suggestion.getLocations();
                writeOutput(locations, outputFile);
            } else {
                throw new OutputFileExistsException();
            }
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

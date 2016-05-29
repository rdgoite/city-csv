package de.goeuro.demo;

import java.io.File;
import java.io.IOException;

public class CsvDownloader {

    public void download(String keyword) {
        File file = new File("location.csv");
        try {
            file.createNewFile();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}

package de.goeuro.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class CsvDownloadRunner implements ApplicationRunner {

    private final CsvDownloader downloader;

    public CsvDownloadRunner(CsvDownloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        downloader.download("Berlin", false);
    }

}

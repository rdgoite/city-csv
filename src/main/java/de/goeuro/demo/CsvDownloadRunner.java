package de.goeuro.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

public class CsvDownloadRunner implements ApplicationRunner {

    private final CsvDownloader downloader;

    public CsvDownloadRunner(CsvDownloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> nonOptionArgs = args.getNonOptionArgs();
        if (!nonOptionArgs.isEmpty()) {
            String keyword = nonOptionArgs.get(0);
            String override = "false";
            String forceOption = "force";
            if (args.containsOption(forceOption)) {
                override = args.getOptionValues(forceOption).get(0);
            }
            downloader.download(keyword, Boolean.parseBoolean(override));
        } else {
            System.out.println("No keyword specified.");
        }
    }

}

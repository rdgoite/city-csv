package de.goeuro.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

public class CsvDownloadRunner implements ApplicationRunner {

    public static final String KEYWORD_COUNT_MESSAGE = "Expected exactly 1 keyword.";

    private final CsvDownloader downloader;

    public CsvDownloadRunner(CsvDownloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> nonOptionArgs = args.getNonOptionArgs();
        if (nonOptionArgs.size() == 1) {
            String keyword = nonOptionArgs.get(0);
            String override = "false";
            String forceOption = "force";
            if (args.containsOption(forceOption)) {
                override = args.getOptionValues(forceOption).get(0);
            }
            downloader.download(keyword, Boolean.parseBoolean(override));
        } else {
            System.out.println(KEYWORD_COUNT_MESSAGE);
        }
    }

}

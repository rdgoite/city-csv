package de.goeuro.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

public class CsvDownloadRunner implements ApplicationRunner {

    public static final String MESSAGE_SUCCESS = "Download completed with no errors.";
    public static final String MESSAGE_KEYWORD_COUNT = "Expected exactly 1 keyword.";

    @Value("${csv.download.override:false}")
    private String defaultOverrideMode;

    private final CsvDownloader downloader;

    public CsvDownloadRunner(CsvDownloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> nonOptionArgs = args.getNonOptionArgs();
        if (nonOptionArgs.size() == 1) {
            String keyword = nonOptionArgs.get(0);
            String override = defaultOverrideMode;
            String forceOption = "force";
            if (args.containsOption(forceOption)) {
                override = args.getOptionValues(forceOption).get(0);
            }
            downloader.download(keyword, Boolean.parseBoolean(override));
            System.out.println(MESSAGE_SUCCESS);
        } else {
            System.out.println(MESSAGE_KEYWORD_COUNT);
        }
    }

}

package de.goeuro.demo.csvdownloader;

import de.goeuro.demo.CsvDownloader;
import de.goeuro.demo.LocationService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={CsvDownloaderConfiguration.class})
public class CsvDownloaderTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private CsvDownloader downloader;

    @Rule
    public TemporaryFolder outputFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outputFolder.newFolder("output");
    }

    @Test
    public void testDownloadCsv() {
        //expect:
        assertNotNull(locationService);
        assertNotNull(downloader);
    }

}

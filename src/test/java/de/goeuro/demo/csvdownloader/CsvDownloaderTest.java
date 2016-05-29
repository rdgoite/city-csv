package de.goeuro.demo.csvdownloader;

import de.goeuro.demo.CsvDownloader;
import de.goeuro.demo.Location;
import de.goeuro.demo.LocationService;
import de.goeuro.demo.PositionSuggestion;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={CsvDownloaderConfiguration.class})
public class CsvDownloaderTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private CsvDownloader downloader;

    private Set<String> temporaryFiles = new HashSet<>();

    @After
    public void cleanUp() {
        for (String fileName : temporaryFiles) {
            new File(fileName).delete();
        }
        temporaryFiles.clear();
    }

    @Test
    public void testDownload() throws Exception {
        //given:
        Location berlin = mock(Location.class);
        doReturn("123,Berlin,location,12.3,45.6").when(berlin).toCsv();

        //and:
        Location berlingerode = mock(Location.class);
        doReturn("777,Berlingerode,location,98.7,65.4").when(berlingerode).toCsv();

        //and:
        String searchKey = "Berlin";
        PositionSuggestion suggestion = new PositionSuggestion(asList(berlin, berlingerode));
        doReturn(suggestion).when(locationService).suggestPosition(searchKey);

        //when:
        downloader.download(searchKey);

        //then:
        String expectedFileName = "location.csv";
        File outputFile = new File(expectedFileName);
        assertTrue(outputFile.exists());
        temporaryFiles.add(expectedFileName);
    }

}

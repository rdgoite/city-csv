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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={CsvDownloaderConfiguration.class})
public class CsvDownloaderTest {

    private static final String OUTPUT_FILE_NAME = "location.csv";

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
        List<String> csvOutput = asList("123,Berlin,location,12.3,45.6",
                "777,Berlingerode,location,98.7,65.4");

        Location berlin = mock(Location.class);
        doReturn(csvOutput.get(0)).when(berlin).toCsv();

        //and:
        Location berlingerode = mock(Location.class);
        doReturn(csvOutput.get(1)).when(berlingerode).toCsv();

        //and:
        String searchKey = "Berlin";
        PositionSuggestion suggestion = new PositionSuggestion(asList(berlin, berlingerode));
        doReturn(suggestion).when(locationService).suggestPosition(searchKey);

        //when:
        downloader.download(searchKey, false);

        //then:
        File outputFile = new File(OUTPUT_FILE_NAME);
        assertTrue(outputFile.exists());
        temporaryFiles.add(OUTPUT_FILE_NAME);

        //and:
        BufferedReader reader = new BufferedReader(new FileReader(outputFile));
        try {
            for (String expectedOutput : csvOutput) {
                assertTrue("No more lines to read from output file.", reader.ready());
                assertEquals(expectedOutput, reader.readLine());
            }
        } finally {
            reader.close();
        }
    }

    @Test
    public void testDownloadNoResults() throws Exception {
        //given:
        PositionSuggestion suggestion = new PositionSuggestion(emptyList());
        doReturn(suggestion).when(locationService).suggestPosition(anyString());

        //when:
        downloader.download("non existent", false);

        //then:
        File outputFile = new File(OUTPUT_FILE_NAME);
        assertTrue(outputFile.exists());
        temporaryFiles.add(OUTPUT_FILE_NAME);

        //and:
        BufferedReader reader = new BufferedReader(new FileReader(outputFile));
        try {
            assertTrue(reader.ready());
            assertEquals("# no results found", reader.readLine());
        } finally {
            reader.close();
        }
    }

    @Test(expected=RuntimeException.class)
    public void testDownloadOutputFileExists() throws Exception {
        //given:
        createOutputFile();

        //and:
        doReturn(mock(PositionSuggestion.class)).when(locationService).suggestPosition(anyString());

        //when:
        downloader.download("Munich", false);
    }

    @Test
    public void testDownloadOutputFileOverride() throws Exception {
        //given:
        createOutputFile();

        //and:
        PositionSuggestion suggestion = new PositionSuggestion(emptyList());
        doReturn(suggestion).when(locationService).suggestPosition(anyString());

        //when:
        downloader.download("Leipzig", true);

        //then:
        File outputFile = new File(OUTPUT_FILE_NAME);
        assertTrue(outputFile.exists());

        //and:
        BufferedReader reader = new BufferedReader(new FileReader(outputFile));
        try {
            assertTrue(reader.ready());
            assertEquals("# no results found", reader.readLine());
        } finally {
            reader.close();
        }
    }

    private void createOutputFile() throws IOException {
        File outputFile = new File(OUTPUT_FILE_NAME);
        outputFile.createNewFile();
        temporaryFiles.add(OUTPUT_FILE_NAME);
    }

}

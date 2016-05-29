package de.goeuro.demo.csvdownloader;

import de.goeuro.demo.CsvDownloadRunner;
import de.goeuro.demo.CsvDownloader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.ApplicationArguments;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CsvDownloadRunnerTest {

    @Mock
    private CsvDownloader downloader;

    private CsvDownloadRunner runner;

    @Before
    public void setUp() {
        initMocks(this);
        runner = new CsvDownloadRunner(downloader);
    }

    @Test
    public void testRun() throws Exception {
        //given:
        String searchKey = "Berlin";

        //and:
        ApplicationArguments arguments = mock(ApplicationArguments.class);
        doReturn(asList(searchKey)).when(arguments).getNonOptionArgs();

        //when:
        runner.run(arguments);

        //then:
        verify(downloader).download(searchKey, false);
    }

    @Test
    public void testRunOverride() throws Exception {
        //given:
        String searchKey = "Hamburg";

        //and:
        ApplicationArguments arguments = mock(ApplicationArguments.class);
        doReturn(true).when(arguments).containsOption("force");
        doReturn(asList("true")).when(arguments).getOptionValues("force");
        doReturn(asList(searchKey)).when(arguments).getNonOptionArgs();

        //when:
        runner.run(arguments);

        //then:
        verify(downloader).download(searchKey, true);
    }

}

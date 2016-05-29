package de.goeuro.demo.csvdownloader;

import de.goeuro.demo.CsvDownloadRunner;
import de.goeuro.demo.CsvDownloader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.OutputCapture;

import static de.goeuro.demo.CsvDownloadRunner.KEYWORD_COUNT_MESSAGE;
import static de.goeuro.demo.CsvDownloadRunner.MESSAGE_SUCCESS;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CsvDownloadRunnerTest {

    @Mock
    private CsvDownloader downloader;

    private CsvDownloadRunner runner;

    @Rule
    public OutputCapture consoleOutput = new OutputCapture();

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

        //and:
        consoleOutput.expect(is(MESSAGE_SUCCESS + "\n"));
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

        //and:
        consoleOutput.expect(is(MESSAGE_SUCCESS + "\n"));
    }

    @Test
    public void testRunNoKeyword() throws Exception {
        //given:
        ApplicationArguments arguments = mock(ApplicationArguments.class);

        //when:
        runner.run(arguments);

        //then:
        verify(downloader, never()).download(anyString(), anyBoolean());

        //and:
        consoleOutput.expect(is(KEYWORD_COUNT_MESSAGE + "\n"));
    }

    @Test
    public void testRunMoreThanOneKeyword() throws Exception {
        //given:
        ApplicationArguments arguments = mock(ApplicationArguments.class);
        doReturn(asList("Berlin", "Leipzig")).when(arguments).getNonOptionArgs();

        //when:
        runner.run(arguments);

        //then:
        consoleOutput.expect(is(KEYWORD_COUNT_MESSAGE + "\n"));
    }

}

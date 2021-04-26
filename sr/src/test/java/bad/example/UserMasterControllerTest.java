package bad.example;

import bad.example.http.HttpAdapter;
import bad.example.repository.RegionAmapMapperCustom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMasterControllerTest {

    @InjectMocks
    private UserMasterController userMasterController;
    @Mock
    private RegionAmapMapperCustom regionAmapMapperCustom;
    @Mock
    private HttpAdapter httpAdapter;

    @Test
    public void getMasterList() throws IOException {
        when(httpAdapter.doGet(any(), any())).thenReturn(getFakeResponse());
        userMasterController.getMasterList();
    }

    private InputStream getFakeResponse() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("valid_response.json").getFile());
        return new ByteArrayInputStream(Files.readString(Path.of(file.getAbsolutePath())).getBytes());
    }
}
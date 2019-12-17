package hu.jtechlog.cities;

//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockserver.client.MockServerClient;
//import org.mockserver.integration.ClientAndServer;
//import org.mockserver.model.HttpRequest;
//import org.mockserver.model.HttpResponse;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.util.SocketUtils;
//
//import java.io.IOException;
//import java.nio.file.Files;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class TemperatureServiceMockServerIT {

//    static ClientAndServer mockServer;
//
//    static String host = "127.0.0.1";
//
//    static int port;
//
//    @BeforeAll
//    static void startServer() {
//        System.out.println("Starting...");
//        port = SocketUtils.findAvailableTcpPort();
//        mockServer = startClientAndServer(port);
//        System.out.println("Started...");
//    }
//
//    @AfterAll
//    static void stopServer() {
//        mockServer.stop();
//    }
//
//    @Test
//    public void testTemperature() {
//        new MockServerClient(host, port)
//                .when(HttpRequest.request())
//                .respond(
//                        HttpResponse.response(readFile("/idokep.html"))
//                );
//
//        String url = String.format("http://%s:%d/", host, port);
//        TemperatureService temperatureService = new TemperatureService(url);
//        String temperature = temperatureService.temperature("Dobogókő");
//        assertEquals("8°C", temperature);
//
//    }
//
//    private String readFile(String name) {
//        try {
//            var path = new ClassPathResource(name).getFile().toPath();
//            return
//                    Files.readString(path);
//        }
//        catch (IOException ioe) {
//            throw new IllegalStateException("Can not read file", ioe);
//        }
//    }
}

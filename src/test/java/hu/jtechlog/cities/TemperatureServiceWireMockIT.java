package hu.jtechlog.cities;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SocketUtils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemperatureServiceWireMockIT {

    static String host = "127.0.0.1";

    static int port;

    static WireMockServer wireMockServer;

    @BeforeAll
    static void startServer() {
        port = SocketUtils.findAvailableTcpPort();
        wireMockServer = new WireMockServer(wireMockConfig().port(port));
        WireMock.configureFor(host, port);
        wireMockServer.start();
    }

    @AfterAll
    static void stopServer() {
        wireMockServer.stop();
    }

    @BeforeEach
    void resetServer() {
        reset();
    }

    @Test
    void testGetTemperature() {
        stubFor(get(urlEqualTo("/Budapest"))
                .willReturn(aResponse().withBodyFile("idokep.html")));

        var url = String.format("http://%s:%d/", host, port);
        TemperatureService temperatureService = new TemperatureService(url);
        var temperature = temperatureService.getTemperature("Budapest");
        assertEquals("8°C", temperature);
    }
}

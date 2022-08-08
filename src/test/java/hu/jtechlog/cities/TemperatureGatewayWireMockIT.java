package hu.jtechlog.cities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {"cities.idokep.url=http://localhost:${wiremock.server.port}/idokep.html#"})
@AutoConfigureWireMock(port = 0, files = "*.html")
class TemperatureGatewayWireMockIT {

    @Autowired
    TemperatureGateway temperatureGateway;

    @Test
    void testGetTemperature() {
        var temperature = temperatureGateway.getTemperature("Budapest");
        assertEquals("8°C", temperature);
    }
}

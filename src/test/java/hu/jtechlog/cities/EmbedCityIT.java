package hu.jtechlog.cities;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.util.SocketUtils;
import org.springframework.web.context.WebApplicationContext;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;


@SpringBootTest(properties = "")
public class EmbedCityIT {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    Environment environment;

    static String host = "127.0.0.1";

    static int port;

    static WireMockServer wireMockServer;

    @BeforeAll
    static void startServer() {
        port = SocketUtils.findAvailableTcpPort();
        wireMockServer = new WireMockServer(wireMockConfig().port(port));
        WireMock.configureFor(host, port);
        wireMockServer.start();

        var url = String.format("http://%s:%d/", host, port);
        System.setProperty("cities.idokep.url", url);
    }

    @AfterAll
    static void stopServer() {
        wireMockServer.stop();
        System.clearProperty("cities.idokep.url");
    }

    @BeforeEach
    void init() {
        RestAssuredMockMvc.requestSpecification = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @BeforeEach
    void resetServer() {
        reset();
    }

    @Test
    void testGetTemperature() {
        stubFor(get(urlEqualTo("/Debrecen"))
                .willReturn(aResponse().withBodyFile("idokep.html")));

        when()
                .get("/api/cities/Debrecen")
                .then()
                .body("name", Matchers.equalTo("Debrecen"))
                .body("temperature", Matchers.equalTo("8°C"));


    }

}

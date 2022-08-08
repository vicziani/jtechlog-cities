package hu.jtechlog.cities;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.web.context.WebApplicationContext;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;


@SpringBootTest(properties = {"cities.idokep.url=http://localhost:${wiremock.server.port}/idokep.html#"})
@AutoConfigureWireMock(port = 0, files = "*.html")
public class EmbedCityIT {

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void init() {
        RestAssuredMockMvc.requestSpecification = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
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

package hu.jtechlog.cities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@WebAppConfiguration
class CitiesApplicationIT {

	@Autowired
	WebApplicationContext context;

	@MockBean
	TemperatureGateway temperatureGateway;

	@Test
	void testGetCity() {
		// RestAssured alapesetben float-ként olvassa be a JSON-ben lévő számot, így a CloseTo nem működik rá,
		// ezért kell extract-ot hívni
		when(temperatureGateway.getTemperature(any())).thenReturn("20");

		double lat = given()
			.webAppContextSetup(context)
		.when()
			.get("/api/cities/Debrecen")
		.then()
			.statusCode(200)
			.body("name", equalTo("Debrecen"))
		.extract().jsonPath().getDouble("lat");

		assertThat(lat, closeTo(47.52883333, 0.000005));
	}

}

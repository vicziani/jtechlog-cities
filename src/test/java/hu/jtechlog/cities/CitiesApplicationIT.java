package hu.jtechlog.cities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@WebAppConfiguration
class CitiesApplicationIT {

	@Autowired
	WebApplicationContext context;

	@Test
	void testGetCity() {
		// RestAssured alapesetben float-ként olvassa be a JSON-ben lévő számot, így a CloseTo nem működik rá,
		// ezért kell extract-ot hívni

		double lat = given()
			.webAppContextSetup(context)
		.when()
				// Ékezetessel nem megy
			.get("/api/cities/Debrecen")
		.then()
			.statusCode(200)
			.body("name", equalTo("Debrecen"))
		.extract().jsonPath().getDouble("lat");

		assertThat(lat, closeTo(47.52883333, 0.000005));
	}

}

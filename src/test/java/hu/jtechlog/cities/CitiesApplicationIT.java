package hu.jtechlog.cities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@WebAppConfiguration
class CitiesApplicationIT {

	@Autowired
	WebApplicationContext context;

	@Test
	void testGetCity() {
		given()
			.webAppContextSetup(context)
		.when()
				// Ékezetessel nem megy
			.get("/api/cities/Budapest")
		.then()
			.statusCode(200)
			.body("name", equalTo("Budapest"))
			.body("lat", equalTo(47.4825))
			;
	}

}

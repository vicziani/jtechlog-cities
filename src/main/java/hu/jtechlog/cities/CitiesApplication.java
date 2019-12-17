package hu.jtechlog.cities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class CitiesApplication {

	@Value("${cities.idokep.url}")
	private String url;

	public static void main(String[] args) {
		SpringApplication.run(CitiesApplication.class, args);
	}

	@Bean
	public TemperatureService temperatureService() {
		return new TemperatureService(url);
	}

}

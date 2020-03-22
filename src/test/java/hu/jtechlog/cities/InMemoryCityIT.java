package hu.jtechlog.cities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
@AutoConfigureMockMvc
public class InMemoryCityIT {

    @MockBean
    CityRepository cityRepository;

    @MockBean
    TemperatureGateway temperatureGateway;

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_getCity() throws Exception {
        when(cityRepository.findByName(anyString())).thenReturn(
                Optional.of(new City(1L, "Debrecen",47.52883333,21.63716667)));
        when(temperatureGateway.getTemperature(anyString())).thenReturn("8°C");

        mockMvc.perform(get("/api/cities/Debrecen"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.temperature", equalTo("8°C")));
    }
}

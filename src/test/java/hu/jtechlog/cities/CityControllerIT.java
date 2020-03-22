package hu.jtechlog.cities;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest
public class CityControllerIT {

    @Autowired
    CityController cityController;

    @MockBean
    CityService cityService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_getCity() throws Exception {
        when(cityService.getCityDetails(any())).thenReturn(
                new CityDetails("Debrecen",47.52883333,21.63716667, 10, Optional.of("8°C")));

        mockMvc.perform(get("/api/cities/Debrecen"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.temperature", equalTo("8°C")));

    }
}

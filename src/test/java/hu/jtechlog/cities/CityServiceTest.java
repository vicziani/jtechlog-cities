package hu.jtechlog.cities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    CityRepository cityRepository;

    @Mock
    TemperatureService temperatureService;

    @InjectMocks
    CityService cityService;

    @Test
    void testGetCityDetails() {
        when(cityRepository.findByName(eq("Budapest"))).thenReturn(Optional.of(new City(1L, "Budapest", 47.4825,19.15933333)));
        when(cityRepository.findByName(eq("Páty"))).thenReturn(Optional.of(new City(1L, "Páty", 47.5165,18.82816667)));

        when(temperatureService.getTemperature(anyString())).thenReturn("8");

        var cityDetails = cityService.getCityDetails("Páty").get();
        assertAll(
                () -> assertEquals("Páty", cityDetails.getName()),
                () -> assertEquals(47.5165, cityDetails.getLat()),
                () -> assertEquals(18.82816667, cityDetails.getLon()),
                () -> assertTrue(cityDetails.getDistance() > 0),
                () -> assertEquals(Optional.of("8"), cityDetails.getTemperature())
                );
    }

    @Test
    void testGetCityDetailsMissingCity() {
        var cityDetails = cityService.getCityDetails("Páty");
        assertTrue(cityDetails.isEmpty());
    }

    @Test
    void testGetCityDetailsMissingBase() {
        when(cityRepository.findByName(eq("Páty"))).thenReturn(Optional.of(new City(1L, "Páty", 47.5165,18.82816667)));
        assertThrows(IllegalStateException.class, () -> cityService.getCityDetails("Páty"));
    }

    @Test
    void testGetCityDetailsWhenGetTemperatureThrowsException() {
        when(temperatureService.getTemperature(anyString())).thenThrow(new IllegalStateException());
        when(cityRepository.findByName(eq("Budapest"))).thenReturn(Optional.of(new City(1L, "Budapest", 47.4825,19.15933333)));
        when(cityRepository.findByName(eq("Páty"))).thenReturn(Optional.of(new City(1L, "Páty", 47.5165,18.82816667)));

        var cityDetails = cityService.getCityDetails("Páty").get();

        assertEquals(Optional.empty(), cityDetails.getTemperature());
    }
}

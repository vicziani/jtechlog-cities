package hu.jtechlog.cities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    CityRepository cityRepository;

    @Mock
    TemperatureGateway temperatureGateway;

    @Mock
    HaversineCalculator haversineCalculator;

    @InjectMocks
    CityService cityService;

    @Test
    void testGetCityDetails() {
        when(cityRepository.findByName(eq("Budapest"))).thenReturn(Optional.of(new City(1L, "Budapest", 47.4825,19.15933333)));
        when(cityRepository.findByName(eq("Debrecen"))).thenReturn(Optional.of(new City(1L, "Debrecen",47.52883333,21.63716667)));
        when(haversineCalculator.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(10.0);

        when(temperatureGateway.getTemperature(anyString())).thenReturn("8°C");

        var cityDetails = cityService.getCityDetails("Debrecen");
        assertAll(
                () -> assertEquals("Debrecen", cityDetails.getName()),
                () -> assertEquals(47.52883333, cityDetails.getLat()),
                () -> assertEquals(21.63716667, cityDetails.getLon()),
                () -> assertEquals(10.0, cityDetails.getDistance()),
                () -> assertEquals(Optional.of("8°C"), cityDetails.getTemperature())
                );
    }

    @Test
    void testGetCityDetailsMissingCity() {
        assertThrows(CityNotFoundException.class, () -> {
            cityService.getCityDetails("Páty");
        });
    }

    @Test
    void testGetCityDetailsMissingBase() {
        when(cityRepository.findByName(eq("Debrecen"))).thenReturn(Optional.of(new City(1L, "Debrecen",47.52883333,21.63716667)));
        assertThrows(IllegalStateException.class, () -> cityService.getCityDetails("Debrecen"));
    }

    @Test
    void testGetCityDetailsWhenGetTemperatureThrowsException() {
        when(temperatureGateway.getTemperature(anyString())).thenThrow(new IllegalStateException());
        when(cityRepository.findByName(eq("Budapest"))).thenReturn(Optional.of(new City(1L, "Budapest", 47.4825,19.15933333)));
        when(cityRepository.findByName(eq("Debrecen"))).thenReturn(Optional.of(new City(1L, "Debrecen",47.52883333,21.63716667)));

        var cityDetails = cityService.getCityDetails("Debrecen");

        assertEquals(Optional.empty(), cityDetails.getTemperature());
    }
}

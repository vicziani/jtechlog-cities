package hu.jtechlog.cities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CityService {

    private static final String BASE_NAME = "Budapest";

    private CityRepository cityRepository;

    private HaversineCalculator haversineCalculator;

    private TemperatureGateway temperatureGateway;

    public CityService(CityRepository cityRepository, HaversineCalculator haversineCalculator, TemperatureGateway temperatureGateway) {
        this.cityRepository = cityRepository;
        this.haversineCalculator = haversineCalculator;
        this.temperatureGateway = temperatureGateway;
    }

    public CityDetails getCityDetails(String nameOfTheCity) {
        var mayBeCity = cityRepository.findByName(nameOfTheCity);
        if (mayBeCity.isEmpty()) {
            throw new CityNotFoundException("City not found with name: " + nameOfTheCity);
        }
        var city = mayBeCity.get();
        var distance = calculateDistance(city);
        var temperature = getTemperature(nameOfTheCity);

        return new CityDetails(city.getName(), city.getLat(), city.getLon(), distance, temperature);
    }

    private double calculateDistance(City city) {
        var base = cityRepository.findByName(BASE_NAME)
                .orElseThrow(() -> new IllegalStateException("Invalid base city: " + BASE_NAME));
        return haversineCalculator.calculateDistance(city.getLat(), city.getLon(), base.getLat(), base.getLon());
    }

    private Optional<String> getTemperature(String nameOfTheCity) {
        try {
            return Optional.of(temperatureGateway.getTemperature(nameOfTheCity));
        }
        catch (IllegalStateException ise) {
            log.debug("Can not get temperature", ise);
            return Optional.empty();
        }
    }
}

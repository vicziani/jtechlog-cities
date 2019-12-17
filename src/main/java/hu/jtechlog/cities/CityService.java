package hu.jtechlog.cities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CityService {

    private static final String BASE_NAME = "Budapest";

    private CityRepository cityRepository;

    private TemperatureService temperatureService;

    public CityService(CityRepository cityRepository, TemperatureService temperatureService) {
        this.cityRepository = cityRepository;
        this.temperatureService = temperatureService;
    }

    public Optional<CityDetails> getCityDetails(String nameOfTheCity) {
        var mayBeCity = cityRepository.findByName(nameOfTheCity);
        if (mayBeCity.isEmpty()) {
            return Optional.empty();
        }
        var city = mayBeCity.get();
        var distance = calculateDistance(city);
        var temperature = getTemperature(nameOfTheCity);

        return Optional.of(new CityDetails(city.getName(), city.getLat(), city.getLon(), distance, temperature));
    }

    private double calculateDistance(City city) {
        var base = cityRepository.findByName(BASE_NAME)
                .orElseThrow(() -> new IllegalStateException("Invalid city: " + BASE_NAME));
        return Haversine.distance(city.getLat(), city.getLon(), base.getLat(), base.getLon());
    }

    private Optional<String> getTemperature(String nameOfTheCity) {
        try {
            return Optional.of(temperatureService.getTemperature(nameOfTheCity));
        }
        catch (IllegalStateException ise) {
            log.debug("Can not get temperature", ise);
            return Optional.empty();
        }
    }
}

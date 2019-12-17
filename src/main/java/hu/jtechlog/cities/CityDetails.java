package hu.jtechlog.cities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class CityDetails {

    private String name;

    private double lat;

    private double lon;

    private double distance;

    private Optional<String> temperature;
}

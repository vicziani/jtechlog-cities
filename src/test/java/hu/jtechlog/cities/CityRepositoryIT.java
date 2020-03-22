package hu.jtechlog.cities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CityRepositoryIT {

    @Autowired
    CityRepository cityRepository;

    @Test
    void test_findByName() {
        var city = cityRepository.findByName("Budapest");
        assertEquals(47.4825, city.get().getLat());
    }
}

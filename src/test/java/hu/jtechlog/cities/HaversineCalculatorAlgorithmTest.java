package hu.jtechlog.cities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HaversineCalculatorAlgorithmTest {

    @Test
    void testDistance() {
        assertEquals(14.97319048158622,
                new HaversineCalculator().calculateDistance(47.6788206, -122.3271205, 47.6788206, -122.5271205),
                0.000000000000000000005);
    }
}

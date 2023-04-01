package UnitTests;

import Simulation.Environment.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationTest {

    @Test
    public void testEquals() {
        Location location0 = new Location(0, 0);
        Location location1 = new Location(0, 0);
        assertEquals(location0, location1);
    }
}

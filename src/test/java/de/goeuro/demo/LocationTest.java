package de.goeuro.demo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocationTest {

    @Test
    public void testToCsv() {
        //given:
        Location berlinAirport = new Location(789L, "airport", "Berlin Sch\u00f6nefeld", 52.3887261, 13.5180874);

        //expect:
        assertEquals("airport,Berlin Sch\u00f6nefeld,52.388,13.5180874", berlinAirport.toCsv());
    }

}

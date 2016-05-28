package de.goeuro.demo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocationTest {

    @Test
    public void testToCsv() {
        //given:
        Location schonefeld = new Location(314827L, "airport", "Berlin Sch\u00f6nefeld", 52.3887261, 13.5180874);
        Location berlingerode = new Location(425332L, "location", "Berlingerode", 51.45775, 10.2384);

        //expect:
        assertEquals("314827,airport,Berlin Sch\u00f6nefeld,52.3887261,13.5180874", schonefeld.toCsv());
        assertEquals("425332,location,Berlingerode,51.45775,10.2384", berlingerode.toCsv());
    }

}

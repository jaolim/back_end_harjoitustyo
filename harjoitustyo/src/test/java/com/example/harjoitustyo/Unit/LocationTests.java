package com.example.harjoitustyo.Unit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.Location;
import com.example.harjoitustyo.domain.Region;

@SpringBootTest
public class LocationTests {

    @Test
    public void shouldCreateLocation (){
        Region region = new Region("testRegion",
                "testRegionDescription",
                "testRegionImage");
        City city = new City("testCity", 1, 1, region);

        Location location = new Location("testLocation", city);

        assertTrue(location.getCity().getRegion().getName().equals("testRegion"));
    }

}

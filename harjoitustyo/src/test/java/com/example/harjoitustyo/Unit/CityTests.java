package com.example.harjoitustyo.Unit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.Region;

@SpringBootTest
public class CityTests {

    @Test
    public void shouldCreateCity (){
        Region region = new Region("testRegion",
                "testRegionDescription",
                "testRegionImage");
        City city = new City("testCity", 1, 1, region);

        assertTrue(city.getRegion().getName().equals("testRegion"));
    }

}

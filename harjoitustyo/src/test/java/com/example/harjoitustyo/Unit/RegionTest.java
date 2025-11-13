package com.example.harjoitustyo.Unit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.harjoitustyo.domain.Region;

@SpringBootTest
public class RegionTest {

    @Test
    public void shouldCreateNewRegions() {

        Region nameOnly = new Region("name");
        assertTrue(nameOnly.getDescription() == null);
        Region withDescription = new Region("Pirkanmaa",
                "Vibrant region centered on Tampere, mixing industry, culture, and lake scenery.");
        assertTrue(withDescription.getImage() == null);
        Region all = new Region("Uusimaa",
                "Finland’s most populous region, including Helsinki; urban, international, and coastal.",
                "test");
        assertTrue(all.getImage().equals("test"));

    }

    @Test
    void shouldChangeAttribute() {
        Region region = new Region("name");
        region.setDescription("description");
        assertTrue(region.getDescription().equals("description"));
        region.setImage("image");
        assertTrue(region.getImage().equals("image"));
    }

}

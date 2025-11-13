package com.example.harjoitustyo.Unit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.harjoitustyo.domain.AppUser;
import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.Comment;
import com.example.harjoitustyo.domain.Location;
import com.example.harjoitustyo.domain.Region;

@SpringBootTest
public class CommentTests {

    @Test
    public void shouldCreateComment() {
        Region region = new Region("testRegion",
                "testRegionDescription",
                "testRegionImage");
        City city = new City("testCity", 1, 1, region);

        Location location = new Location("testLocation", city);

        AppUser appUser = new AppUser("testAppUser",
                "testpasswordHash",
                "ADMIN",
                "testFirstname", "testLastname");

        Comment comment = new Comment("testHeadline", "testBody", location, appUser);

        assertTrue(comment.getAppUser().getUsername().equals("testAppUser"));
        assertTrue(comment.getLocation().getCity().getRegion().getName().equals("testRegion"));
    }

}
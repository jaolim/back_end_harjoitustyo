package com.example.harjoitustyo.Unit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.harjoitustyo.domain.AppUser;


@SpringBootTest
public class AppUserTests {

        @Test
        public void shouldCreateNewAppUsers() {
                AppUser appUser1 = new AppUser("testaaja1",
                                "$2a$10$zNXa3MgyyUslVl.jL8950eGswQKEBKFVkSXlvghOPigjUcKXsasbK",
                                "ADMIN",
                                "adminTest", "userTestLast");
                assertTrue(appUser1.getUsername().equals("testaaja1"));
                AppUser appUser2 = new AppUser("testaaja2",
                                "$2a$10$zNXa3MgyyUslVl.jL8950eGswQKEBKFVkSXlvghOPigjUcKXsasbK",
                                "CUSTOM",
                                "adminTest");
                assertTrue(appUser2.getUsername().equals("testaaja2"));
                AppUser appUser3 = new AppUser("testaaja3",
                                "$2a$10$zNXa3MgyyUslVl.jL8950eGswQKEBKFVkSXlvghOPigjUcKXsasbK",
                                "USER");
                assertTrue(appUser3.getUsername().equals("testaaja3"));

        }

        @Test
        public void shouldChangeAttributes() {
                AppUser appUser1 = new AppUser("testaaja1",
                                "$2a$10$zNXa3MgyyUslVl.jL8950eGswQKEBKFVkSXlvghOPigjUcKXsasbK",
                                "ADMIN",
                                "adminTest", "userTestLast");
                appUser1.setUsername("newName");
                assertTrue(appUser1.getUsername().equals("newName"));
                appUser1.setPasswordHash("newHash");
                assertTrue(appUser1.getPasswordHash().equals("newHash"));
                appUser1.setUserRole("newRole");
                assertTrue(appUser1.getUserRole().equals("newRole"));
                appUser1.setFirstname("newFirst");
                assertTrue(appUser1.getFirstname().equals("newFirst"));
                appUser1.setLastname("newLast");
                assertTrue(appUser1.getLastname().equals("newLast"));
        }

}
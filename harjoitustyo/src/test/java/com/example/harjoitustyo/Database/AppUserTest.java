package com.example.harjoitustyo.Database;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.harjoitustyo.domain.AppUser;
import com.example.harjoitustyo.domain.AppUserRepository;

@SpringBootTest
public class AppUserTest {

        @Autowired
        private AppUserRepository auRepository;

        @BeforeEach
        public void setup() {

                AppUser appUser1 = new AppUser("admin", "$2a$10$zNXa3MgyyUslVl.jL8950eGswQKEBKFVkSXlvghOPigjUcKXsasbK",
                                "ADMIN",
                                "adminTest", "userTestLast");
                AppUser appUser2 = new AppUser("user", "$2a$10$YxQApzp3QttDxZO2Mx1BbeyQHH9YJaLDMciTk0zI/yiULbTJVZq/C",
                                "USER",
                                "userTest", "userTestLast");
                AppUser appUser3 = new AppUser("erkki", "$2a$10$DS/qbwxu.pxDNcvaFC4IS.00fnAovXseaUnqu/ccE5DBBoLpWUile",
                                "USER",
                                "Erkki", "Esimerkki");
                AppUser appUser4 = new AppUser("pekka", "$2a$10$a1O0jjIa9HUB7Zr1rMkmY.qU0OuPj9XRlkCEoiS6CtGYcDWi3f2rK",
                                "USER",
                                "Pekka");
                AppUser appUser5 = new AppUser("maintenance",
                                "$2a$10$WUfrNQVubEAoZdPE/D8Qkue2ApFz4DzXlR3AUop/RYYG9u1Xp1dfy", "ADMIN");

                auRepository.save(appUser1);
                auRepository.save(appUser2);
                auRepository.save(appUser3);
                auRepository.save(appUser4);
                auRepository.save(appUser5);

        }

        @AfterEach
        public void cleanup() {
                auRepository.deleteAll();
        }

        @Test
        public void shouldSaveNewAppUser() {
                AppUser appUser1 = new AppUser("testaaja",
                                "$2a$10$zNXa3MgyyUslVl.jL8950eGswQKEBKFVkSXlvghOPigjUcKXsasbK",
                                "ADMIN",
                                "adminTest", "userTestLast");

                auRepository.save(appUser1);

                assertTrue(auRepository.existsByUsername("testaaja"));

        }

}

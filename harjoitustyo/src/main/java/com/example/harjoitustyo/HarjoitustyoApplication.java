package com.example.harjoitustyo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.harjoitustyo.domain.AppUser;
import com.example.harjoitustyo.domain.AppUserRepository;
import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.CityRepository;
import com.example.harjoitustyo.domain.Comment;
import com.example.harjoitustyo.domain.CommentRepository;
import com.example.harjoitustyo.domain.Location;
import com.example.harjoitustyo.domain.LocationRepository;
import com.example.harjoitustyo.domain.Region;
import com.example.harjoitustyo.domain.RegionRepository;

@SpringBootApplication
public class HarjoitustyoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarjoitustyoApplication.class, args);
	}

	@Bean
	public CommandLineRunner harjoitustyo(RegionRepository rRepository, CityRepository cRepository,
			LocationRepository lRepository, AppUserRepository auRepository, CommentRepository coRepository) {

		return (args) -> {
			if (!auRepository.existsByUsername("admin")) {
				Region rUusimaa = new Region("Uusimaa",
						"Finland’s most populous region, including Helsinki; urban, international, and coastal.",
						"https://upload.wikimedia.org/wikipedia/commons/f/f9/Uusimaa.vaakuna.svg");
				Region rPirkanmaa = new Region("Pirkanmaa",
						"Vibrant region centered on Tampere, mixing industry, culture, and lake scenery.",
						"https://upload.wikimedia.org/wikipedia/commons/c/c3/Pirkanmaa.vaakuna.svg");

				City cAkaa = new City("Akaa", 16402, 293.29,
						"Small Pirkanmaa town with lakes, industry, and peaceful rural surroundings.", rPirkanmaa,
						"https://upload.wikimedia.org/wikipedia/commons/5/52/Akaa.vaakuna.svg");
				City cHelsinki = new City("Helsinki", 689758, 214.58,
						"Finland’s capital, combining coastal beauty, culture, and international life.",
						rUusimaa,
						"https://upload.wikimedia.org/wikipedia/commons/c/c4/Helsinki.vaakuna.svg");
				Location location1 = new Location("Central Railway Station", "The main railway station.", cHelsinki, "https://upload.wikimedia.org/wikipedia/commons/e/ee/Helsinki_Central_railway_station_in_Finland%2C_2021_July.jpg");
				Location location2 = new Location(
						"Suomenlinna Fortress",
						"A historic sea fortress reachable by ferry, popular for walks and picnics.",
						cHelsinki);

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
				Comment comment1 = new Comment("Statues", "Remember to check the pillar statues outside as you exit!",
						location1, appUser1);
				Comment comment2 = new Comment(
						"Beautiful Views",
						"Suomenlinna offers stunning seaside views — perfect place for taking photos.",
						location1,
						appUser3);
				Comment comment3 = new Comment(
						"Bring Snacks",
						"There are cafés but they can get crowded. A small picnic works great here.",
						location1,
						appUser1);
				Comment comment4 = new Comment(
						"Majestic Building",
						"The cathedral is even more impressive up close. A must-see for any visitor.",
						location2,
						appUser4);

				Comment comment5 = new Comment(
						"Great Spot for Sunrise",
						"Arrived early and watched the sun hit the cathedral steps. Absolutely worth it.",
						location2,
						appUser2);

				rRepository.save(rPirkanmaa);
				rRepository.save(rUusimaa);
				cRepository.save(cAkaa);
				cRepository.save(cHelsinki);
				lRepository.save(location1);
				lRepository.save(location2);
				auRepository.save(appUser1);
				auRepository.save(appUser2);
				auRepository.save(appUser3);
				auRepository.save(appUser4);
				auRepository.save(appUser5);
				coRepository.save(comment1);
				coRepository.save(comment2);
				coRepository.save(comment3);
				coRepository.save(comment4);
				coRepository.save(comment5);
			}
		};
	}

}

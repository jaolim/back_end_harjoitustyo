package com.example.harjoitustyo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
				Region region1 = new Region("Cmd: Test Region 1", "A region of test", "");
				Region region2 = new Region("Cmd: Test Region 2");
				Region rPirkanmaa = new Region("Pirkanmaa",
						"Vibrant region centered on Tampere, mixing industry, culture, and lake scenery.",
						"https://upload.wikimedia.org/wikipedia/commons/c/c3/Pirkanmaa.vaakuna.svg");

				City cAkaa = new City("Akaa", 16402, 293.29,
						"Small Pirkanmaa town with lakes, industry, and peaceful rural surroundings.", rPirkanmaa,
						"https://upload.wikimedia.org/wikipedia/commons/5/52/Akaa.vaakuna.svg");
				City city1 = new City("Cmd: Test City 1", 210542, 80.55, "A city in need is a city indeed", region1);
				String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin luctus lorem et erat malesuada efficitur. Praesent convallis accumsan risus, at feugiat diam feugiat ullamcorper. Mauris varius ligula eros, nec mattis dui laoreet vel.";
				City city2 = new City("Cmd: Test City 2", 55004, 65.55, lorem, region1);
				Location location1 = new Location("Cmd: Test Location 1", city1);
				Location location2 = new Location("Cmd: Test Location 2", "A lovely location indeed", city2);
				PasswordEncoder encoder = new BCryptPasswordEncoder();
				AppUser appUser1 = new AppUser("admin", encoder.encode("admin"), "adminTest", "userTestLast", "ADMIN");
				AppUser appUser2 = new AppUser("user", encoder.encode("user"), "userTest", "userTestLast", "USER");
				AppUser appUser3 = new AppUser("erkki", encoder.encode("erkki"), "Erkki", "Esimerkki", "USER");
				Comment comment1 = new Comment("Test headline", "A testful body of text", location1, appUser1);
				Comment comment2 = new Comment("Test headline", "A testful body of text", location2, appUser2);
				Comment comment3 = new Comment("Test headline", "A testful body of text", location1, appUser3);
				Comment comment4 = new Comment("Test headline", "A testful body of text", location1, appUser2);
				rRepository.save(rPirkanmaa);
				rRepository.save(region1);
				rRepository.save(region2);
				cRepository.save(cAkaa);
				cRepository.save(city1);
				cRepository.save(city2);
				lRepository.save(location1);
				lRepository.save(location2);
				auRepository.save(appUser1);
				auRepository.save(appUser2);
				auRepository.save(appUser3);
				coRepository.save(comment1);
				coRepository.save(comment2);
				coRepository.save(comment3);
				coRepository.save(comment4);
			}
		};
	}

}

package com.example.harjoitustyo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.harjoitustyo.domain.AppUser;
import com.example.harjoitustyo.domain.AppUserRepository;
import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.CityRepository;
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
			LocationRepository lRepository, AppUserRepository auRepository) {

		return (args) -> {
			Region region1 = new Region("Cmd: Test Region 1");
			Region region2 = new Region("Cmd: Test Region 2");
			City city1 = new City("Cmd: Test City 1", 210542, 80.55, "A city in need is a city indeed", region1);
			City city2 = new City("Cmd: Test City 2", 55004, 65.55, "To live here is a true test of character",
					region1);
			Location location1 = new Location("Cmd: Test Location 1", city1);
			Location location2 = new Location("Cmd: Test Location 2", "A lovely location indeed", city2);
			AppUser appUser1 = new AppUser("admin", "asdf", "userTest", "userTestLast", "ADMIN");
			AppUser appUser2 = new AppUser("user", "asdf", "userTest", "userTestLast", "USER");
			rRepository.save(region1);
			rRepository.save(region2);
			cRepository.save(city1);
			cRepository.save(city2);
			lRepository.save(location1);
			lRepository.save(location2);
			auRepository.save(appUser1);
			auRepository.save(appUser2);
		};
	}

}

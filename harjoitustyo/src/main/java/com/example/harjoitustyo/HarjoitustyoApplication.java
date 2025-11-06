package com.example.harjoitustyo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.harjoitustyo.domain.Region;
import com.example.harjoitustyo.domain.RegionRepository;

@SpringBootApplication
public class HarjoitustyoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarjoitustyoApplication.class, args);
	}

	@Bean
	public CommandLineRunner harjoitustyo(RegionRepository rRepository) {
		
		return (args) -> {
			Region region1 = new Region("Cmd: Test Region 1");
			Region region2 = new Region("Cmd: Test Region 2");
			rRepository.save(region1);
			rRepository.save(region2);
		};
	}

}

package com.imen.hotels;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.imen.hotels.service.HotelService;
import com.imen.hotels.entities.Hotel;
import com.imen.hotels.entities.TypeHotel;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;





@SpringBootApplication
public class HotelsApplication implements CommandLineRunner {
	
	@Autowired
	private HotelService hotelService;
	
	 //convertion auto
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);
		return modelMapper;
	}

	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer() {
		return new RepositoryRestConfigurer() {
			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
				config.exposeIdsFor(Hotel.class, TypeHotel.class);
			}
		};
	}

	
	@Override
	public void run(String... args) throws Exception {
		// Ton code existant pour les hôtels...
		if (hotelService.getAllHotels().isEmpty()) {
			System.out.println("=== CRÉATION DES HÔTELS DE DÉMONSTRATION ===");
			// Création des hôtels de démonstration...
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(HotelsApplication.class, args);
	}
}
package com.zacharyhughes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVReader;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class AirportServiceApplication {
	
	private static String filename = "airports.dat.txt";
	
	private static CSVReader reader;
	
	public static void main(String[] args) {
		try {
			reader = new CSVReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File " + filename + " could not be found.");
		}
		SpringApplication.run(AirportServiceApplication.class, args);
	}
	
	/**
	 * populate method populate's an in-memory H2 database with airline information from openflights.org
	 * @param repository
	 * @return
	 */
	@Bean
	public CommandLineRunner populate(AirportRepository repository) {
		return (args) -> {
			List<String[]> airports = reader.readAll();
			
			for (String[] airport : airports) {
				double lat = Double.parseDouble(airport[6]);
				double lng = Double.parseDouble(airport[7]);
				repository.save(new Airport(airport[4], airport[1], airport[2], airport[3], lat, lng));
				
			}
		};
	}
}

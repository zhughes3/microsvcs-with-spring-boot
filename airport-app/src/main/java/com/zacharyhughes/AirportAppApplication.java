package com.zacharyhughes;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class AirportAppApplication {
	
	@Inject
	private RestTemplate restTemplate;
	
	@Autowired
    @Qualifier("halJacksonHttpMessageConverter")
    private TypeConstrainedMappingJackson2HttpMessageConverter halJacksonHttpMessageConverter;

	public static void main(String[] args) {
		SpringApplication.run(AirportAppApplication.class, args);
	}
	
	@GetMapping
	public String getTime() {
		
		return restTemplate.getForEntity("http://distance-service", String.class).getBody();
	}
	
//	@RequestMapping("/airports")
//	public String getAirports() {
//		return restTemplate.getForEntity("http://airport-service", String.class).getBody();
//	}
	
	@RequestMapping("/airports")
	public Airport getAirport(@RequestParam(value="iata") String iata) {
		String url = String.format("http://airport-service/search/findByIata?iata=%s", iata);
		return restTemplate.getForEntity(url, Airport.class).getBody();
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> existingConverters = restTemplate.getMessageConverters();
		List<HttpMessageConverter<?>> newConverters = new ArrayList<>();
		newConverters.add(halJacksonHttpMessageConverter);
		newConverters.addAll(existingConverters);
		restTemplate.setMessageConverters(newConverters);
		return restTemplate;
	}
}

package com.zacharyhughes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Airport {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String city;
	private String country;
	private String iata;
	private double latitude;
	private double longitude;
	
	protected Airport() { } 
	
	public Airport(String iata, String name, String city, String country, double lat, double lng) {
		this.iata = iata;
		this.name = name;
		this.city = city;
		this.country = country;
		this.latitude = lat;
		this.longitude = lng;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getIata() {
		return iata;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public Long getId() {
		return id;
	}
	
	public String toString() {
		return this.name + ", " + this.city + ", " + this.country;
	}

}

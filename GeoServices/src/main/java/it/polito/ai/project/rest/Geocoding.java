package it.polito.ai.project.rest;

import java.util.HashMap;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import it.polito.ai.project.postgis.repo.BusStopsRepository;

@RestController
public class Geocoding {

	private static String google_geocoding_endpoint = "https://maps.googleapis.com/maps/api/geocode/json";
	// please if you are using this code, change the token or it will reach the
	// quota and won't work
	private static String google_geocoding_token = "AIzaSyCL8dY-vuMWMd6hb10aPPctsyenIMxPmi4";

	private RestTemplate restTemplate;
	// save the bounding box of the interested area to provide more results in
	// this rectangle
	private HashMap<String, Double> minLatLng;
	private HashMap<String, Double> maxLatLng;

	public Geocoding(@Autowired BusStopsRepository busStops) {
		restTemplate = new RestTemplate();
		minLatLng = new HashMap<>();
		maxLatLng = new HashMap<>();
		minLatLng.put("lat", busStops.findAll().stream().mapToDouble(s -> s.getLatitude()).min().getAsDouble());
		minLatLng.put("lng", busStops.findAll().stream().mapToDouble(s -> s.getLongitude()).min().getAsDouble());
		maxLatLng.put("lat", busStops.findAll().stream().mapToDouble(s -> s.getLatitude()).max().getAsDouble());
		maxLatLng.put("lng", busStops.findAll().stream().mapToDouble(s -> s.getLongitude()).max().getAsDouble());
	}

	@GetMapping("/geocode")
	public ResponseEntity<String> geocode(@RequestParam("address") @Size(min = 1) String address) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(google_geocoding_endpoint)
				// use the google geocoding token
				.queryParam("key", google_geocoding_token)
				// prefer results in the bounding box
				.queryParam("bounds",
						minLatLng.get("lat") + "," + minLatLng.get("lng") + "|" + maxLatLng.get("lat") + ","
								+ maxLatLng.get("lng"))
				// and use the address to get a value
				.queryParam("address", address);
		// return the response as is
		return restTemplate.getForEntity(uriBuilder.build().encode().toUri(), String.class);
	}
	
	@GetMapping("/reverseGeocode")
	public ResponseEntity<String> reverseGeocode(@RequestParam("latlng") @Size(min = 1) String latlng) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(google_geocoding_endpoint)
				// use the google geocoding token
				.queryParam("key", google_geocoding_token)
				// prefer results in the bounding box
				.queryParam("bounds",
						minLatLng.get("lat") + "," + minLatLng.get("lng") + "|" + maxLatLng.get("lat") + ","
								+ maxLatLng.get("lng"))
				// and use the latlng to get a value
				.queryParam("latlng", latlng);
		// return the response as is
		return restTemplate.getForEntity(uriBuilder.build().encode().toUri(), String.class);
	}
	
}

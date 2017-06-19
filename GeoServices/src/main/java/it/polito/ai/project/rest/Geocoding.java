package it.polito.ai.project.rest;

import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class Geocoding {

	private static String google_geocoding_endpoint = "https://maps.googleapis.com/maps/api/geocode/json";
	// please if you are using this code, change the token or it will reach the
	// quota and won't work
	private static String google_geocoding_token = "AIzaSyCL8dY-vuMWMd6hb10aPPctsyenIMxPmi4";

	private RestTemplate restTemplate;

	public Geocoding() {
		restTemplate = new RestTemplate();
	}

	@GetMapping("/geocode")
	public ResponseEntity<String> geocode(@RequestParam("address") @Size(min=1) String address) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(google_geocoding_endpoint)
				.queryParam("key", google_geocoding_token).queryParam("bounds", "44.92288,7.49722|45.1887,7.84365").queryParam("address", address);
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity(uriBuilder.build().encode().toUri(), String.class);
		return responseEntity;
	}
}

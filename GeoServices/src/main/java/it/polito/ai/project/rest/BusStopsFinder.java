package it.polito.ai.project.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.postgis.repo.BusStopsGeoRepository;

@RestController
@CrossOrigin(origins = "*")
public class BusStopsFinder {
	
	@Autowired
	private BusStopsGeoRepository busStopsGeo;
	
	@GetMapping("/findBusStops")
	public List<String> findStopsAround(@RequestParam(value="max_meters", defaultValue="250") Double max_meters, @RequestParam("lat") @Min(0) Double lat, @RequestParam("lng") @Min(0) Double lng) {
		String point = "SRID=4326;POINT(" + lat + " " + lng + ")";
		return busStopsGeo.findByDistance(point, max_meters).stream().map(bs -> bs.getId()).collect(Collectors.toList());
	}

}

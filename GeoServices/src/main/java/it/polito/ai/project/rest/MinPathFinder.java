package it.polito.ai.project.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.mongo.entities.Key;
import it.polito.ai.project.mongo.entities.MinPath;
import it.polito.ai.project.mongo.repo.MinPathsRepository;
import it.polito.ai.project.postgis.entities.BusStopGeographic;
import it.polito.ai.project.postgis.repo.BusStopsGeoRepository;

@RestController
@CrossOrigin(origins = "*")
public class MinPathFinder {
	
	@Autowired
	private BusStopsGeoRepository busStopsGeoRepository;
	
	@Autowired
	private MinPathsRepository minPathsRepository;
	
	@GetMapping("/getMinPathBetweenPositions")
	public MinPath getMinPathBetweenPositions(@RequestParam("srcLat") @Min(0) Double srcLat, @RequestParam("srcLng") @Min(0) Double srcLng, @RequestParam("dstLat") @Min(0) Double dstLat, @RequestParam("dstLng") @Min(0) Double dstLng) {
		String srcPosition = "SRID=4326;POINT(" + srcLat + " " + srcLng + ")";
		String dstPosition = "SRID=4326;POINT(" + dstLat + " " + dstLng + ")";
		
		// find the stops near source point
		List<BusStopGeographic> stopsNearSrc = busStopsGeoRepository.findByDistance(srcPosition, 250.0);
		
		// find the stops near destination point
		List<BusStopGeographic> stopsNearDst = busStopsGeoRepository.findByDistance(dstPosition, 250.0);
		
		// find all possible MinPaths between the two sets of stops
		List<MinPath> minPaths = new ArrayList<MinPath>();
		for (BusStopGeographic source : stopsNearSrc) {
			for (BusStopGeographic destination : stopsNearDst) {
				Key key = new Key(source.getId(), destination.getId());
				MinPath minPath = minPathsRepository.findOne(key);
				minPaths.add(minPath);
			}
		}
		
		// find the MinPath with lower cost
		MinPath bestPath = null;
		for (MinPath p : minPaths) {
			if (bestPath == null || p.getTotalCost() < bestPath.getTotalCost()) {
				bestPath = p;
			}
		}
		return bestPath;

	}
}

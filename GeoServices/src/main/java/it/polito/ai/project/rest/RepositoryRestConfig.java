package it.polito.ai.project.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import it.polito.ai.project.postgis.entities.BusLine;
import it.polito.ai.project.postgis.entities.BusStop;
import it.polito.ai.project.postgis.entities.BusStopGeographic;

/**
 * 
 * This class modifies the default configuration for the RepositoryRestResources
 * by exposing the id for the serialization
 *
 */
@Configuration
public class RepositoryRestConfig extends RepositoryRestConfigurerAdapter {
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(BusLine.class, BusStopGeographic.class, BusStop.class);
	}
}

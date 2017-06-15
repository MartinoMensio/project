package it.polito.ai.project.web.controller.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import it.polito.ai.project.repo.CarSharingServicesRepository;
import it.polito.ai.project.repo.entities.CarSharingService;

public class CarSharingServiceFormatter implements Formatter<CarSharingService> {

	@Autowired
	private CarSharingServicesRepository carSharingServicesRepository;

	@Override
	public String print(CarSharingService carSharingService, Locale locale) {
		return String.valueOf(carSharingService);
	}

	@Override
	public CarSharingService parse(String text, Locale locale) throws ParseException {
		try {
			long fuelId = Long.parseLong(text);
			return carSharingServicesRepository.findOne(fuelId);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}

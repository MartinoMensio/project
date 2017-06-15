package it.polito.ai.project.web.controller.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import it.polito.ai.project.repo.FuelsRepository;
import it.polito.ai.project.repo.entities.Fuel;

public class FuelFormatter implements Formatter<Fuel> {

	@Autowired
	private FuelsRepository fuelsRepository;

	@Override
	public String print(Fuel fuel, Locale locale) {
		return String.valueOf(fuel.getId());
	}

	@Override
	public Fuel parse(String text, Locale locale) throws ParseException {
		try {
			long fuelId = Long.parseLong(text);
			return fuelsRepository.findOne(fuelId);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}

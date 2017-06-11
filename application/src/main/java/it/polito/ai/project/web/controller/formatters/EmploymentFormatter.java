package it.polito.ai.project.web.controller.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import it.polito.ai.project.repo.EmploymentsRepository;
import it.polito.ai.project.repo.entities.Employment;

public class EmploymentFormatter implements Formatter<Employment> {

	@Autowired
	private EmploymentsRepository employmentsRepository;

	@Override
	public String print(Employment employment, Locale locale) {
		return String.valueOf(employment.getId());
	}

	@Override
	public Employment parse(String text, Locale locale) throws ParseException {
		try {
			long fuelId = Long.parseLong(text);
			return employmentsRepository.findOne(fuelId);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}

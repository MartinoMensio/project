package it.polito.ai.project.web.controller.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import it.polito.ai.project.repo.EducationLevelsRepository;
import it.polito.ai.project.repo.entities.EducationLevel;

public class EducationLevelFormatter implements Formatter<EducationLevel> {

	@Autowired
	private EducationLevelsRepository educationLevelsRepository;

	@Override
	public String print(EducationLevel educationLevel, Locale locale) {
		return String.valueOf(educationLevel.getId());
	}

	@Override
	public EducationLevel parse(String text, Locale locale) throws ParseException {
		try {
			long fuelId = Long.parseLong(text);
			return educationLevelsRepository.findOne(fuelId);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}

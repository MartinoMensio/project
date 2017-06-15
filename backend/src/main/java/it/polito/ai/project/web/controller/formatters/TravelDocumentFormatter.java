package it.polito.ai.project.web.controller.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import it.polito.ai.project.repo.TravelDocumentsRepository;
import it.polito.ai.project.repo.entities.TravelDocument;

public class TravelDocumentFormatter implements Formatter<TravelDocument> {

	@Autowired
	private TravelDocumentsRepository travelDocumentsRepository;

	@Override
	public String print(TravelDocument travelDocument, Locale locale) {
		return String.valueOf(travelDocument);
	}

	@Override
	public TravelDocument parse(String text, Locale locale) throws ParseException {
		try {
			long fuelId = Long.parseLong(text);
			return travelDocumentsRepository.findOne(fuelId);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}

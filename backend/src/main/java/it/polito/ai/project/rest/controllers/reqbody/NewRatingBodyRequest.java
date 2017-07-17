package it.polito.ai.project.rest.controllers.reqbody;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class NewRatingBodyRequest {
	@Min(1)
	@Max(5)
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}

package it.polito.ai.project.rest.resources;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import it.polito.ai.project.repo.entities.User;
import it.polito.ai.project.repo.entities.UserProfile;

public class AnonymizedUser extends ResourceSupport {
	private String sex;
	private Date dateOfBirth;
	
	private String educationLevel;
	private String employment;

	private Boolean privateCarOwnership;
	private Integer carRegistrationYear;
	private String carFuel;

	private Boolean carSharingUsage;
	private String carSharingService;

	private Boolean bikeUsage;
	private Boolean privateBikeUsage;
	private Boolean bikeSharingUsage;

	private Boolean publicTransportUsage;
	private String habitualTravelDocument;

	/**
	 * build an anonymized user from a user
	 * 
	 * @param user
	 */
	public AnonymizedUser(User user) {
		
		UserProfile userProfile = user.getProfile();
		if (userProfile != null) {
			this.sex = userProfile.getSex();
			this.dateOfBirth = userProfile.getDateOfBirth();
			
			this.educationLevel = userProfile.getEducationLevel() == null ? null : userProfile.getEducationLevel().getValue();
			this.employment = userProfile.getEmployment().getValue();
			
			this.privateCarOwnership = userProfile.getPrivateCarOwnership();
			this.carRegistrationYear = userProfile.getCarRegistrationYear();
			this.carFuel = userProfile.getCarFuel() == null ? null : userProfile.getCarFuel().getValue();
			
			this.carSharingUsage = userProfile.getCarSharingUsage();
			this.carSharingService = userProfile.getCarSharingService() == null ? null : userProfile.getCarSharingService().getName();
			
			this.bikeUsage = userProfile.getBikeUsage();
			this.privateBikeUsage = userProfile.getPrivateBikeUsage();
			this.bikeSharingUsage = userProfile.getBikeSharingUsage();
			
			this.publicTransportUsage = userProfile.getPublicTransportUsage();
			this.habitualTravelDocument = userProfile.getHabitualTravelDocument() == null ? null : userProfile.getHabitualTravelDocument().getName();
		}
	}

	public String getSex() {
		return sex;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public String getEmployment() {
		return employment;
	}

	public Boolean getPrivateCarOwnership() {
		return privateCarOwnership;
	}

	public Integer getCarRegistrationYear() {
		return carRegistrationYear;
	}

	public String getCarFuel() {
		return carFuel;
	}

	public Boolean getCarSharingUsage() {
		return carSharingUsage;
	}

	public String getCarSharingService() {
		return carSharingService;
	}

	public Boolean getBikeUsage() {
		return bikeUsage;
	}

	public Boolean getPrivateBikeUsage() {
		return privateBikeUsage;
	}

	public Boolean getBikeSharingUsage() {
		return bikeSharingUsage;
	}

	public Boolean getPublicTransportUsage() {
		return publicTransportUsage;
	}

	public String getHabitualTravelDocument() {
		return habitualTravelDocument;
	}

}

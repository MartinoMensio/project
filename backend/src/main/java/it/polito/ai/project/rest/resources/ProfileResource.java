package it.polito.ai.project.rest.resources;

import java.util.Date;

import it.polito.ai.project.repo.entities.CarSharingService;
import it.polito.ai.project.repo.entities.EducationLevel;
import it.polito.ai.project.repo.entities.Employment;
import it.polito.ai.project.repo.entities.Fuel;
import it.polito.ai.project.repo.entities.Image;
import it.polito.ai.project.repo.entities.Status;
import it.polito.ai.project.repo.entities.TravelDocument;
import it.polito.ai.project.repo.entities.User;
import it.polito.ai.project.repo.entities.UserProfile;

public class ProfileResource {

	private long id;
	private String email;
	private String nickname;
	private Status status;
	private String sex;
	private Date dateOfBirth;
	private EducationLevel educationLevel;
	private Employment employment;
	private Boolean privateCarOwnership;
	private Integer carRegistrationYear;
	private Fuel carFuel;
	private Boolean carSharingUsage;
	private CarSharingService carSharingService;
	private Boolean bikeUsage;
	private Boolean privateBikeUsage;
	private Boolean bikeSharingUsage;
	private Boolean publicTransportUsage;
	private TravelDocument habitualTravelDocument;
	private String imageUrl;

	public ProfileResource(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.nickname = user.getNickname();
		this.status = user.getStatus();
		UserProfile userProfile = user.getProfile();
		if (userProfile != null) {
			this.sex = userProfile.getSex();
			this.dateOfBirth = userProfile.getDateOfBirth();
			this.employment = userProfile.getEmployment();
			this.privateCarOwnership = userProfile.getPrivateCarOwnership();
			this.carRegistrationYear = userProfile.getCarRegistrationYear();
			this.carFuel = userProfile.getCarFuel();
			this.carSharingUsage = userProfile.getCarSharingUsage();
			this.carSharingService = userProfile.getCarSharingService();
			this.bikeUsage = userProfile.getBikeUsage();
			this.privateBikeUsage = userProfile.getPrivateBikeUsage();
			this.bikeSharingUsage = userProfile.getBikeSharingUsage();
			this.publicTransportUsage = userProfile.getPublicTransportUsage();
			this.habitualTravelDocument = userProfile.getHabitualTravelDocument();
			Image userImage = userProfile.getImage();
			if (userImage != null) {
				this.imageUrl = userImage.getUrl();
			}
		}
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public Status getStatus() {
		return status;
	}

	public String getSex() {
		return sex;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public Employment getEmployment() {
		return employment;
	}

	public Boolean getPrivateCarOwnership() {
		return privateCarOwnership;
	}

	public Integer getCarRegistrationYear() {
		return carRegistrationYear;
	}

	public Fuel getCarFuel() {
		return carFuel;
	}

	public Boolean getCarSharingUsage() {
		return carSharingUsage;
	}

	public CarSharingService getCarSharingService() {
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

	public TravelDocument getHabitualTravelDocument() {
		return habitualTravelDocument;
	}

	public String getImageUrl() {
		return imageUrl;
	}

}

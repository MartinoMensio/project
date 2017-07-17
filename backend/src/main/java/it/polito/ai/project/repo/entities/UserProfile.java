package it.polito.ai.project.repo.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Type;

@Entity(name="userProfiles")
public class UserProfile {
	@Id
	private long id;
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private User user;
	@Enumerated(EnumType.STRING)
	private Gender sex;
	@Type(type="date")
	private Date dateOfBirth;
	@ManyToOne
	@JoinColumn(name="education_level_id")
	private EducationLevel educationLevel;
	@ManyToOne
	@JoinColumn(name="employment_id")
	private Employment employment;
	
	private Boolean privateCarOwnership;
	private Integer carRegistrationYear;
	@ManyToOne
	@JoinColumn(name="fuel_id")
	private Fuel carFuel;
	
	private Boolean carSharingUsage;
	@ManyToOne
	@JoinColumn(name="car_sharing_service_id")
	private CarSharingService carSharingService;
	
	private Boolean bikeUsage;
	private Boolean privateBikeUsage;
	private Boolean bikeSharingUsage;
	
	private Boolean publicTransportUsage;
	@ManyToOne
	@JoinColumn(name="habitual_travel_document_id")
	private TravelDocument habitualTravelDocument;
	
	@OneToOne(cascade = CascadeType.ALL, optional=true)
	@JoinColumn(name="image_id")
	private Image image;
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return image;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getSex() {
		return sex.name();
	}
	public void setSex(String sex) {
		this.sex = Gender.valueOf(sex);
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public EducationLevel getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}
	public Employment getEmployment() {
		return employment;
	}
	public void setEmployment(Employment employment) {
		this.employment = employment;
	}
	public Boolean getPrivateCarOwnership() {
		return privateCarOwnership;
	}
	public void setPrivateCarOwnership(Boolean privateCarOwnership) {
		this.privateCarOwnership = privateCarOwnership;
	}
	public Integer getCarRegistrationYear() {
		return carRegistrationYear;
	}
	public void setCarRegistrationYear(Integer carRegistrationYear) {
		this.carRegistrationYear = carRegistrationYear;
	}
	public Fuel getCarFuel() {
		return carFuel;
	}
	public void setCarFuel(Fuel carFuel) {
		this.carFuel = carFuel;
	}
	public Boolean getCarSharingUsage() {
		return carSharingUsage;
	}
	public void setCarSharingUsage(Boolean carSharingUsage) {
		this.carSharingUsage = carSharingUsage;
	}
	public CarSharingService getCarSharingService() {
		return carSharingService;
	}
	public void setCarSharingService(CarSharingService carSharingService) {
		this.carSharingService = carSharingService;
	}
	public Boolean getBikeUsage() {
		return bikeUsage;
	}
	public void setBikeUsage(Boolean bikeUsage) {
		this.bikeUsage = bikeUsage;
	}
	public Boolean getPrivateBikeUsage() {
		return privateBikeUsage;
	}
	public void setPrivateBikeUsage(Boolean privateBikeUsage) {
		this.privateBikeUsage = privateBikeUsage;
	}
	public Boolean getBikeSharingUsage() {
		return bikeSharingUsage;
	}
	public void setBikeSharingUsage(Boolean bikeSharingUsage) {
		this.bikeSharingUsage = bikeSharingUsage;
	}
	public Boolean getPublicTransportUsage() {
		return publicTransportUsage;
	}
	public void setPublicTransportUsage(Boolean publicTransportUsage) {
		this.publicTransportUsage = publicTransportUsage;
	}
	public TravelDocument getHabitualTravelDocument() {
		return habitualTravelDocument;
	}
	public void setHabitualTravelDocument(TravelDocument habitualTravelDocument) {
		this.habitualTravelDocument = habitualTravelDocument;
	}
}

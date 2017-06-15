package it.polito.ai.project.rest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.business.services.accounting.AccountingService;
import it.polito.ai.project.business.services.accounting.ResultInfo;
import it.polito.ai.project.business.services.authentication.CurrentUserService;
import it.polito.ai.project.repo.entities.User;
import it.polito.ai.project.repo.entities.UserProfile;
import it.polito.ai.project.rest.resources.ProfileResource;
import it.polito.ai.project.rest.resources.ResultInfoResource;
import it.polito.ai.project.web.controller.forms.PasswordForm;
import it.polito.ai.project.web.controller.forms.ProfileForm;
import it.polito.ai.project.web.exceptions.ClientErrorException;

@RestController
public class ProfileRestController {

	@Autowired
	CurrentUserService currentUserService;

	@Autowired
	AccountingService accountingService;

	@RequestMapping(path = "/api/profile", method = RequestMethod.GET)
	public ProfileResource getProfile() {
		User currentUser = currentUserService.getCurrentUser();
		return new ProfileResource(currentUser);
	}

	@RequestMapping(path = "/api/profile/password", method = RequestMethod.PUT)
	public ResultInfoResource changePassword(@Valid @RequestBody PasswordForm passwordForm, BindingResult result) {
		if (result.hasErrors()) {
			throw new ClientErrorException("wrong fields in the request");
		}
		
		String userEmail = currentUserService.getCurrentUser().getEmail();
		ResultInfo res = null;
		
		// Request the service to update the password
		try {
			res = accountingService.changePassword(userEmail, 
							passwordForm.getOldPassword(), 
							passwordForm.getNewPassword(), 
							passwordForm.getConfirmedPassword());
		}
		catch (Exception e) {
			throw new ClientErrorException("change password failed");
		}
		
		if (res == ResultInfo.PASSWORD_CHANGE_OK) {
			return new ResultInfoResource(200, "password changed");
		}
		else {
			throw new ClientErrorException();
		}
	}

	@RequestMapping(path = "/api/profile", method = RequestMethod.PUT)
	public ResultInfoResource updateProfile(@Valid @RequestBody ProfileForm profileFormTest, BindingResult result) {
		if (!result.getFieldValue("carRegistrationYear").equals("") && result.hasErrors()) {
			throw new ClientErrorException("wrong fields in the request");
		}

		// Create the userProfile object from the form. It is just a conversion
		UserProfile userProfile = profileFormToUserProfile(profileFormTest);

		// restore the image
		UserProfile userProfileOld = currentUserService.getCurrentUser().getProfile();
		if (userProfileOld != null) {
			userProfile.setImage(userProfileOld.getImage());
		}

		// Save the user profile data
		try {
			accountingService.updateUserProfileInfo(currentUserService.getCurrentUser().getEmail(), userProfile,
					profileFormTest.getNickname());
		} catch (Exception e) {
			throw new ClientErrorException("update failed");
		}

		return new ResultInfoResource(200, "profile updated");
	}

	private UserProfile profileFormToUserProfile(ProfileForm profileForm) {
		UserProfile userProfile = new UserProfile();

		userProfile.setSex(profileForm.getSex());
		userProfile.setDateOfBirth(profileForm.getDateOfBirth());

		userProfile.setEducationLevel(profileForm.getEducationLevel());
		userProfile.setEmployment(profileForm.getEmployment());

		userProfile.setPrivateCarOwnership(profileForm.getPrivateCarOwnership());
		if (userProfile.getPrivateCarOwnership() == true) {
			userProfile.setCarRegistrationYear(profileForm.getCarRegistrationYear());
			userProfile.setCarFuel(profileForm.getCarFuel());
		} else {
			userProfile.setCarRegistrationYear(null);
			userProfile.setCarFuel(null);
		}

		userProfile.setCarSharingUsage(profileForm.getCarSharingUsage());
		if (userProfile.getCarSharingUsage() == true) {
			userProfile.setCarSharingService(profileForm.getCarSharingService());
		} else {
			userProfile.setCarSharingService(null);
		}

		userProfile.setBikeUsage(profileForm.getBikeUsage());
		if (userProfile.getBikeUsage() == true) {
			userProfile.setPrivateBikeUsage(profileForm.getPrivateBikeUsage());
			userProfile.setBikeSharingUsage(profileForm.getBikeSharingUsage());
		} else {
			userProfile.setPrivateBikeUsage(null);
			userProfile.setBikeSharingUsage(null);
		}

		userProfile.setPublicTransportUsage(profileForm.getPublicTransportUsage());
		if (userProfile.getPublicTransportUsage() == true) {
			userProfile.setHabitualTravelDocument(profileForm.getHabitualTravelDocument());
		} else {
			userProfile.setHabitualTravelDocument(null);
		}

		return userProfile;
	}
}

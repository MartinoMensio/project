package it.polito.ai.project.rest.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.business.services.accounting.AccountingService;
import it.polito.ai.project.business.services.accounting.ResultInfo;
import it.polito.ai.project.business.services.authentication.CurrentUserService;
import it.polito.ai.project.business.services.emailVerification.EmailVerificationService;
import it.polito.ai.project.repo.entities.User;
import it.polito.ai.project.repo.entities.UserProfile;
import it.polito.ai.project.rest.resources.ProfileResource;
import it.polito.ai.project.rest.resources.ResultInfoResource;
import it.polito.ai.project.web.controller.forms.PasswordForm;
import it.polito.ai.project.web.controller.forms.ProfileForm;
import it.polito.ai.project.web.controller.forms.RegistrationForm;
import it.polito.ai.project.web.exceptions.ClientErrorException;

@RestController
public class ProfileRestController {

	@Autowired
	private CurrentUserService currentUserService;

	@Autowired
	private AccountingService accountingService;
	
	@Autowired
	private EmailVerificationService emailVerificationService;
	
	private Map<String, AtomicLong> wrongTrials = new HashMap<>();

	@GetMapping("/api/profile")
	public ProfileResource getProfile() {
		User currentUser = currentUserService.getCurrentUser();
		return new ProfileResource(currentUser);
	}

	@PutMapping("/api/profile/password")
	public ResultInfoResource changePassword(@Valid @RequestBody PasswordForm passwordForm, BindingResult result) {
		if (result.hasErrors()) {
			throw new ClientErrorException("wrong fields in the request");
		}

		String userEmail = currentUserService.getCurrentUser().getEmail();
		ResultInfo res = null;

		// Request the service to update the password
		try {
			res = accountingService.changePassword(userEmail, passwordForm.getOldPassword(),
					passwordForm.getNewPassword(), passwordForm.getConfirmedPassword());
		} catch (Exception e) {
			throw new ClientErrorException("change password failed");
		}

		if (res == ResultInfo.PASSWORD_CHANGE_OK) {
			return new ResultInfoResource(200, "password changed");
		} else {
			throw new ClientErrorException();
		}
	}

	@PutMapping("/api/profile")
	public ProfileResource updateProfile(@Valid @RequestBody ProfileForm profileFormTest, BindingResult result) {
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
			User updatedUser = accountingService.updateUserProfileInfo(currentUserService.getCurrentUser().getEmail(),
					userProfile, profileFormTest.getNickname());
			return new ProfileResource(updatedUser);
		} catch (Exception e) {
			throw new ClientErrorException("update failed");
		}
	}

	@PostMapping("/api/signup")
	public ResultInfoResource signup(@Valid @RequestBody RegistrationForm registrationForm, BindingResult result) {
		/*
		 * Validate received data.
		 */
		if (result.hasErrors()) {
			throw new ClientErrorException("the data provided contains some errors");
		}

		try {
			accountingService.addNewUser(registrationForm.getEmail(), registrationForm.getNickname(),
					registrationForm.getPassword());
		} catch (DataIntegrityViolationException e) {
			throw new ClientErrorException("An account already exists associated to this email");
		} catch (Exception e) {
			throw new ClientErrorException();
		}

		return new ResultInfoResource(200,
				"the account is created and an email has been sent to the provided address. Confirm the email address");
	}
	
	@GetMapping("/api/confirmEmail")
	public ResultInfoResource confirmEmail(@RequestParam @NotNull @Size(min=1) String email, @RequestParam @NotNull @Size(min=1) String token) {
		
		boolean verificationResult = emailVerificationService.verifyMail(email, token);
		if (!verificationResult) {
			// avoid bruteforce attack
			wrongTrials.putIfAbsent(email, new AtomicLong(0));
			long attempts = wrongTrials.get(email).incrementAndGet();
			try {
				// sleep quadratically in the number of attempts
				Thread.sleep(attempts * attempts * 1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return null;
			}
			throw new ClientErrorException("verification failed");
		}
		return new ResultInfoResource(200, "verified");
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

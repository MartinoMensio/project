package it.polito.ai.project.web.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.polito.ai.project.business.services.authentication.CurrentUserService;
import it.polito.ai.project.business.services.images.ImagesService;
import it.polito.ai.project.repo.entities.Image;
import it.polito.ai.project.repo.entities.User;
import it.polito.ai.project.repo.entities.UserProfile;

@Controller
public class ImagesController {

	@Autowired
	ImagesService imagesService;
	
	@Autowired
	CurrentUserService currentUserService;

	@GetMapping("/images/{id}")
	public void getImage(@PathVariable("id") Long imageId, HttpServletResponse response) {
		Image image = imagesService.getImage(imageId);
		byte[] imageBytes = image.getValue();

		response.setContentType("image/jpeg");

		try {
			ServletOutputStream responseOutputStream = response.getOutputStream();
			responseOutputStream.write(imageBytes);
			responseOutputStream.flush();
			responseOutputStream.close();
		} catch (IOException e) {
			response.setStatus(500);
		}

	}

	@PostMapping("/setProfileImage")
	public String setProfileImage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		System.out.println(file.getContentType());
		if (!file.getContentType().startsWith("image/")) {
			redirectAttributes.addAttribute("error", "wrong type of file");
			return "redirect:profile";
		}
		if (file.isEmpty()) {
			redirectAttributes.addAttribute("error", "no file was provided");
			return "redirect:profile";
		}
		
		User user = currentUserService.getCurrentUser();
		if (user == null) {
			redirectAttributes.addAttribute("error", "no authenticated user");
			return "redirect:profile";
		}
		UserProfile userProfile = user.getProfile();
		if (userProfile == null) {
			// this should not be possible because profile must be complete
			redirectAttributes.addAttribute("error", "incomplete profile");
			return "redirect:profile";
		}

		try {
			byte[] bytes = file.getBytes();
			imagesService.saveUserImage(userProfile, bytes);
		} catch (IOException e) {
			redirectAttributes.addAttribute("error", "error in images processing");
		}

		return "redirect:profile";
	}
}

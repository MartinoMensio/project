package it.polito.ai.project.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.business.services.authentication.CurrentUserService;
import it.polito.ai.project.business.services.images.ImagesService;
import it.polito.ai.project.repo.entities.Image;
import it.polito.ai.project.repo.entities.User;
import it.polito.ai.project.repo.entities.UserProfile;
import it.polito.ai.project.rest.resources.ImageResource;
import it.polito.ai.project.web.exceptions.ClientErrorException;
import it.polito.ai.project.web.exceptions.NotFoundException;

@RestController
public class ImagesController {

	@Autowired
	ImagesService imagesService;

	@Autowired
	CurrentUserService currentUserService;

	@GetMapping("/images/{id}")
	public HttpEntity<ImageResource> getImageResource(@PathVariable("id") Long imageId) {
		Image image = imagesService.getImage(imageId);
		if (image == null) {
			throw new NotFoundException();
		}

		ImageResource imageResource = getImageResourceWithLinks(image);

		return new ResponseEntity<ImageResource>(imageResource, HttpStatus.OK);
	}

	/**
	 * This endpoint exposes the images from the database. The response is with
	 * content type image/jpeg so that can be used directly
	 * 
	 * @param imageId
	 * @param response
	 */
	@GetMapping(path = "/images/{id}/raw", produces = { MediaType.IMAGE_JPEG_VALUE })
	public byte[] getImageRaw(@PathVariable("id") Long imageId) {
		Image image = imagesService.getImage(imageId);
		if (image == null) {
			throw new NotFoundException();
		}
		byte[] imageBytes = image.getValue();

		return imageBytes;

	}

	@PutMapping("/api/profile/image")
	public HttpEntity<ImageResource> setProfileImage(@RequestBody String imageString) {
		Image image = imagesService.getImageFromString(imageString);
		if (image == null) {
			throw new NotFoundException();
		}
		User user = currentUserService.getCurrentUser();
		UserProfile userProfile = user.getProfile();
		if (userProfile == null) {
			// this should not be possible because profile must be complete
			throw new ClientErrorException("incomplete profile");
		}

		image = imagesService.saveUserImage(userProfile, image);
		ImageResource imageResource = getImageResourceWithLinks(image);
		return new ResponseEntity<ImageResource>(imageResource, HttpStatus.OK);
	}

	/**
	 * This method builds a HATEOAS resource for the image with self link and
	 * link to the raw version (directy renderable by the clients)
	 * 
	 * @param image
	 * @return
	 */
	ImageResource getImageResourceWithLinks(Image image) {
		ImageResource imageResource = new ImageResource(image);
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(ImagesController.class).getImageResource(image.getId()))
				.withSelfRel();
		imageResource.add(selfLink);
		imageResource.add(new Link(image.getUrl(), "raw"));
		return imageResource;
	}
}

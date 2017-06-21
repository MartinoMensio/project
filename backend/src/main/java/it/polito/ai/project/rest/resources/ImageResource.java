package it.polito.ai.project.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import it.polito.ai.project.repo.entities.Image;

public class ImageResource extends ResourceSupport{

	private Long imageId;

	public ImageResource(Image image) {
		imageId = image.getId();
	}
	
	public Long getImageId() {
		return imageId;
	}
}

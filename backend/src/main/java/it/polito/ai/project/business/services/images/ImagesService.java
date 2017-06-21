package it.polito.ai.project.business.services.images;

import it.polito.ai.project.repo.entities.Image;
import it.polito.ai.project.repo.entities.UserProfile;

public interface ImagesService {
	
	public Image getImage(long id);
	
	public Image saveUserImage(UserProfile userProfile, Image image);
	
	public Image getImageFromString(String imageString);
}

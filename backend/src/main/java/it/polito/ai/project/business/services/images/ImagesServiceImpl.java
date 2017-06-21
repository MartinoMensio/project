package it.polito.ai.project.business.services.images;

import java.util.Base64;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import it.polito.ai.project.repo.ImagesRepository;
import it.polito.ai.project.repo.UserProfilesRepository;
import it.polito.ai.project.repo.entities.Image;
import it.polito.ai.project.repo.entities.UserProfile;

@Service
@Transactional
public class ImagesServiceImpl implements ImagesService{

	@Autowired
	private ImagesRepository imagesRepository;
	
	@Autowired
	private UserProfilesRepository userProfilesRepository;

	@Override
	public Image getImage(long id) {
		return imagesRepository.findOne(id);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	public Image saveUserImage(UserProfile userProfile, Image image) {
		Image oldImage = userProfile.getImage();
		// delete the old image
		if (oldImage != null) {
			imagesRepository.delete(oldImage);
		}
		userProfile.setImage(image);
		
		userProfilesRepository.save(userProfile);
		
		return userProfile.getImage();
	}
	
	@Override
	public Image getImageFromString(String imageString) {
		if (imageString != null && !imageString.equals("")) {
			String imageDataWithPrefix = imageString;
			// check MIME type
			if (imageDataWithPrefix.startsWith("data:image/")) {
				// get the string after "data:image/png;base64," prefix
				String imageData = imageDataWithPrefix.substring(imageDataWithPrefix.indexOf(",") + 1);
				byte[] imageBytes = Base64.getDecoder().decode(imageData);
				return new Image(imageBytes);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}

package it.polito.ai.project.business.services.images;

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
	public Image saveUserImage(UserProfile userProfile, byte[] imageBytes) {
		Image oldImage = userProfile.getImage();
		// delete the old image
		if (oldImage != null) {
			imagesRepository.delete(oldImage);
		}
		userProfile.setImage(new Image(imageBytes));
		
		userProfilesRepository.save(userProfile);
		
		return userProfile.getImage();
	}
}

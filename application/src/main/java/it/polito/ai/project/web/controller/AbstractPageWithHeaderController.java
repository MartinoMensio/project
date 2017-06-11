package it.polito.ai.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import it.polito.ai.project.business.services.authentication.CurrentUserService;
import it.polito.ai.project.business.services.chat.ChatService;
import it.polito.ai.project.repo.entities.User;

public abstract class AbstractPageWithHeaderController {
	@Autowired
	protected ChatService chatService;
	@Autowired
	protected CurrentUserService currentUserService;

	
	public void attachData(ModelMap model) {
		model.put("topics", chatService.getTopics());
		User currentUser = currentUserService.getCurrentUser();
		
		if (currentUser != null) {
			model.put("user", currentUser);
		}
	}
}

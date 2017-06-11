package it.polito.ai.project.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.polito.ai.project.repo.entities.User;

@Controller
@RequestMapping({"/profile"})
public class ProfileController extends AbstractPageWithHeaderController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String showLogin(ModelMap model){
		super.attachData(model);
		User currentUser = currentUserService.getCurrentUser();
		
		model.addAttribute("profileInfo", currentUser.getProfile());
		
		return "profile";
	}

}

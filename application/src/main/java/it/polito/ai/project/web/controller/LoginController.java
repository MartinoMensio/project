package it.polito.ai.project.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/login"})
public class LoginController extends AbstractPageWithHeaderController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String showLogin(ModelMap model, @RequestParam(required = false) String error){
		super.attachData(model);
		if (error != null) {
			model.addAttribute("loginError", error);
		}
		return "login";
	}

}

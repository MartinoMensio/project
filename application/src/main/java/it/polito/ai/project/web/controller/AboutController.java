package it.polito.ai.project.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/about"})
public class AboutController extends AbstractPageWithHeaderController{
	
	@RequestMapping(method = RequestMethod.GET)
	public String showLogin(ModelMap model){
		super.attachData(model);
		return "about";
	}

}

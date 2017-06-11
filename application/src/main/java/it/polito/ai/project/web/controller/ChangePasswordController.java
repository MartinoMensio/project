package it.polito.ai.project.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.polito.ai.project.business.services.accounting.AccountingService;
import it.polito.ai.project.business.services.accounting.ResultInfo;
import it.polito.ai.project.business.services.authentication.CurrentUserService;
import it.polito.ai.project.web.controller.forms.PasswordForm;

@Controller
@RequestMapping({"/changePassword"})
public class ChangePasswordController extends AbstractPageWithHeaderController {
	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private AccountingService accountingService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(ModelMap model) {
		super.attachData(model);
		
		model.addAttribute("passwordForm", new PasswordForm());
		
		return "changePassword";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(@Valid @ModelAttribute("passwordForm") PasswordForm passwordForm, BindingResult result, RedirectAttributes ras) {
		if (result.hasErrors()) {
			ras.addAttribute("result", "wrongFields");
			return "redirect:changePassword";
		}
		
		String userEmail = currentUserService.getCurrentUser().getEmail();
		ResultInfo res = null;
		
		// Request the service to update the password
		try {
			res = accountingService.changePassword(userEmail, 
							passwordForm.getOldPassword(), 
							passwordForm.getNewPassword(), 
							passwordForm.getConfirmedPassword());
		}
		catch (Exception e) {
			ras.addAttribute("result", "pwdChgErr");
		}
		
		if (res == ResultInfo.PASSWORD_CHANGE_OK) {
			ras.addAttribute("result", "pwdChgOk");
		}
		else {
			ras.addAttribute("result", "pwdChgErr");
		}
		
		return "redirect:changePassword";
	}
}

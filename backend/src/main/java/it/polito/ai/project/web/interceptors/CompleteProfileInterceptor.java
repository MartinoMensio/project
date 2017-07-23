package it.polito.ai.project.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import it.polito.ai.project.business.services.authentication.CurrentUserService;
import it.polito.ai.project.repo.entities.User;
import it.polito.ai.project.web.exceptions.IncompleteProfileException;

@Component
public class CompleteProfileInterceptor implements HandlerInterceptor {
	@Autowired
	private CurrentUserService currentUserService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// now denying doing requests different than GET/OPTIONS or calls to /api/profile
		// look at WebMvcConfig also
		if (!request.getMethod().equals(RequestMethod.GET.name()) && !request.getMethod().equals(RequestMethod.OPTIONS.name())) {
			User user = currentUserService.getCurrentUser();
			if (user != null) {
				if (user.getStatus().getValue().equals("INCOMPLETE")) {
					throw new IncompleteProfileException("please complete your profile first");
				}
			}
		}

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}
}

package it.polito.ai.project.rest.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.rest.CustomResponse;
import it.polito.ai.project.rest.resources.LoginRequest;

@RestController
public class AuthenticationController {

	@Autowired
	AuthenticationManager authenticationManager;

	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> jsonLogin(@RequestBody LoginRequest loginRequest,
			HttpServletRequest request) {

		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.email,
					loginRequest.password);
			token.setDetails(new WebAuthenticationDetails(request));

			Authentication auth = authenticationManager.authenticate(token);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(auth);

			if (auth.isAuthenticated()) {
				HttpSession session = request.getSession(true);
				session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
				return ResponseEntity.ok(new CustomResponse(200, "logged in"));
			} else {
				SecurityContextHolder.getContext().setAuthentication(null);

				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new CustomResponse(400, "authentication failed"));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "authentication failed"));
		}
	}

	@RequestMapping(value = "/api/logout", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> jsonLogout(HttpServletRequest request) {

		try {
			// clear the security context
			SecurityContextHolder.clearContext();
			// and invalidate the HttpSession
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			return ResponseEntity.ok(new CustomResponse(200, "logged out"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, "authentication failed"));
		}
	}
}

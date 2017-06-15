package it.polito.ai.project.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 
 * Filter that sets the authentication using the TokenAuthenticationService. The
 * incoming requests are authenticated if they contain an "Authorization" header
 * with a JWT token (type bearer)
 *
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		// get the authentication using the Authorization header
		Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
		// set the Security context
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// and proceed
		filterChain.doFilter(request, response);
	}
}

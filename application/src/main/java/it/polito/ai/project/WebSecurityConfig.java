package it.polito.ai.project;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		// definition of the request filtering
		.authorizeRequests()
			// the permitted URLs without authentication
			.antMatchers("/", "/home", "/login", "/registration", "/about", "/contact", "/registrationResult","/css/**", "/webjars/**","/rest/**").permitAll()
			// all the other URLs require authentication
			.antMatchers("/**").hasRole("USER")
		.and()
		// something about login
			.formLogin()
			// the email field is used as username for the authentication
			.usernameParameter("email")
			// define the login location
			.loginPage("/login")
			// and the failure url
			.failureUrl("/login?error")
		.and()
		// something about logout
			.logout()
			// where to go after logout
			.logoutSuccessUrl("/home");
		// @formatter:on
	}

}

package it.polito.ai.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication()
				// add a dummy admin user, never used
				.withUser("admin").password("admin").roles("USER", "ADMIN");
	}

	/**
	 * This section defines the security policy for the app
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().cors().and().authorizeRequests()
				// permit everyone to do GET requests
				.antMatchers(HttpMethod.GET, "/**").permitAll()
				// and also OPTIONS requests
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				// and no one can do other requests
				.anyRequest().denyAll();
	}
}

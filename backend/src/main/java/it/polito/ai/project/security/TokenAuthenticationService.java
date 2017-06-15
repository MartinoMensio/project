package it.polito.ai.project.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	static final long EXPIRATIONTIME = 864_000_000; // 10 days
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	/**
	 * This function creates the JWT token and returns it to the client
	 * 
	 * @param res
	 * @param user
	 */
	static void addAuthentication(HttpServletResponse res, Authentication user) {
		Date expiration = new Date(System.currentTimeMillis() + EXPIRATIONTIME);
		// save wanted informations in the claims: first of all the username
		// (the email)
		Claims claims = Jwts.claims().setSubject(user.getName());
		// also save the roles attached to this user, because the security of
		// some services requires some roles
		claims.put("roles", user.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()));

		// build the JWT token
		String JWT = Jwts.builder().setExpiration(expiration).setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		JwtResource jwtResource = new JwtResource(JWT, expiration);
		
		try {
			// set content type
			res.addHeader("Content-Type", "application/json");
			// write in the response useful info for the client
			new ObjectMapper().writeValue(res.getOutputStream(), jwtResource);
		} catch (IOException e) {
			// this won't happen
			e.printStackTrace();
		}
	}

	static Authentication getAuthentication(HttpServletRequest request) {
		// get the Authorization header
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// parse the token
			Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody();
			// see who owns the token
			String userEmail = claims.getSubject();
			if (userEmail == null) {
				return null;
			}
			// get the roles of this user
			@SuppressWarnings("unchecked")
			List<String> roles = (List<String>) claims.get("roles");
			if (roles == null) {
				return null;
			}
			// build a list of GrantedAuthority from the names of roles
			List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role))
					.collect(Collectors.toList());

			return new UsernamePasswordAuthenticationToken(userEmail, null, authorities);
		}
		return null;
	}
}

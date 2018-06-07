/**
 * 
 */
package com.tsiry.org.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsiry.org.entities.AppUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Tsiry MANANKASINA
 *
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	
	private AuthenticationManager authenticationManager;
	
	
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		//recupération de l'user envoyé en format JSON
		AppUser appUser=null;
		try {
			//ObjectMapper transforme un objet JSON en objet JAVA
			appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		System.out.println("*************************************************************");
		System.out.println("username :" +appUser.getUsername());
		System.out.println("Password :" +appUser.getPassword());
		
		System.out.println("*************************************************************");
		
		return authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword())
		);
	}
	
	
	
	//GENERATION DE TOKEN
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		

		//recupération de l'user authentifié OK
		User springUser = (User) authResult.getPrincipal();
		
		String jwtToken = Jwts.builder()
				.setSubject(springUser.getUsername())   
				.setExpiration(
				 new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.claim("roles", springUser.getAuthorities())  //ce qu'on veut
				.compact();

		//envoyer le token dans la reponse
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+jwtToken);
		
	}
}

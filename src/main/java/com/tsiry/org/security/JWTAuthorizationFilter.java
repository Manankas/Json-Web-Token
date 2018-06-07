/**
 * 
 */
package com.tsiry.org.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author Tsiry MANANKASINA
 *
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwtToken = request.getHeader(SecurityConstants.HEADER_STRING);
		if(jwtToken == null || !jwtToken.startsWith(SecurityConstants.TOKEN_PREFIX) ){
			//on passe vers un autre filtre, c'est meme pas necessaire de continuer
			filterChain.doFilter(request, response);
			return;
		}
		
		//sinon , on signe le token
		Claims claims = Jwts.parser()
				.setSigningKey(SecurityConstants.SECRET)
				.parseClaimsJws(jwtToken.replace(SecurityConstants.TOKEN_PREFIX, "")) //supprimer le prefix
				.getBody();
		
		//le nom de l'user qui se connecte
		String username = claims.getSubject();
		
		ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>)
				claims.get("roles");
		
		//Pour pouvoir etre manipuler par  spring security , les roles doivent etre de type Collection<GrantedAuthority> 
		Collection<GrantedAuthority> authorities = new ArrayList();
		roles.forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.get("authority")));					
		});
		
		UsernamePasswordAuthenticationToken authenticatedUser = 
					new UsernamePasswordAuthenticationToken(username, null, authorities); //null car il n'y pas de password dans le token
		
		//on accede au contexte de secutité de SPRING , y charger l'user 	
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		
		filterChain.doFilter(request, response);
	}

}

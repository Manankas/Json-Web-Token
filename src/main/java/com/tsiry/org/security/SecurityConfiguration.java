/**
 * 
 */
package com.tsiry.org.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Tsiry MANANKASINA
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//comment chercher les users et les roles
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		/*auth.inMemoryAuthentication()
			.withUser("tsiry").password("tsiry").roles("ADMIN");
		*/
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
	}

	//les requetes entrant
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		//pour l'utilisation du JWT, desactiver la session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		/*
		 * The authorizeRequests().antMatchers() is then used to apply authorization to one or more paths you specify in antMatchers()
		 * */
		http.formLogin();
		http.authorizeRequests().antMatchers("/login/**", "/register/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/tasks/**").hasAuthority("USER");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/tasks/**").hasAuthority("ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		
		//lors de l'authentification:  POST > localhost:8080/login > { "username":"admin", "password":"1234"}
		http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
		
		//tout les requetes passe par ce filtre sauf ce du permitAll()
		http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
}

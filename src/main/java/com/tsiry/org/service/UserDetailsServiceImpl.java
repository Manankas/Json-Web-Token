/**
 * 
 */
package com.tsiry.org.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tsiry.org.entities.AppUser;

/**
 * @author Tsiry MANANKASINA
 *
 */
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private AccountService acountService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = acountService.findUserByUsername(username);
		if(user == null) throw new UsernameNotFoundException(username); 
		
		//Pour pouvoir etre manipuler par spring security , les roles doivent etre de type Collection<GrantedAuthority>
		Collection<GrantedAuthority> authorities = new ArrayList();
		user.getRoles().forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
		});
		
		return  new User(user.getUsername(),user.getPassword(),authorities);
	}

}

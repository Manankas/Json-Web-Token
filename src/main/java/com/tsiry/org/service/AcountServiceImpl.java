/**
 * 
 */
package com.tsiry.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsiry.org.entities.AppRole;
import com.tsiry.org.entities.AppUser;
import com.tsiry.org.repository.RoleRepository;
import com.tsiry.org.repository.UserRepository;

/**
 * @author Tsiry MANANKASINA
 *
 */
@Service("acountService")
@Transactional
public class AcountServiceImpl implements AccountService {

	@Autowired
	private UserRepository  userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public AppUser save(AppUser u) {
		String hashPw = bCryptPasswordEncoder.encode(u.getPassword());
		u.setPassword(hashPw);
		return userRepository.save(u);
	}


	@Override
	public AppRole saveRole(AppRole role) {
		return roleRepository.save(role) ;
	}

	
	@Override
	public void addRoleToUser(String username, String roleName) {
		AppRole role = roleRepository.findByRoleName(roleName);
		AppUser user = userRepository.findByUsername(username);
		  
		user.getRoles().add(role);
		
	}

	
	@Override
	public AppUser findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	} 

}

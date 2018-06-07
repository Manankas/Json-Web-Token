/**
 * 
 */
package com.tsiry.org.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tsiry.org.entities.AppRole;
import com.tsiry.org.entities.AppUser;
import com.tsiry.org.repository.UserRepository;

/**
 * @author Tsiry MANANKASINA
 *
 */
public interface AccountService {
	
	
	public AppUser save(AppUser u);
	public AppRole saveRole(AppRole role);
	
	public void addRoleToUser(String username , String roleName);
	public AppUser findUserByUsername(String username);
}

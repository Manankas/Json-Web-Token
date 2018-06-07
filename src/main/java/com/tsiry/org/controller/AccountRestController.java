/**
 * 
 */
package com.tsiry.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tsiry.org.dto.RegisterForm;
import com.tsiry.org.entities.AppUser;
import com.tsiry.org.service.AccountService;

/**
 * @author Tsiry MANANKASINA
 *
 */
@RestController
public class AccountRestController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping("/register")
	public AppUser register(@RequestBody RegisterForm userForm){
		if(!userForm.getPassword().equals(userForm.getRepassword())) throw new RuntimeException("confirm your password");
		AppUser user = accountService.findUserByUsername(userForm.getUsername());
		if(user != null) throw new RuntimeException("This user already exist");
		AppUser appUser = new AppUser();
		appUser.setUsername(userForm.getUsername());
		appUser.setPassword(userForm.getPassword());
		
	    accountService.save(appUser);
	    accountService.addRoleToUser(userForm.getUsername(), "USER");
		
	    return appUser;
	}
}

/**
 * 
 */
package com.tsiry.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsiry.org.entities.AppUser;


/**
 * @author Tsiry MANANKASINA
 *
 */
public interface UserRepository extends JpaRepository<AppUser,Long>{
	public AppUser findByUsername(String username);
}

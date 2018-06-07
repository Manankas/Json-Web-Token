/**
 * 
 */
package com.tsiry.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsiry.org.entities.AppRole;


/**
 * @author Tsiry MANANKASINA
 *
 */

public interface RoleRepository extends JpaRepository<AppRole,Long>{
	public AppRole findByRoleName(String roleName);
}

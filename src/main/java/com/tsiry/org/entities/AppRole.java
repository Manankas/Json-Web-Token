/**
 * 
 */
package com.tsiry.org.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tsiry MANANKASINA
 *
 */
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class AppRole {
	@Id @GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String roleName;
}

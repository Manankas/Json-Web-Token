/**
 * 
 */
package com.tsiry.org.entities;

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
public class Task {
	@Id @GeneratedValue
	private Long id;
	private String taskName;
	
}

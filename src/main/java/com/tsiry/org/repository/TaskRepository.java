/**
 * 
 */
package com.tsiry.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsiry.org.entities.Task;

/**
 * @author Tsiry MANANKASINA
 *
 */
public interface TaskRepository extends JpaRepository<Task,Long>{

}

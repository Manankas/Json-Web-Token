package com.tsiry.org;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tsiry.org.entities.AppRole;
import com.tsiry.org.entities.AppUser;
import com.tsiry.org.entities.Task;
import com.tsiry.org.repository.TaskRepository;
import com.tsiry.org.service.AccountService;

@SpringBootApplication
public class JwtSpringApplication implements CommandLineRunner {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private AccountService acountService;
	
	public static void main(String[] args) {
		SpringApplication.run(JwtSpringApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		
		acountService.save(new AppUser(null, "admin", "1234", null));
		acountService.save(new AppUser(null, "rakoto", "123", null));
		acountService.save(new AppUser(null, "rabe", "123", null));
		acountService.saveRole(new AppRole(null, "ADMIN"));
		acountService.saveRole(new AppRole(null, "USER"));
		acountService.addRoleToUser("admin", "ADMIN");
		acountService.addRoleToUser("admin", "USER");
		acountService.addRoleToUser("rakoto", "USER");
		
		Stream.of("T1", "T2" , "T3").forEach(t -> {
			taskRepository.save(new Task(null,t));
		});
		
		taskRepository.findAll().forEach(t ->{
			System.out.println(t.getTaskName());
		});
		
		
	}
}

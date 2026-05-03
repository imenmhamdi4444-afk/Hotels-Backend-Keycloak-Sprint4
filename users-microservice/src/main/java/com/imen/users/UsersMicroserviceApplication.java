package com.imen.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.imen.users.entities.Role;
import com.imen.users.entities.User;
import com.imen.users.service.UserService;
import jakarta.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class UsersMicroserviceApplication {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(UsersMicroserviceApplication.class, args);
	}

	@PostConstruct
	void init_users() {
		try {
			// Get all roles and find ADMIN
			List<Role> allRoles = userService.findAllRoles();
			
			Role adminRole = allRoles.stream()
					.filter(r -> "ADMIN".equals(r.getRole()))
					.findFirst()
					.orElse(null);

			// 1. Ensure ADMIN role exists
			if (adminRole == null) {
				adminRole = userService.addRole(new Role(null, "ADMIN"));
			}

			// 2. Ensure admin user exists
			User admin = userService.findUserByUsername("admin");
			if (admin == null) {
				System.out.println("Creating default admin user...");
				admin = new User();
				admin.setUsername("admin");
				admin.setPassword("123");
				admin.setEnabled(true);
				userService.saveUser(admin);
			}

			// 3. Assign role to admin
			try {
				userService.addRoleToUser("admin", "ADMIN");
				System.out.println("Role ADMIN successfully assigned to admin");
			} catch (Exception e) {
				System.out.println("Admin already has the role.");
			}

		} catch (Exception e) {
			System.out.println("Init failed: " + e.getMessage());
		}
	}
}

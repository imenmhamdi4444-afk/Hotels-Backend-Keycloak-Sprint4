package com.imen.users.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.imen.users.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    java.util.Optional<User> findByEmail(String email);
}

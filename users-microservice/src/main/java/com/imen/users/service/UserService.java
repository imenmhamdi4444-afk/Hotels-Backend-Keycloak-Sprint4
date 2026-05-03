package com.imen.users.service;

import com.imen.users.entities.Role;
import com.imen.users.entities.User;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername (String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String rolename);
    java.util.List<User> findAllUsers();
    java.util.List<Role> findAllRoles();
    User registerUser(com.imen.users.service.RegistrationRequest request);
    User validateToken(String code);
}


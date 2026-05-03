package com.imen.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.imen.users.entities.Role;
import com.imen.users.entities.User;
import com.imen.users.repos.RoleRepository;
import com.imen.users.repos.UserRepository;
import com.imen.users.repos.VerificationTokenRepository;
import com.imen.users.service.EmailSender;
import com.imen.users.service.RegistrationRequest;
import com.imen.users.entities.VerificationToken;
import com.imen.users.service.EmailAlreadyExistsException;
import com.imen.users.service.ExpiredTokenException;
import com.imen.users.service.InvalidTokenException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRep;

    @Autowired
    RoleRepository roleRep;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    VerificationTokenRepository verificationTokenRepo;

    @Autowired
    EmailSender emailSender;

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRep.save(user);
    }

    @Override
    public User addRoleToUser(String username, String rolename) {
        User usr = userRep.findByUsername(username);
        Role r = roleRep.findByRole(rolename);
        usr.getRoles().add(r);
        return usr;
    }

    @Override
    public Role addRole(Role role) {
        return roleRep.save(role);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRep.findByUsername(username);
    }

    @Override
    public java.util.List<User> findAllUsers() {
        return userRep.findAll();
    }

    @Override
    public java.util.List<Role> findAllRoles() {
        return roleRep.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> optionaluser = userRep.findByEmail(request.getEmail());
        if (optionaluser.isPresent())
            throw new EmailAlreadyExistsException("email déjà existant!");

        // Créer et sauvegarder l'utilisateur
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setEnabled(false);
        newUser.setRoles(new ArrayList<>());
        userRep.save(newUser);

        // Ajouter le rôle USER via addRoleToUser
        Role r = roleRep.findByRole("USER");
        if (r == null) {
            r = roleRep.save(new Role(null, "USER"));
        }
        newUser.getRoles().add(r);
        userRep.save(newUser);

        // Générer le code secret
        String code = this.generateCode();
        VerificationToken token = new VerificationToken(code, newUser);
        verificationTokenRepo.save(token);

        // Envoyer par email (ne bloque pas si l'email échoue)
        try {
            sendEmailUser(newUser, token.getToken());
        } catch (Exception e) {
            System.err.println("Email sending failed: " + e.getMessage());
            // On continue quand même - l'utilisateur est créé
        }

        return newUser;
    }

    public String generateCode() {
        Random random = new Random();
        Integer code = 100000 + random.nextInt(900000);
        return code.toString();
    }

    public void sendEmailUser(User u, String code) {
        String emailBody = "Bonjour <h1>" + u.getUsername() + "</h1>" +
                " Votre code de validation est <h1>" + code + "</h1>";
        emailSender.sendEmail(u.getEmail(), emailBody);
    }

    @Override
    public User validateToken(String code) {
        VerificationToken token = verificationTokenRepo.findByToken(code);
        if (token == null) {
            throw new InvalidTokenException("Invalid Token");
        }

        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepo.delete(token);
            throw new ExpiredTokenException("expired Token");
        }
        user.setEnabled(true);
        userRep.save(user);
        return user;
    }
}

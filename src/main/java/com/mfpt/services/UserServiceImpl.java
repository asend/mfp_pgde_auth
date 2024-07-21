package com.mfpt.services;

import com.mfpt.entities.Role;
import com.mfpt.entities.User;
import com.mfpt.entities.VerificationToken;
import com.mfpt.exceptions.ExpiredTokenException;
import com.mfpt.exceptions.InvalidTokenException;
import com.mfpt.repositories.RoleRepository;
import com.mfpt.repositories.UserRepository;
import com.mfpt.repositories.VerificationTokenRepository;
import com.mfpt.utils.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailSender emailSender;

    @Override
    public User create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String code = this.generateCode();
        VerificationToken token = new VerificationToken(code, user);
        verificationTokenRepository.save(token);
        sendEmailUser(user, code);
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User addRoleToUser(String username, String nomRole) {
        User u = userRepository.findByUsername(username);
        Role r = roleRepository.findByNom(nomRole);
        u.getRoles().add(r);
        return u;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    private String generateCode() {
        Random random = new Random();
        Integer code = 100000 + random.nextInt(900000);
        return code.toString();
    }

    @Override
    public void sendEmailUser(User u, String code) {
        String emailBody ="Bonjour "+ "<h1>"+u.getUsername() +"</h1>" +
                " Votre code de validation est "+"<h1>"+code+"</h1>";
        emailSender.sendEmail(u.getEmail(), emailBody);
    }

    @Override
    public User validateToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null){
            throw new InvalidTokenException("Invalid token");
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime())<=0){
            verificationTokenRepository.delete(verificationToken);
            throw new ExpiredTokenException("expired Token");
        }
        user.setEnabled(true);
        userRepository.save(user);
        return user;
    }
}

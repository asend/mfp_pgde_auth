package com.mfpt.services;

import com.mfpt.entities.Role;
import com.mfpt.entities.User;
import com.mfpt.entities.dto.AuthenticationRequest;
import com.mfpt.entities.dto.AuthenticationResponse;

import java.util.List;

public interface UserService {
    User create(User user);
    User findUserByUsername(String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String nomRole);
    List<User> findAll();
    void sendEmailUser(User u, String code);
    User validateToken(String token);

}

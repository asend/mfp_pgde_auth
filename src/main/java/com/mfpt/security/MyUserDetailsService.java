package com.mfpt.security;

import com.mfpt.entities.User;
import com.mfpt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("Utilisateur introuvable!");
        }
        List<GrantedAuthority> auths = new ArrayList<>();

        user.getRoles().forEach(role->{
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getNom());
            auths.add(authority);
        });
        // user.getEnabled(), true, true, true,
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), auths);
    }
}

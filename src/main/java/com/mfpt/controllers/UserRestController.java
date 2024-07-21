package com.mfpt.controllers;

import com.mfpt.entities.User;
import com.mfpt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserRestController {
    @Autowired
    UserService userService;

    @GetMapping("/alls")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/{username}")
    public User getByUsername(@PathVariable("username") String username){
        return userService.findUserByUsername(username);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.create(user);

    }

    @GetMapping("/verifyEmail/{token}")
    public User verifyEmail(@PathVariable("token") String token){
        return userService.validateToken(token);
    }


}

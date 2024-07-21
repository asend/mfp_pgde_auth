package com.mfpt;

import com.mfpt.entities.Role;
import com.mfpt.entities.User;
import com.mfpt.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PgdeMicroserviceUserApplication {

    @Autowired
    UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(PgdeMicroserviceUserApplication.class, args);
    }

    /*@PostConstruct
    void init_user(){
        userService.addRole(new Role(null,"ADMIN", "ADMIN"));
        userService.addRole(new Role(null,"USER", "USER"));

        userService.create(new User(null,"admin","admin@gmail.com","123","admin","admin","12345",true,null));
        userService.create(new User(null,"nadhem","nadhem@gmail.com","123","nadhem","nadhem","12345",true,null));
        userService.create(new User(null,"yassine","yassine@gmail.com","123","yassine","yassine","12345",true,null));

        userService.addRoleToUser("admin", "ADMIN");
        userService.addRoleToUser("nadhem", "USER");
        userService.addRoleToUser("yassine", "USER");
    }*/

}

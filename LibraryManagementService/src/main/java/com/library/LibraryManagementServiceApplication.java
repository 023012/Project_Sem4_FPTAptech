package com.library;

import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LibraryManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementServiceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(UserService userService, UserRepository userRepository) {
        return args -> {
            List<User> listUserExisted = userRepository.findAll();
            if (listUserExisted == null) {
                userService.saveUser(new User("John Deep", "john", "1234", "john@gmail.com", "053453455", "Hanoi" , "1.png", 500000, User.AccountStatus.ACTIVE, new ArrayList<>()));
                userService.saveUser(new User("Will Smith", "will", "1234", "will@gmaol.com", "053453455", "Hanoi", "2.png",  50000,User.AccountStatus.BLACKLISTED, new ArrayList<>()));
                userService.saveUser(new User("Jim Carry", "jim", "1234", "th.dat1230@gmail.com","053453455",  "Hanoi" , "3.png", 50000,User.AccountStatus.CANCELED, new ArrayList<>()));
                userService.saveUser(new User("Thanh Dat","dat","023012","ntd.dat1230@gmail.com","0379651398","Hanoi","1.png",50000, User.AccountStatus.ACTIVE, new ArrayList<>()));


                //userService.addRoleToUser("john@gmail.com", "ROLE_USER");
                userService.addRoleToUser("will@gmaol.com", "USER");
                userService.addRoleToUser("th.dat1230@gmail.com", "USER");
                userService.addRoleToUser("ntd.dat1230@gmail.com", "USER");
                //ADMIN  LIBRARIAN  MEMBER  USER
                userService.addRoleToUser("ntd.dat1230@gmail.com", "ADMIN");
                userService.addRoleToUser("john@gmail.com", "USER");
            }
        };
    }

}
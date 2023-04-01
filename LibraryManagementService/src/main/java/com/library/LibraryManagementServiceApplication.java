package com.library;

import com.library.entity.User;
import com.library.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LibraryManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner( UserRepository userRepository) {
        return args -> {
            List<User> listUserExisted = userRepository.findAll();
            if (listUserExisted == null) {
                userRepository.save(new User("dat","ntd.dat1230@gmail.com","023012"));
                userRepository.save(new User("dung","vandung123@gmail.com","222222"));
                userRepository.save(new User("tuan","minhtuan113@gmail.com","111111"));
            }
        };
    }

}

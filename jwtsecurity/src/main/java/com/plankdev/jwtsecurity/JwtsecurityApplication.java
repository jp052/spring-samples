package com.plankdev.jwtsecurity;

import com.plankdev.jwtsecurity.model.Authority;
import com.plankdev.jwtsecurity.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class JwtsecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtsecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, AuthorityRespository authorityRespository) {
        System.out.println("Spring Version: " + SpringVersion.getVersion());
        return (evt) -> {


            User user = new User("Someone", new BCryptPasswordEncoder().encode("password"));
            user.setEnabled(true);
            User admin = new User("Admin", new BCryptPasswordEncoder().encode("password"));
            admin.setEnabled(true);

            Authority userAuthority = new Authority("ROLE_USER");
            Authority adminAuthority = new Authority("ROLE_ADMIN");


            User savedUser = userRepository.save(user);
            User savedAdmin = userRepository.save(admin);

            Authority savedUserAuth = authorityRespository.save(userAuthority);
            Authority savedAdminAuth = authorityRespository.save(adminAuthority);

            savedUserAuth.addUser(savedUser);
            savedAdminAuth.addUser(savedAdmin);
            savedUserAuth.addUser(savedAdmin);

            userRepository.save(savedUser);
            userRepository.save(savedAdmin);





            /*Just sample code:
            for (Authority authority : form.getAuthorities()) {
                Authority _authority = authorityRepository.findByAuthority(authority.getAuthority());
                saved.getAuthorities().add(_authority);
            }

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(saved, saved.getPassword(), saved.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);*/

        };
    }

}


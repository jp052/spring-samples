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
            //Create user and Admin authority
            //create admin user with admin authority
            //create user with user authority



            /*for (Authority authority : form.getAuthorities()) {
                Authority _authority = authorityRepository.findByAuthority(authority.getAuthority());
                saved.getAuthorities().add(_authority);
            }

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(saved, saved.getPassword(), saved.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            User user = userRepository.save(new User("Someone", new BCryptPasswordEncoder().encode("password")));
            User admin = userRepository.save(new User("Admin", new BCryptPasswordEncoder().encode("password")));

            Authority userAuthority = authorityRespository.save(new Authority("USER"));

            user.getAuthorities().add()


            Authority adminAuthority = new Authority("ADMIN");
            Authority userAuthority = new Authority("USER");
            authorityRespository.save(adminAuthority);
            authorityRespository.save(userAuthority);



            userRepository.save(admin);
            userRepository.save(user);*/

        };
    }

}


package com.plankdev.jwtsecurity;

import com.plankdev.jwtsecurity.dataaccess.AppUser;
import com.plankdev.jwtsecurity.dataaccess.Authority;
import com.plankdev.jwtsecurity.dataaccess.AuthorityRespository;
import com.plankdev.jwtsecurity.dataaccess.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtsecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtsecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, AuthorityRespository authorityRespository) {
        System.out.println("Spring Version: " + SpringVersion.getVersion());
        return (evt) -> {


            AppUser appUser = new AppUser("user", new BCryptPasswordEncoder().encode("password"));
            appUser.setEnabled(true);
            AppUser admin = new AppUser("admin", new BCryptPasswordEncoder().encode("password"));
            admin.setEnabled(true);

            Authority userAuthority = new Authority("ROLE_USER");
            Authority adminAuthority = new Authority("ROLE_ADMIN");


            AppUser savedAppUser = userRepository.save(appUser);
            AppUser savedAdmin = userRepository.save(admin);

            Authority savedUserAuth = authorityRespository.save(userAuthority);
            Authority savedAdminAuth = authorityRespository.save(adminAuthority);

            savedUserAuth.addUser(savedAppUser);
            savedAdminAuth.addUser(savedAdmin);
            savedUserAuth.addUser(savedAdmin);

            userRepository.save(savedAppUser);
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


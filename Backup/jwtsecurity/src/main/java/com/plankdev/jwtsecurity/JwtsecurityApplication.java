package com.plankdev.jwtsecurity;

import com.plankdev.jwtsecurity.security.dataaccess.ApiUser;
import com.plankdev.jwtsecurity.security.dataaccess.Authority;
import com.plankdev.jwtsecurity.security.dataaccess.AuthorityRespository;
import com.plankdev.jwtsecurity.security.dataaccess.ApiUserRepository;
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
    CommandLineRunner init(ApiUserRepository apiUserRepository, AuthorityRespository authorityRespository) {
        System.out.println("Spring Version: " + SpringVersion.getVersion());
        return (evt) -> {


            ApiUser apiUser = new ApiUser("user", new BCryptPasswordEncoder().encode("password"));
            apiUser.setEnabled(true);
            ApiUser admin = new ApiUser("admin", new BCryptPasswordEncoder().encode("password"));
            admin.setEnabled(true);

            Authority userAuthority = new Authority("ROLE_USER");
            Authority adminAuthority = new Authority("ROLE_ADMIN");


            ApiUser savedApiUser = apiUserRepository.save(apiUser);
            ApiUser savedAdmin = apiUserRepository.save(admin);

            Authority savedUserAuth = authorityRespository.save(userAuthority);
            Authority savedAdminAuth = authorityRespository.save(adminAuthority);

            savedUserAuth.addUser(savedApiUser);
            savedAdminAuth.addUser(savedAdmin);
            savedUserAuth.addUser(savedAdmin);

            apiUserRepository.save(savedApiUser);
            apiUserRepository.save(savedAdmin);





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


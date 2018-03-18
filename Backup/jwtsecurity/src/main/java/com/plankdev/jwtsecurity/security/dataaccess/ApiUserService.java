package com.plankdev.jwtsecurity.security.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.plankdev.jwtsecurity.security.dataaccess.AuthorityRespository.ROLE_USER_DB_NAME;

@Service
public class ApiUserService {

    private ApiUserRepository userRepo;

    private AuthorityRespository authorityRespo;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public ApiUserService(ApiUserRepository userRepo, AuthorityRespository authorityRespo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authorityRespo = authorityRespo;
        this.passwordEncoder = passwordEncoder;
    }

    public ApiUser findByUsername(String username) throws UsernameNotFoundException {
        ApiUser u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));
        return u;
    }

    public ApiUser findById(Long id) throws AccessDeniedException {
        ApiUser u = userRepo.findOne(id);
        return u;
    }

    public List<ApiUser> findAll() throws AccessDeniedException {
        List<ApiUser> result = userRepo.findAll();
        return result;
    }

    public Optional<ApiUser> createUser(ApiUser apiUser) {
        apiUser.setEnabled(true);

        String plainPassword = apiUser.getPassword();
        if (plainPassword == null) {
            throw new NullPointerException("Password needs to be set, check json ignore field");
        }
        String encodedPassword = passwordEncoder.encode(apiUser.getPassword());
        apiUser.setPassword(encodedPassword);

        Optional<ApiUser> createdUserOpt = Optional.of(userRepo.save(apiUser));

        //add apiUser to Authority
        Optional<Authority> userRoleOpt = authorityRespo.findByName(ROLE_USER_DB_NAME);
        Authority authority = userRoleOpt.orElseThrow(() -> new EntityNotFoundException("user authority not found: " + ROLE_USER_DB_NAME));
        authority.addUser(createdUserOpt.get());
        authorityRespo.save(authority);

        return createdUserOpt;
    }

    public Optional<ApiUser> updateUser(ApiUser apiUser) {
        ApiUser oldApiUser = userRepo.findOne(apiUser.getId());
        Optional<ApiUser> updatedUser = Optional.of(userRepo.save(apiUser));
        return updatedUser;
    }

    public void deleteUser(Long userId) {
        if (userId == null) {
            throw new NullPointerException("id needs to be set for user");
        }

        userRepo.delete(userId);
    }
}

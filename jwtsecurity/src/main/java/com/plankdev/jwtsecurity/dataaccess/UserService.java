package com.plankdev.jwtsecurity.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.plankdev.jwtsecurity.dataaccess.AuthorityRespository.ROLE_USER_DB_NAME;

@Service
public class UserService {

    private UserRepository userRepo;

    private AuthorityRespository authorityRespo;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepo, AuthorityRespository authorityRespo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authorityRespo = authorityRespo;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser findByUsername(String username) throws UsernameNotFoundException {
        AppUser u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));
        return u;
    }

    public AppUser findById(Long id) throws AccessDeniedException {
        AppUser u = userRepo.findOne(id);
        return u;
    }

    public List<AppUser> findAll() throws AccessDeniedException {
        List<AppUser> result = userRepo.findAll();
        return result;
    }

    public Optional<AppUser> createUser(AppUser appUser) {
        appUser.setEnabled(true);

        String plainPassword = appUser.getPassword();
        if (plainPassword == null) {
            throw new NullPointerException("Password needs to be set, check json ignore field");
        }
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        Optional<AppUser> createdUserOpt = Optional.of(userRepo.save(appUser));

        //add appUser to Authority
        Optional<Authority> userRoleOpt = authorityRespo.findByName(ROLE_USER_DB_NAME);
        Authority authority = userRoleOpt.orElseThrow(() -> new EntityNotFoundException("user authority not found: " + ROLE_USER_DB_NAME));
        authority.addUser(createdUserOpt.get());
        authorityRespo.save(authority);

        return createdUserOpt;
    }

    public Optional<AppUser> updateUser(AppUser appUser) {
        AppUser oldAppUser = userRepo.findOne(appUser.getId());
        Optional<AppUser> updatedUser = Optional.of(userRepo.save(appUser));
        return updatedUser;
    }

    public void deleteUser(Long userId) {
        if (userId == null) {
            throw new NullPointerException("id needs to be set for user");
        }

        userRepo.delete(userId);
    }
}

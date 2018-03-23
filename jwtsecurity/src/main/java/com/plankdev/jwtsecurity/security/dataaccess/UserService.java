package com.plankdev.jwtsecurity.security.dataaccess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.plankdev.jwtsecurity.security.exception.UserNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.plankdev.jwtsecurity.security.dataaccess.AuthorityRespository.ROLE_USER_DB_NAME;

@Service
public class UserService {
	
	protected final Log LOGGER = LogFactory.getLog(getClass());

    private UserRepository userRepo;

    private AuthorityRespository authorityRespo;
    
    @Autowired
    //@Lazy //Bugfix for circular dependency in AuthenticationRestController.
    private AuthenticationManager authenticationManager;
    
    //@Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepo, AuthorityRespository authorityRespo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authorityRespo = authorityRespo;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser findByUsername(String username) {
        AppUser u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
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
    
    public void changePassword(String oldPassword, String newPassword) {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        if (authenticationManager != null) {
            LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            LOGGER.debug("No authentication manager set. can't change Password!");

            return;
        }

        LOGGER.debug("Changing password for user '" + username + "'");

        AppUser appUser = this.findByUsername(username);

        appUser.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(appUser);

    }
}

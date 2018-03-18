package com.plankdev.jwtsecurity.security.springsecurity;

import com.plankdev.jwtsecurity.security.dataaccess.ApiUser;
import com.plankdev.jwtsecurity.security.dataaccess.ApiUserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private ApiUserRepository apiUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy //Bugfix for circular dependency in AuthenticationRestController.
    private AuthenticationManager authenticationManager;

    @Override
    //@Transactional(readOnly=true) is Transactional needed?
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApiUser apiUser = apiUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));

        return apiUser;
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

        ApiUser apiUser = (ApiUser) loadUserByUsername(username);

        apiUser.setPassword(passwordEncoder.encode(newPassword));
        apiUserRepository.save(apiUser);

    }
}
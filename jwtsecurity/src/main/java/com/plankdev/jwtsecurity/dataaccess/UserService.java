package com.plankdev.jwtsecurity.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.plankdev.jwtsecurity.dataaccess.AuthorityRespository.ROLE_USER_DB_NAME;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRespository authorityRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username ) throws UsernameNotFoundException {
        User u = userRepository.findByUsername( username )
                .orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));
        return u;
    }

    public User findById( Long id ) throws AccessDeniedException {
        User u = userRepository.findOne( id );
        return u;
    }

    public List<User> findAll() throws AccessDeniedException {
        List<User> result = userRepository.findAll();
        return result;
    }

    public Optional<User> createUser(User user) {
        user.setEnabled(true);

        String plainPassword = user.getPassword();
        if(plainPassword == null) {
            throw new NullPointerException("Password needs to be set, check json ignore field");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Optional<User> createdUserOpt = Optional.of(userRepository.save(user));

        //add user to Authority
        Optional<Authority> userRoleOpt = authorityRespository.findByName(ROLE_USER_DB_NAME);
        Authority authority = userRoleOpt.orElseThrow(() -> new EntityNotFoundException("user authority not found: " + ROLE_USER_DB_NAME));
        authority.addUser(createdUserOpt.get());
        authorityRespository.save(authority);

        return createdUserOpt;
    }

    public  Optional<User> updateUser(User user) {
        User oldUser = userRepository.findOne(user.getId());
        Optional<User> updatedUser = Optional.of(userRepository.save(user));
        return updatedUser;
    }

    public void deleteUser(Long userId) {
        if(userId == null) {
            throw new NullPointerException("id needs to be set for user");
        }

        userRepository.delete(userId);
    }
}

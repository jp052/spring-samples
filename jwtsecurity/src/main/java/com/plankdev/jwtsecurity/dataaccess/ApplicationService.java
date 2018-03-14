package com.plankdev.jwtsecurity.dataaccess;

import com.plankdev.jwtsecurity.jwt.TokenHelper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ApplicationService {
    private ApplicationRepository applicationRepo;
    private ApiKeyRepository apiKeyRepo;
    private UserRepository userRepo;

    private TokenHelper tokenHelper;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepo, ApiKeyRepository apiKeyRepo, UserRepository userRepo, TokenHelper tokenHelper) {
        this.applicationRepo = applicationRepo;
        this.apiKeyRepo = apiKeyRepo;
        this.userRepo = userRepo;
        this.tokenHelper = tokenHelper;
    }
    /*
    Lazy init problem: der current user wird aus CustomUserDetailService geladen. Wenn später darauf zugegriffen werden soll, ist die session schon geschlossen.
     */
    public Optional<Application> createApplication(Application application) {
        //TODO: null handling
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser userDetail = (AppUser)authentication.getPrincipal();
        String jwtToken = tokenHelper.generateToken(userDetail.getUsername());

        AppUser currentUserInSession = userRepo.findOne(userDetail.getId());

        ApiKey apiKey = new ApiKey();
        apiKey.setApiKeyToken(jwtToken);
        apiKey.setActive(true);

        ApiKey createdApiKey = apiKeyRepo.save(apiKey);
        application.setApiKey(createdApiKey);
        currentUserInSession.addApplication(application);

        //userRepo.save(currentUserInSession);
        Optional<Application> applicationOpt = Optional.of(applicationRepo.save(application));

        return applicationOpt;
    }
}

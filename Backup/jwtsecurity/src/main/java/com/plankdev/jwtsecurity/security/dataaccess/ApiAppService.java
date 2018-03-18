package com.plankdev.jwtsecurity.security.dataaccess;

import com.plankdev.jwtsecurity.security.jwt.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApiAppService {
    private ApiAppRepository apiAppRepo;
    private ApiKeyRepository apiKeyRepo;
    private ApiUserRepository userRepo;

    private TokenHelper tokenHelper;

    @Autowired
    public ApiAppService(ApiAppRepository apiAppRepo, ApiKeyRepository apiKeyRepo, ApiUserRepository userRepo, TokenHelper tokenHelper) {
        this.apiAppRepo = apiAppRepo;
        this.apiKeyRepo = apiKeyRepo;
        this.userRepo = userRepo;
        this.tokenHelper = tokenHelper;
    }
    /*
    Lazy init problem: der current user wird aus CustomUserDetailService geladen. Wenn später darauf zugegriffen werden soll, ist die session schon geschlossen.
     */
    public Optional<ApiApp> createApplication(ApiApp apiApp) {
        //TODO: null handling
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ApiUser userDetail = (ApiUser)authentication.getPrincipal();
        String jwtToken = tokenHelper.generateToken(userDetail.getUsername());

        ApiUser currentUserInSession = userRepo.findOne(userDetail.getId());

        ApiKey apiKey = new ApiKey();
        apiKey.setApiKeyToken(jwtToken);
        apiKey.setActive(true);

        ApiKey createdApiKey = apiKeyRepo.save(apiKey);
        apiApp.setApiKey(createdApiKey);
        currentUserInSession.addApiApp(apiApp);

        //userRepo.save(currentUserInSession);
        Optional<ApiApp> apiAppOpt = Optional.of(apiAppRepo.save(apiApp));

        return apiAppOpt;
    }
}

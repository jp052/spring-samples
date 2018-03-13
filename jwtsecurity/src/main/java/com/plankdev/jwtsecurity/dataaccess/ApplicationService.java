package com.plankdev.jwtsecurity.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationService {
    private ApplicationRepository applicationRepo;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepo) {
        this.applicationRepo = applicationRepo;
    }

    public Optional<Application> createApplication(Application application) {
        //TODO: create and save api key to db

        //Authentication object aus SecurityContext auslesen
        //Token erstellen:
        //User user = (User)authentication.getPrincipal();
        //String jws = tokenHelper.generateToken( user.getUsername());
        //Token in APIKey ablegen und speichern
        //ApiKey in Application setzen und speichern


        ApiKey apiKey = new ApiKey();
        apiKey.setId(1L);
        apiKey.setApiKeyToken("testToken");
        apiKey.setActive(true);

        application.setApiKey(apiKey);
        Optional<Application> applicationOpt = Optional.of(application);

        return applicationOpt;
    }
}

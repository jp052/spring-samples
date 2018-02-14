package com.planksoftware.springmvcresthateoasjpa.bookmarks;

import com.planksoftware.springmvcresthateoasjpa.SpringmvcRestHateoasJpaApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringmvcRestHateoasJpaApplication.class, ApplicationSecurityTest.ExtraConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationSecurityTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Ignore
    @Test
    public void passwordGrant() {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
        request.set("username", "jlong");
        request.set("password", "password");
        request.set("grant_type", "password");
        Map<String, Object> token = testRestTemplate
                .postForObject("/oauth/token", request, Map.class);
        assertNotNull("Wrong response: " + token, token.get("access_token"));
    }

    @TestConfiguration
    public static class ExtraConfig {

        @Bean
        RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder()
                    .basicAuthorization("android-bookmarks", "123456");
        }
    }
}

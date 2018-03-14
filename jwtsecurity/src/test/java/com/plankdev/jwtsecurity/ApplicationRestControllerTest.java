package com.plankdev.jwtsecurity;

import com.plankdev.jwtsecurity.dataaccess.Application;
import org.junit.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ApplicationRestControllerTest extends BaseRestControllerTest {


    @Test
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "customUserDetailsService")
    //CustomUserDetailsService needed because the appUser needs to be loaded from SecurityContext and not the standard spring user.
    public void shouldCreateApplicationAndReturnNewApiKey() throws Exception {
        //assemble
        //TODO: read userid from db, dont assume it is 1
        final String APP_NAME_EXPECTED = "myApp1";
        Application app = new Application();
        app.setName(APP_NAME_EXPECTED);
        String applicationJson = jsonUtils.pojoToJson(app);

        //action
        ResultActions performRequest = mockMvc.perform(post("/api/users/1/applications")
                .contentType(contentType)
                .content(applicationJson))
                .andDo(print());

        //assert
        performRequest
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(APP_NAME_EXPECTED))
                //.andExpect(jsonPath("$.appUser.username").exists())
                .andExpect(jsonPath("$.apiKey.apiKeyToken").exists());
    }


    @Test
    @WithAnonymousUser
    public void shouldGetUnauthorizedWithoutRole() throws Exception {

        mockMvc.perform(get("/api/users/1/applications"))
                .andExpect(status().isUnauthorized());
    }

}

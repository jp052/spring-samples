package com.plankdev.jwtsecurity;

import com.plankdev.jwtsecurity.dataaccess.Application;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

public class ApplicationRestControllerTest extends BaseRestControllerTest {

    //user token needed and new token for application returned and saved in DB
    @Test
    @WithMockUser(roles = "USER")
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
                .andExpect(jsonPath("$.name").value(APP_NAME_EXPECTED))
                .andExpect(jsonPath("$.apiKey.apiKeyToken").exists());
    }


    @Test
    @WithAnonymousUser
    public void shouldGetUnauthorizedWithoutRole() throws Exception {

        mockMvc.perform(get("/api/users/1/applications"))
                .andExpect(status().isUnauthorized());
    }

}

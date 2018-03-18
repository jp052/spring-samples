package com.plankdev.jwtsecurity.api;

import com.plankdev.jwtsecurity.testutils.BaseRestControllerTest;
import org.junit.Test;


public class VehicleRestControllerTest extends BaseRestControllerTest {

    @Test
    public void dummyTest() {}

/*    @Test
    @WithMockUser(value = "user")
    public void createVehicleWorksWhenAllValuesSet() throws Exception {
        //assemble
        //TODO: read userid from db, dont assume it is 1
        final String APP_NAME_EXPECTED = "myApp1";
        ApiApp app = new ApiApp();
        app.setName(APP_NAME_EXPECTED);
        String applicationJson = jsonUtils.pojoToJson(app);

        //action
        ResultActions performRequest = mockMvc.perform(post("/api/v1/vehicles")

        Vehicle vehicle = new Vehicle();
        vehicle.setName("car1");
        vehicle.setApiApp("myApp1");
    }

    private ApiApp createApplication() throws Exception {
        final String APP_NAME_EXPECTED = "myApp1";
        ApiApp newApp = new ApiApp();
        newApp.setName(APP_NAME_EXPECTED);
        String applicationJson = jsonUtils.pojoToJson(newApp);

        MvcResult createApplicationResult = mockMvc.perform(post("/api/users/1/applications")
                .contentType(contentType)
                .content(applicationJson))
                .andReturn();

        String applicationJsonAsString = createApplicationResult.getResponse().getContentAsString();
        ApiApp createdApp = jsonUtils.jsonStringToPojo(applicationJsonAsString, ApiApp.class);
        return createdApp;
    }*/



    //createVehicleFailsWhenApplicationDoesNotMatchApiKey
}

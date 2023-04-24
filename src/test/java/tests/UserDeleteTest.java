package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Deletion cases")
public class UserDeleteTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Description("This test trying to delete a user by ID 2")
    @DisplayName("Delete a user by ID 2")
    @Feature("Deletion negative")
    @Test
    public void testDeleteUserByID2() {

        //LOGIN
        Map<String, String> userData = new HashMap<>();
        userData.put("email", "vinkotov@example.com");
        userData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", userData);

        String cookie = getCookie(responseGetAuth, "auth_sid");
        String header = getHeader(responseGetAuth, "x-csrf-token");
        int id = getIntFromJson(responseGetAuth, "user_id");

        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + id, header, cookie);

        String idInitial =  responseUserData.jsonPath().getString("id");
        String firstNameInitial =  responseUserData.jsonPath().getString("firstName");
        String usernameInitial =  responseUserData.jsonPath().getString("username");
        String lastNameInitial =  responseUserData.jsonPath().getString("lastName");
        String emailInitial =  responseUserData.jsonPath().getString("email");


        //TRY DELETE
        Response responseDELETEUser = apiCoreRequests
                .makeDELETERequest("https://playground.learnqa.ru/api/user/" + id, header, cookie);

        responseDELETEUser.prettyPrint();

        //CHECK RESULTS
        Assertions.assertResponseTextContains(responseDELETEUser, "Please, do not delete test users with ID 1, 2, 3, 4 or 5");
        Assertions.assertResponseCodeEquals(responseDELETEUser, 400);

        Response responseUserData2 = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + id, header, cookie);

        Assertions.assertResponseCodeEquals(responseUserData2, 200);
        Assertions.assertJsonByName(responseUserData, "id", idInitial);
        Assertions.assertJsonByName(responseUserData, "firstName", firstNameInitial);
        Assertions.assertJsonByName(responseUserData, "username", usernameInitial);
        Assertions.assertJsonByName(responseUserData, "lastName", lastNameInitial);
        Assertions.assertJsonByName(responseUserData, "email", emailInitial);


    }

    @Description("This test trying to delete a newly created user")
    @DisplayName("Deleting a newly created user")
    @Feature("Deletion positive")
    @Test
    public void testDeletingNewlyCreatedUser() {

        String id;

        //GENERATE USER & REGISTRATION
        Map<String, String> userData = new HashMap<>();
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        id = responseCreateAuth.jsonPath().getString("id");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", userData);

        String cookie = getCookie(responseGetAuth, "auth_sid");
        String header = getHeader(responseGetAuth, "x-csrf-token");


        //TRY DELETE
        Response responseDELETEUser = apiCoreRequests
                .makeDELETERequest("https://playground.learnqa.ru/api/user/" + id, header, cookie);

        responseDELETEUser.prettyPrint();

        //CHECK RESULTS

        Assertions.assertResponseCodeEquals(responseDELETEUser, 200);

        Response responseUserData2 = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + id, header, cookie);

        Assertions.assertResponseCodeEquals(responseUserData2, 404);
        Assertions.assertJsonHasNotField(responseUserData2, "id");
        Assertions.assertJsonHasNotField(responseUserData2, "username");
        Assertions.assertJsonHasNotField(responseUserData2, "firstName");
        Assertions.assertJsonHasNotField(responseUserData2, "lastName");
        Assertions.assertJsonHasNotField(responseUserData2, "email");


    }

    @Description("This test trying to delete the user while being logged in by another user")
    @DisplayName("Delete User by another user")
    @Feature("Deletion negative")
    @Test
    public void testDeleteUserByAnotherUser() {


        String userId;

        //============GENERATE USER & REGISTRATION (FIRST USER)============
        Map<String, String> firstUserData = new HashMap<>();
        //   firstUserData.put("firstName", currentName);
        firstUserData = DataGenerator.getRegistrationData(firstUserData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", firstUserData);

        userId = responseCreateAuth.jsonPath().getString("id");


        //============LOGIN BY SECOND USER============
        Map<String, String> secondUserData = new HashMap<>();
        secondUserData.put("email", "vinkotov@example.com");
        secondUserData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", secondUserData);

        String cookieSecondUser = getCookie(responseGetAuth, "auth_sid");
        String headerSecondUser = getHeader(responseGetAuth, "x-csrf-token");
        int idSecondUser = getIntFromJson(responseGetAuth, "user_id");

        //============TRY DELETE BY SECOND USER============
        Response responseDELETEUser = apiCoreRequests
                .makeDELETERequest("https://playground.learnqa.ru/api/user/" + userId, headerSecondUser, cookieSecondUser);

        responseDELETEUser.prettyPrint();

        Assertions.assertResponseCodeEquals(responseDELETEUser, 400);

        //============LOGIN BY FIRST USER============
        Map<String, String> authDataFirst = new HashMap<>();
        authDataFirst.put("email", firstUserData.get("email"));
        authDataFirst.put("password", firstUserData.get("password"));

        Response responseGetAuth2 = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authDataFirst);

        String cookieFirstUser = getCookie(responseGetAuth2, "auth_sid");
        String headerFirstUser = getHeader(responseGetAuth2, "x-csrf-token");


        //============CHECK FIRSTNAME FIELDS BY FIRST USER============
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, headerFirstUser, cookieFirstUser);

        Assertions.assertResponseCodeEquals(responseUserData, 200);
        Assertions.assertJsonByName(responseUserData, "id", userId);
        Assertions.assertJsonByName(responseUserData, "firstName", firstUserData.get("firstName"));
        Assertions.assertJsonByName(responseUserData, "username", firstUserData.get("username"));
        Assertions.assertJsonByName(responseUserData, "lastName", firstUserData.get("lastName"));
        Assertions.assertJsonByName(responseUserData, "email", firstUserData.get("email"));

    }

}


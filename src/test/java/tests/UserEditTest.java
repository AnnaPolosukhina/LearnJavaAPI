package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserEditTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Description("This test edit field firstName in just created user")
    @DisplayName("Edit Just Created User")
    @Test
    public void testEditJustCreatedTest() {

        String newName = "Changed Name";

        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateAuth.jsonPath().getString("id");


        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);


        //EDIT

        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        String cookie = this.getCookie(responseGetAuth, "auth_sid");
        String header = this.getHeader(responseGetAuth, "x-csrf-token");

        Response responseEditUser = apiCoreRequests
                .makePUTRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie, editData);


        //GET

        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie);

        Assertions.assertJsonByName(responseUserData, "firstName", newName);

    }

    @Description("We will try to change the user data, being unauthorized")
    @DisplayName("Edit Just Created User Being Unauthorized")
    @Test
    public void testEditUserByUnauthorized() {

        String userId;

        //GENERATE USER & REGISTRATION
        Map<String, String> userData = new HashMap<>();
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        userId = responseCreateAuth.jsonPath().getString("id");

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");


        //EDIT FIRSTNAME FIELD

        Map<String, String> editData = DataGenerator.getRegistrationAnotherData();

        Response responseEditUser = apiCoreRequests
                .makePUTRequestWithUrlOnly("https://playground.learnqa.ru/api/user/" + userId, editData);

        Assertions.assertResponseTextContains(responseEditUser, "Auth token not supplied");
        Assertions.assertResponseCodeEquals(responseEditUser, 400);


        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        String cookie = this.getCookie(responseGetAuth, "auth_sid");
        String header = this.getHeader(responseGetAuth, "x-csrf-token");


        //CHECK FIRSTNAME FIELD
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie);

        Assertions.assertJsonByName(responseUserData, "firstName", userData.get("firstName"));
        Assertions.assertJsonByName(responseUserData, "username", userData.get("username"));
        Assertions.assertJsonByName(responseUserData, "lastName", userData.get("lastName"));
        Assertions.assertJsonByName(responseUserData, "email", userData.get("email"));

    }

    @Description("We will try to change the data of the user, being authorized by another user")
    @DisplayName("Edit User by another user")
    @Test
    public void testEditUserByAnotherUser() {


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

        //============EDIT FIRSTNAME FIELD BY SECOND USER============

        Map<String, String> changeUserData = DataGenerator.getRegistrationAnotherData();

        Response responseEditUser = apiCoreRequests
                .makePUTRequest("https://playground.learnqa.ru/api/user/" + userId, headerSecondUser, cookieSecondUser, changeUserData);

        Assertions.assertResponseCodeEquals(responseEditUser, 400);

        //============LOGIN BY FIRST USER============
        Map<String, String> authDataFirst = new HashMap<>();
        authDataFirst.put("email", firstUserData.get("email"));
        authDataFirst.put("password", firstUserData.get("password"));

        Response responseGetAuth2 = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authDataFirst);

        String cookieFirstUser = getCookie(responseGetAuth2, "auth_sid");
        String headerFirstUser = getHeader(responseGetAuth2, "x-csrf-token");


        //============CHECK FIRSTNAME FIELD BY FIRST USER============
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, headerFirstUser, cookieFirstUser);

        Assertions.assertJsonByName(responseUserData, "firstName", firstUserData.get("firstName"));
        Assertions.assertJsonByName(responseUserData, "username", firstUserData.get("username"));
        Assertions.assertJsonByName(responseUserData, "lastName", firstUserData.get("lastName"));
        Assertions.assertJsonByName(responseUserData, "email", firstUserData.get("email"));

    }

    @Description("This test try to change the user's email, being authorized by the same user, to a new email without the @ symbol")
    @DisplayName("Edit new email without the @ symbol")
    @Test
    public void testEditNewEmailWithoutAtSymbol() {

        String newEmail = DataGenerator.getRandomEmailWithoutAtSign();

        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateAuth.jsonPath().getString("id");


        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);


        //EDIT

        Map<String, String> editData = new HashMap<>();
        editData.put("email", newEmail);

        String cookie = this.getCookie(responseGetAuth, "auth_sid");
        String header = this.getHeader(responseGetAuth, "x-csrf-token");

        Response responseEditUser = apiCoreRequests
                .makePUTRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie, editData);

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTextEquals(responseEditUser, "Invalid email format");

        //GET

        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie);

        Assertions.assertJsonByName(responseUserData, "email", userData.get("email"));

    }

    @Description("This test try to change the firstName of the user, being authorized by the same user, to a very short value of one character")
    @DisplayName("Edit firstName of one character")
    @Test
    public void testEditFirstNameByShort() {

        String newFirstName = DataGenerator.getRandomString(1);

        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateAuth.jsonPath().getString("id");


        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);


        //EDIT

        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newFirstName);

        String cookie = this.getCookie(responseGetAuth, "auth_sid");
        String header = this.getHeader(responseGetAuth, "x-csrf-token");

        Response responseEditUser = apiCoreRequests
                .makePUTRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie, editData);


        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTextEqualsByKey(responseEditUser, "error", "Too short value for field firstName");


        //GET

        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie);

        Assertions.assertJsonByName(responseUserData, "firstName", userData.get("firstName"));

    }

}


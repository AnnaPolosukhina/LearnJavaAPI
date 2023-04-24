package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import lib.ApiCoreRequests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Epic("Registration cases")
public class UserRegisterTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Description("This test tries to create a registration with an already taken email")
    @DisplayName("Test negative registration by existing email")
    @Feature("Registration negative")
    @Test
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
    }

    @Description("This test creates a registration from uniquely generated data")
    @DisplayName("Test positive registration")
    @Feature("Registration positive")
    @Test
    public void testCreateUserSuccessfully() {
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");

    }

    @Description("This test creates a registration with invalid email - No '@' symbol")
    @DisplayName("Test negative registration - No @ in Email")
    @Feature("Registration negative")
    @Test
    public void testCreateUserWithInvalidEmailWithoutAtSign () {
        String email = DataGenerator.getRandomEmailWithoutAtSign();

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");

    }

    @Description("This test creates a registration with missing fields")
    @DisplayName("Test negative registration - with missing fields")
    @Feature("Registration negative")
    @ParameterizedTest
    @ValueSource(strings = { "username", "password", "firstName", "lastName", "email" })
    public void testCreateUserWithMissingFields (String key) {

        Map<String, String> userData = new HashMap<>();
        userData.put(key, null);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextContains(responseCreateAuth, "The following required params are missed: "+key);
    }


    @Description("This test creates a registration with a very short name in fields")
    @DisplayName("Test negative registration - Very Short Strings")
    @Feature("Registration negative")
    @ParameterizedTest
    @ValueSource(strings = { "username", "password", "firstName", "lastName", "email" })
    public void testCreateUserWithVeryShortStrings (String key) {

        Map<String, String> userData = new HashMap<>();
        userData.put(key, DataGenerator.getRandomString(1));
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextContains(responseCreateAuth, "The value of '" + key + "' field is too short");
    }

    @Description("This test creates a registration with a very long name in field firstName")
    @DisplayName("Test negative registration - Very long String in firstName")
    @Feature("Registration negative")
    @Test
    public void testCreateUserWithVeryLongNameInFirstName () {

        Map<String, String> userData = new HashMap<>();
        userData.put("firstName", DataGenerator.getRandomString(251));
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextContains(responseCreateAuth, "The value of 'firstName' field is too long");
    }

}

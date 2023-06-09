package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jdk.nashorn.internal.ir.RuntimeNode;
import lib.Assertions;
import lib.BaseTestCase;
import lib.ApiCoreRequests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@Epic("Authorisation cases")
public class UserAuthTest extends BaseTestCase {

    String cookie;
    String header;
    int userIdOnAuth;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @BeforeEach
    public void loginUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth, "user_id");
    }

        @Test
        @Feature("Authorisation positive")
        @Description("This test successfully authorise user by email and password")
        @DisplayName("Test positive auth user")
        public void testAuthUser(){
            Response responseCheckAuth = apiCoreRequests
                    .makeGetRequest(
                            "https://playground.learnqa.ru/api/user/auth",
                            this.header,
                            this.cookie
                    );

            Assertions.assertJsonByName(responseCheckAuth, "user_id", this.userIdOnAuth);
        }
        @Description("This test check authorisation status without sending cookie or token")
        @DisplayName("Test negative auth user")
        @Feature("Authorisation negative")
        @ParameterizedTest
        @ValueSource(strings = {"cookie", "headers"})
        public void testNegativeAuth(String condition) throws IllegalAccessException {


            if (condition.equals("cookie")) {
                Response responseForCheck = apiCoreRequests.makeGetRequestWithCookie(
                        "https://playground.learnqa.ru/api/user/auth",
                        this.cookie
                );
                Assertions.assertJsonByName(responseForCheck, "user_id", 0);

            } else if (condition.equals("headers")){
                Response responseForCheck = apiCoreRequests.makeGetRequestWithToken(
                        "https://playground.learnqa.ru/api/user/auth",
                        this.header
                );
                Assertions.assertJsonByName(responseForCheck, "user_id", 0);
            }  else {
                throw new IllegalAccessException("Condition value is known: "+condition);
            }

        }

    }


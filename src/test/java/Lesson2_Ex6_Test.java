import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Lesson2_Ex6_Test {

    @Test
    public void testLesson2_Ex6_Test(){

        Response response2 = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        //response2.prettyPrint();
        String locationH = response2.getHeader("location");
        System.out.println(locationH);

    }
}

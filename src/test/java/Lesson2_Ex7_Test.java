import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class Lesson2_Ex7_Test {

    @Disabled
    @Test
    public void testLesson2_Ex7_Test(){

        int counter = 0;
        Response response2= RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String locationH = response2.getHeader("location");
        System.out.println(locationH);

        while(response2.getStatusCode()!=200) {
        response2 = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get(locationH)
                .andReturn();

            locationH = response2.getHeader("location");
            System.out.println(locationH);
            counter++;

        }

        System.out.println(counter);

    }
}

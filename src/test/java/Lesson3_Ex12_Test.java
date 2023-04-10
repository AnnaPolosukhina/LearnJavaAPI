import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Lesson3_Ex12_Test {


    @Test
    public void testLesson3_Ex12_Test() {

        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        System.out.println("\n Headers");
        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        assertTrue(responseHeaders.hasHeaderWithName("x-secret-homework-header"));
        assertEquals("Some secret value", responseHeaders.getValue("x-secret-homework-header"),
                "header value is not as expected");

    }


}
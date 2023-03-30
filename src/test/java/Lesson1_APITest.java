
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Lesson1_APITest {


    @Test
    public void testRestAssured(){

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
    public void testRestAssuredJson(){

        Map<String, String> params = new HashMap<>();
        params.put("name", "Anna");

        JsonPath response = RestAssured
                .given()  // означает что будут параметры заппроса ниже
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String key = "answer2";
        String answer = response.get(key);
        if (answer==null){
            System.out.println("key "+key+" missing");
        }
        else {
            System.out.println(answer);
        }

    }

}

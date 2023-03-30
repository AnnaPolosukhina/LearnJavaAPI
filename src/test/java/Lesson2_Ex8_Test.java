import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.*;

public class Lesson2_Ex8_Test {

    @Test
    public void testLesson2_Ex8_Test(){

        //1 создаем задачу
        JsonPath response2= RestAssured
                .given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String token = response2.getString("token");
        int seconds = response2.getInt("seconds");


        //2 делаем один запрос с token ДО того, как задача готова, убеждался в правильности поля status
        Map<String, String> params = new HashMap<>();
        params.put("token", token);

        Response response3 = RestAssured
                .given()  // означает что будут параметры заппроса ниже
                .queryParams(params)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();

        System.out.println("Status:");
        response3.prettyPrint();

        //2 проверки
        Assertions.assertEquals(response3.jsonPath().getString("status"), "Job is NOT ready");

        //3. ждем нужное количество секунд с помощью функции Thread.sleep()
        pause(seconds*1000);

        // 4. делаем один запрос c token ПОСЛЕ того, как задача готова, убеждался в правильности поля status и наличии поля result
        Response response4 = RestAssured
                .given()  // означает что будут параметры заппроса ниже
                .queryParams(params)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();

        System.out.println("Result:");
        response4.prettyPrint();

        //4 проверки
        Assertions.assertEquals(response4.jsonPath().getString("status"), "Job is ready");
        Assertions.assertTrue(response4.jsonPath().getString("result")!=null);


    }

    public void pause(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

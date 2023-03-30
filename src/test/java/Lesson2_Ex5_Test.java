import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class Lesson2_Ex5_Test {

    @Test
    public void testLesson2_Ex5_Test(){

        JsonPath response2 = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

      //  System.out.println(response2.prettyPrint());  //изучила структуру json
        System.out.println(response2.getString("messages.message[1]")); //вывела текст 2го сообщения

    }
}

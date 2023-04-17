import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class yandex {
    @Test
    public void checkAddress(){

            Map<String, String> params = new HashMap<>();
            params.put("location", "Саратовская, Пролетарская, 4");

            Response response = RestAssured
                    .given()
                    .body(params)
                    .when()
                    .post("https://b2b-authproxy.taxi.yandex.net/api/b2b/platform/location/detect")
                    .andReturn();

            response.prettyPrint();


        System.out.println(response.statusCode());

        }

    }


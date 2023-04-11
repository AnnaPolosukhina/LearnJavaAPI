import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class Lesson3_Ex13_Test {

    @ParameterizedTest
    @ArgumentsSource(JsonArgumentsProvider.class)
    public void testLesson3_Ex13_Test(JSONObject requiredValues) {

        List<String> fieldsForCheck = new ArrayList<>();
        fieldsForCheck.add("platform");
        fieldsForCheck.add("browser");
        fieldsForCheck.add("device");

        List<String> allCaughtErrors = new ArrayList<>();

        JsonPath response = RestAssured
                .given()
                .header("User-Agent", requiredValues.get("user_agent"))
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();

        for (String field : fieldsForCheck) {

            if (response.get(field) ==null) {
                allCaughtErrors.add(field+ " parameter is missing in the response\n");
                continue;
            }

            try {
                assertEquals(requiredValues.get(field), response.getString(field), field + " value is not as required\n");
            } catch (AssertionFailedError e) {
                allCaughtErrors.add(e.getMessage());
            }
        }

        //========================== кидаем ошибку, если были несоответствия и печатаем все найденные несоответствия из листа allCaughtErrors ==========================
        if (allCaughtErrors.size() > 0) {
            throw new AssertionFailedError("For User Agent: \"" + requiredValues.get("user_agent") + "\" the following errors occurred: \n" +
                    allCaughtErrors);
        }

    }
}
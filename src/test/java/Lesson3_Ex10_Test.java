import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lesson3_Ex10_Test {


    @ParameterizedTest
    @ValueSource(strings = {"jkfjdi5646fdgfg645fjigjf", "string3рр"})
    public void testLesson3_Ex10_Test(String stringForAssert){

        int length = stringForAssert.length();

        System.out.println(stringForAssert + " - длина = " + length);
        assertTrue(15 < length, "error, text "+ stringForAssert +" shorter than 15 characters");



    }
}

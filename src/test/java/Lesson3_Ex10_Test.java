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
    @ValueSource(strings = {"съешь ещё этих мягких французских булок, да выпей чаю", "съешь ещё этих мягких французских булок", "выпей чаю"})
    public void testLesson3_Ex10_Test(String stringForAssert){
        int lengthString = stringForAssert.length();

        assertTrue( lengthString>15, "error, text \""+ stringForAssert +"\" shorter than 15 characters");
    }
}


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
String name = "Anna";

    @Test
    public void testHello(){
        System.out.println("Hello from "+name);
}

}

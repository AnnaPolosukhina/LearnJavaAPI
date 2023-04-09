
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

public class Lesson1_HelloWorldTest {
String name = "Anna";

    @Test
    @Ignore
    public void testHello(){
        System.out.println("Hello from "+name);
}

}

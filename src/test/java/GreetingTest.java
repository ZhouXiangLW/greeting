import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GreetingTest {
    @Test
    void should_return_hello_world() {
        assertEquals("Hello world", new Greeting().greet());
    }
}

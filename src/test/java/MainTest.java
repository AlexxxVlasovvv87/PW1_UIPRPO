import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testProcessPhoneNumber() {
        String input = "+123-456-789-00";
        String expected = "+1 (234) 567-89-00";
        String actual = Main.processPhoneNumber(input, "+1");
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatPhoneNumber() {
        String input = "+12345678900";
        String expected = "+1 (234) 567-89-00";
        String actual = Main.formatPhoneNumber(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testInvalidPhoneNumber() {
        String invalidPhoneNumber = "123";
        assertThrows(RuntimeException.class, () -> {
            Main.processPhoneNumber(invalidPhoneNumber, "+1");
        });
    }
}
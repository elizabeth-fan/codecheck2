import org.junit.Test;
import org.junit.Assert;
import com.horstmann.codecheck.Main;

public class MainTest {
    @Test
    public void testEscapeSpecialCharacters() {
        Main mainObject = new Main();

        // Test escaping asterisks
        String argWithAsterisk = "argWithAsterisk*";
        String escapedArgWithAsterisk = mainObject.escapeSpecialCharacters(argWithAsterisk);
        Assert.assertEquals("argWithAsterisk\\*", escapedArgWithAsterisk);

        // Test escaping quotes
        String argWithQuotes = "\"string*with*asterisks\"";
        String escapedArgWithQuotes = mainObject.escapeSpecialCharacters(argWithQuotes);
        Assert.assertEquals("\\\"string\\*with\\*asterisks\\\"", escapedArgWithQuotes);

        // Add more test cases as needed for other special characters
    }
}

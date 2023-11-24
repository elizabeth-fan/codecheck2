import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.horstmann.codecheck.CLanguage;

public class CLanguageTest {

    @Test
    public void testFunctionNameExtraction() {
        CLanguage cLang = new CLanguage();

        assertEquals("int", cLang.functionName("int function()"));
        assertEquals("double", cLang.functionName("double myFunction()"));
        assertEquals("char*", cLang.functionName("char* getString()"));

        // Test with modifiers
        assertEquals("int", cLang.functionName("static int function()"));
        assertEquals("double", cLang.functionName("extern double myFunction()"));
        assertEquals("char*", cLang.functionName("const char* getString()"));
    }
}

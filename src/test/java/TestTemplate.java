/**
 * Created by YDNPC on 29/11/2015.
 */
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

public class TestTemplate {

    private Template template;

    @Before
    public void setUp() throws Exception{
        template = new Template("${one}, ${two}, ${three}");
        template.set("one", "1");
        template.set("two", "2");
        template.set("three", "3");
    }


    @Test
    public void multipleVariables() throws Exception {
        assertTemplateEvaluatesTo("1, 2, 3");
    }

    @Test
    public void unknownVariablesAreIgnored() throws Exception{
        template.set("donotexist", "whatever");
        assertTemplateEvaluatesTo("1, 2, 3");
    }

    @Test
    public void missingValueRaisesException(){
        try{
            new Template("${foo}").evaluate();
            fail("evaluate() should throw an exception if a variable was left without a value!");
        }catch (MissingValueException expected){
            assertEquals("No value for ${foo}",expected.getMessage());
        }
    }

    @Test
    public void variablesGetProcessedJustOnce() throws Exception{
        template.set("one", "${one}");
        template.set("two", "${three}");
        template.set("three", "${two}");
        assertTemplateEvaluatesTo("${one}, ${three}, ${two}");
    }

    private void assertTemplateEvaluatesTo(String expected){
        assertEquals(expected, template.evaluate());
    }
}

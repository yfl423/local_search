package test;

import org.testng.annotations.Test;
import tool.Constraint;
import tool.Parser;

import java.util.Map;
import java.util.Set;

public class TestParser {

    @Test
    public void testParseDotVar() {
        Parser p = new Parser();
        Map<String, Set<Integer>> variables = p.parseDotVar("test/ex1.var1");
        System.out.println(variables);
    }

    @Test
    public void testSubtract() {
        Parser p = new Parser();
        Set<Constraint> constraints = p.parseDotCon("test/ex1.con");
        System.out.println(constraints);
    }
}

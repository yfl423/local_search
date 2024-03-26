package test;

import core.LocalSearch;
import org.testng.annotations.Test;
import tool.Parser;

public class TestLocalSearch {
    @Test
    public void TestCase1_None() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("test/ex1.var1"),
                p.parseDotCon("test/ex1.con"),
                "none"
        );
        ls.run();
    }

    @Test
    public void TestCase1_FC() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("test/ex1.var1"),
                p.parseDotCon("test/ex1.con"),
                "fc"
        );
        ls.run();
    }

    @Test
    public void TestCase2_None() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("test/ex2.var1"),
                p.parseDotCon("test/ex2.con"),
                "none"
        );
        ls.run();
    }

    @Test
    public void TestCase2_FC() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("test/ex2.var1"),
                p.parseDotCon("test/ex2.con"),
                "fc"
        );
        ls.run();
    }

    @Test
    public void TestCase3_None() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("test/ex3.var1"),
                p.parseDotCon("test/ex3.con"),
                "none"
        );
        ls.run();
    }

    @Test
    public void TestCase3_FC() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("test/ex3.var1"),
                p.parseDotCon("test/ex3.con"),
                "fc"
        );
        ls.run();
    }
}

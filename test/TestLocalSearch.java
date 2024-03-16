package test;

import org.junit.Test;
import core.LocalSearch;
import tool.Parser;

public class TestLocalSearch {
    @Test
    public void TestCase1_None() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex1.var1"),
                p.parseDotCon("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex1.con"),
                "none"
        );
        ls.run();
    }

    @Test
    public void TestCase1_FC() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex1.var1"),
                p.parseDotCon("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex1.con"),
                "fc"
        );
        ls.run();
    }

    @Test
    public void TestCase2_None() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex2.var1"),
                p.parseDotCon("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex2.con"),
                "none"
        );
        ls.run();
    }

    @Test
    public void TestCase2_FC() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex2.var1"),
                p.parseDotCon("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex2.con"),
                "fc"
        );
        ls.run();
    }

    @Test
    public void TestCase3_None() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex3.var1"),
                p.parseDotCon("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex3.con"),
                "none"
        );
        ls.run();
    }

    @Test
    public void TestCase3_FC() {
        Parser p = new Parser();
        LocalSearch ls = new LocalSearch(
                p.parseDotVar("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex3.var1"),
                p.parseDotCon("/Users/fenglongyang/Documents/file/Fenglong Yang materials/2023 UTD/cs 6364/local_search/test/ex3.con"),
                "fc"
        );
        ls.run();
    }
}

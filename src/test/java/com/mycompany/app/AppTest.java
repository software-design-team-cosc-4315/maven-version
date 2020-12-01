package com.mycompany.app;

//import org.junit.Assert;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest
{
    private static final StringBuilder output = new StringBuilder();

    @Test
    @Order(1)
    public void firstTest() {
        System.out.println("1");
        output.append("a");
    }

    @Test
    @Order(2)
    public void secondTest() {

        System.out.println(2);
        output.append("b");
    }

    @Test
    @Order(3)
    public void thirdTest() {
        System.out.println(3);
        output.append("c");
    }

    @Test
    @Order(4)
    public void canary() {
        assert(true);
    }

    @AfterAll
    public static void assertOutput() {
        System.out.println(output.toString());
        assertEquals(output.toString(), "abc");
    }
}


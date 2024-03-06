package org.LanceOfDestiny;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ExampleTest {

    private final int multiplyInt(int a, int b){
        return a*b;
    }

    private final int additionFalse(int a, int b){
        return a-b;
    }





    @Test
    void multiply_by_zero(){
        assertEquals(0,multiplyInt(0,5));
        System.out.println("Test1 Passed");
    }

    @Test
    void multiply_normal() {
        assertEquals(27, multiplyInt(9, 3));
        System.out.println("Test2 Passed");
    }
    @Test
    void addition(){
        assertEquals(4,additionFalse(3,1));
        System.out.println("Test3 Passed");
    }
}

package org.LanceOfDestiny;

import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.events.IllegalEventInvocationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventSystemTest {


    private final ByteArrayOutputStream outContent
            = new ByteArrayOutputStream();

    private final PrintStream out = System.out;
    String param = "Hello It is an invocation";

    @BeforeEach
    public void setupStreams(){

        Event.clearAllListeners();
        System.setOut(new PrintStream(outContent));
    }
    @AfterEach
    public void restoreStreams() {
        // Restore the original System.out
        System.setOut(out);
    }

    @Test
    void invoke_test_singular(){
        Consumer<Object> print1 = System.out::println;
        Event.LogEvent.addListener(print1);
        //System.out.println(param);
        Event.LogEvent.invoke(param);
        assertEquals(param + System.lineSeparator(),outContent.toString());
        System.out.println("Test1 Passed");
    }


    public void print10FirstLetter(String s){
        for(int i = 0; i<10;i++){
            System.out.print(s.charAt(0));
        }
        System.out.println();
    }

    public void printHello(){
        System.out.println("Hello");
    }

    @Test
    void invoke_test_multiple(){

        Event.LogEvent.addListener(System.out::println);
        Event.LogEvent.addListener(s->print10FirstLetter((String) s));
        Event.LogEvent.invoke(param);
        String wantedOutput = param + System.lineSeparator() + "HHHHHHHHHH" + System.lineSeparator();
        assertEquals(wantedOutput,outContent.toString());
        System.out.println("Test2 Passed");
    }

    @Test
    void invoke_test_event(){
        Consumer<Object> print1 = System.out::println;
        Consumer<Object> print2 = System.out::println;
        Event.LogEvent.addListener(print1);
        Event.LogIntegerEvent.addListener(print2);
        Event.LogEvent.invoke("LogEvent");
        assertEquals("LogEvent" + System.lineSeparator(),outContent.toString());
    }

    @Test
    void invoke_test_no_param(){
        Runnable printNull = this::printHello;
        Event.LogEvent.addRunnableListener(printNull);
        Event.LogEvent.invoke("");
        assertEquals("Hello" + System.lineSeparator(),outContent.toString());
    }

    @Test
    void wrong_type_invocation_test(){
        Event.LogEvent.addListener(System.out::println);
        assertThrows(IllegalEventInvocationException.class, ()-> Event.LogEvent.invoke(5));
    }
}

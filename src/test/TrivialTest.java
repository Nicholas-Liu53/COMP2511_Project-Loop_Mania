package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.io.FileNotFoundException;
// import java.util.List;
// import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;

/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to
 * achieve high coverage. A clickable "Run Test" link should appear if you have
 * installed the Java Extension Pack properly.
 */
public class TrivialTest {
    @Test
    public void blahTest() {
        assertEquals("a", "a");
    }

    @Test
    public void blahTest2(){
        LoopManiaWorld d = TestHelper.generateWorld();
        assertEquals(d.getWidth(), 1);
    }
}

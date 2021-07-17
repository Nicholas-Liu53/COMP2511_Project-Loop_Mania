package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;


/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to achieve high coverage.
 * A clickable "Run Test" link should appear if you have installed the Java Extension Pack properly.
 */
public class TrivialTest {
    @Test
    public void blahTest(){
        assertEquals("a", "a");
    }
    
    @Test
    public void blahTest2(){
        List<Pair<Integer, Integer>> orderedPath = null;

        try {
            orderedPath = TestHelper.generatePathTiles("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Using Gradle rather than VSCode, requires different path
            try {
                orderedPath = TestHelper.generatePathTiles("src/test/Resources/world_with_twists_and_turns.json");
            } catch (FileNotFoundException ee) {
                assertEquals(true, false);
            }
        }

        LoopManiaWorld d = new LoopManiaWorld(1, 2, orderedPath);
        assertEquals(d.getWidth(), 1);
    }
}

package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;

/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to
 * achieve high coverage. A clickable "Run Test" link should appear if you have
 * installed the Java Extension Pack properly.
 */
public class LoopManiaWorldTest {
    @Test
    public void constructTest() {
        // Testing construction + basic getters

        // Create new world
        // LoopMania world = new LoopManiaWorld(3, 3, x);

        // Test Case A:
        // Start a battle
        // Check if character and enemy damage reduce as expected during the battle

        // Test Case B:
        // Ensure that probability of receiving rewards is random and does not happen
        // everytime - how tho?
        // Check for rewards when discarding oldest card of any type (currently
        // applicable to vampireCard)
        // Check for rewards when discarding oldest item of any type (currently
        // applicable to unequippedSword)
    }
}

package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.*;
import unsw.loopmania.buildings.*;
import unsw.loopmania.buildingcards.*;


public class BuildingsTests {

    // NOTE: this test files considers both buildings AND cards
    
    @Test
    public void herosCastleTest() {
        /* 
            Requirements to test:
             1. UI TEST 1: Character gotta start there --> assert that position of hero's castle image is at the beginning of the path ✅
             3. UI TEST 2: There is only one hero's castle and it's there
             2. UI TEST 3: Shop Menu is opened when hero arrives at castle (game is paused)
        */
        
        // Testing Requirement 1:
        // Testing Requirement 2:
        // Testing Requirement 3:
        
    } 

    @Test
    public void vampireCastleTest() {
        /* 
            Requirements to test:
             1. Can only be placed on non-path tiles adjacent to the path
             2. Spawns vampires every 5 cycles
             3. Vampires spawns nearby on path
             4. Number of vampires increase by 1 every 5 cycles
        */
        
        // Testing Requirement 1:
    }
    
    @Test
    public void zombiePitTest() {
        /* 
            Requirements to test:
             1. Can only be placed on non-path tiles adjacent to the path
             2. Spawns zombies every 2 cycles
             3. Zombies spawns nearby on path
             4. Number of vampires increase by 1 every 2 cycles
        */
        
        // Testing Requirement 1:
    } 

    @Test
    public void towerTest() {
        /* 
            Requirements to test:
             1. Can only be placed on non-path tiles adjacent to the path
             2. Deals 10 damage per second to enemies within 2 tile radius during battles
        */
        
        // Testing Requirement 1:
    } 

    @Test
    public void villageTest() {
        /* 
            Requirements to test:
             1. Can only be placed on path tiles
             2. Restores all of characters health points when character walks onto it
        */
        
        // Testing Requirement 1:
    }
    
    @Test
    public void barracksTest() {
        /* 
            Requirements to test:
             1. Can only be placed on path tiles
             2. Gives character an "allied solider" when character walks onto it
        */
        
        // Testing Requirement 1:
    } 

    @Test
    public void trapTest() {
        /* 
            Requirements to test:
             1. Only one trap can only be placed on path tiles
             2. Deals 30 damage to enemy when enemy steps upon it
             3. Is destroyed after dealing damage to enemy
        */
        
        // Testing Requirement 1:
    } 

    @Test
    public void campfireTest() {
        /* 
            Requirements to test:
             1. Can only be placed on non-path tiles
             2. Character does double damage when within a radius of 4 tiles from campfire
        */
        
        // Testing Requirement 1:
    } 
}

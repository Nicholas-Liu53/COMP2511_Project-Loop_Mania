package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.SlugEnemy;


/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to achieve high coverage.
 * A clickable "Run Test" link should appear if you have installed the Java Extension Pack properly.
 */
public class SlugEnemyTest {
    @Test
    public void constructTest(){
        // Testing construction + basic getters
        
        // TODO, figure out how to make SlugEnemy init work
        SlugEnemy newSlug = new SlugEnemy();
        
        // Ensure attributes are correct
        assertEquals(newSlug.getHealth(), 25);
        assertEquals(newSlug.getAttackRadius(), newSlug.getSupportRadius());
        // 2 Being the equivalent of 1 tile
        assertEquals(newSlug.getAttackRadius(), 2);
        assertEquals(newSlug.getSupportRadius(), 2);
    }

    @Test
    public void takeDamageTest() {
        SlugEnemy newSlug = new SlugEnemy();

        
    }
}
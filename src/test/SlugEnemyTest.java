package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.SlugEnemy;


/**
 * Unite tests for the slug enemy class
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

        // Testing basic damage
        newSlug.receiveAttack(10);
        assertEquals(newSlug.getHealth(), 15);
        newSlug.receiveAttack(5);
        assertEquals(newSlug.getHealth(), 10);
        newSlug.receiveAttack(3);
        assertEquals(newSlug.getHealth(), 12);
        newSlug.receiveAttack(0);
        assertEquals(newSlug.getHealth(), 12);
        newSlug.receiveAttack(12);
        assertEquals(newSlug.getHealth(), 0);
        newSlug.receiveAttack(5);
        assertEquals(newSlug.getHealth(), 0);
    }

    @Test
    public void giveDamageTest() {
        Character newChar = new Character();
        SlugEnemy newSlug = new SlugEnemy();

        newSlug.launchAttack(newChar);
        assertEquals(newChar.getHealth(), 97);
        newSlug.launchAttack(newChar);
        assertEquals(newChar.getHealth(), 94);
        newSlug.launchAttack(newChar);
        assertEquals(newChar.getHealth(), 91);
        newSlug.launchAttack(newChar);
        assertEquals(newChar.getHealth(), 88);
    }
}
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.loopmania.Character;
import unsw.loopmania.enemies.SlugEnemy;
import unsw.loopmania.path.PathPosition;

/**
 * Unit tests for the slug enemy class
 */
public class SlugEnemyTest {
    @Test
    public void constructTest(){
        // Testing construction + basic getters
        
        SlugEnemy newSlug = new SlugEnemy(null);
        
        // Ensure attributes are correct
        assertEquals(newSlug.getHealth(), 25);
        assertEquals(newSlug.getAttackRadius(), newSlug.getSupportRadius());
        // 2 Being the equivalent of 1 tile
        assertEquals(newSlug.getAttackRadius(), 2);
        assertEquals(newSlug.getSupportRadius(), 2);
    }

    @Test
    public void takeDamageTest() {
        SlugEnemy newSlug = new SlugEnemy(null);

        // Testing basic damage
        newSlug.receiveAttack(10);
        assertEquals(newSlug.getHealth(), 15);
        newSlug.receiveAttack(5);
        assertEquals(newSlug.getHealth(), 10);
        newSlug.receiveAttack(3);
        assertEquals(newSlug.getHealth(), 7);
        newSlug.receiveAttack(0);
        assertEquals(newSlug.getHealth(), 7);
        newSlug.receiveAttack(12);
        assertEquals(newSlug.getHealth(), 0);
        newSlug.receiveAttack(5);
        assertEquals(newSlug.getHealth(), 0);
    }

    @Test
    public void giveDamageTest() {
        // Tests that the slug gives out correct damage in attacks
        Character newChar = new Character(null);
        SlugEnemy newSlug = new SlugEnemy(null);

        newSlug.launchAttack(newChar);
        assertEquals(newChar.getHealth(), 97);
        newSlug.launchAttack(newChar);
        assertEquals(newChar.getHealth(), 94);
        newSlug.launchAttack(newChar);
        assertEquals(newChar.getHealth(), 91);
        newSlug.launchAttack(newChar);
        assertEquals(newChar.getHealth(), 88);
    }

    @Test
    public void movementTest() {
        // Tests that the slug moves around the map
        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("worlds/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Failed to generate PathPostion
            assertTrue(false);
        }

        SlugEnemy newSlug = new SlugEnemy(position);

        int initialX = newSlug.getX();
        int initialY = newSlug.getY();
        boolean moved = false;
        int i = 0;

        System.out.println(initialX);
        System.out.println(initialY);

        while (i < 100) {
            newSlug.move();

            if (initialX != newSlug.getX() || initialY != newSlug.getY()) {
                moved = true;
            }

            i++;
        }

        assertTrue(moved);
    }
}
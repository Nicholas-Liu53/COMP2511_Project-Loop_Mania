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
        assertEquals(25, newSlug.getHealth());
        assertEquals(newSlug.getAttackRadius(), newSlug.getSupportRadius());
        // 2 Being the equivalent of 1 tile
        assertEquals(2, newSlug.getAttackRadius());
        assertEquals(2, newSlug.getSupportRadius());
    }

    @Test
    public void takeDamageTest() {
        SlugEnemy newSlug = new SlugEnemy(null);

        // Testing basic damage
        newSlug.receiveAttack(10);
        assertEquals(15, newSlug.getHealth());
        newSlug.receiveAttack(5);
        assertEquals(10, newSlug.getHealth());
        newSlug.receiveAttack(3);
        assertEquals(7, newSlug.getHealth());
        newSlug.receiveAttack(0);
        assertEquals(7, newSlug.getHealth());
        newSlug.receiveAttack(12);
        assertEquals(0, newSlug.getHealth());
        newSlug.receiveAttack(5);
        assertEquals(0, newSlug.getHealth());
    }

    @Test
    public void giveDamageTest() {
        // Tests that the slug gives out correct damage in attacks
        Character newChar = new Character(null);
        SlugEnemy newSlug = new SlugEnemy(null);

        newSlug.launchAttack(newChar);
        assertEquals(97, newChar.getHealth());
        newSlug.launchAttack(newChar);
        assertEquals(94, newChar.getHealth());
        newSlug.launchAttack(newChar);
        assertEquals(91, newChar.getHealth());
        newSlug.launchAttack(newChar);
        assertEquals(88, newChar.getHealth());
    }

    @Test
    public void movementTest() {
        // Tests that the slug moves around the map
        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Using gradle, different path is needed
            try {
                position = TestHelper.generatePathPosition("src/test/Resources/world_with_twists_and_turns.json");
            } catch (FileNotFoundException ee) {
                // Failed to generate PathPostion
                assertTrue(false);
            }
        }

        SlugEnemy newSlug = new SlugEnemy(position);

        int initialX = newSlug.getX();
        int initialY = newSlug.getY();
        boolean moved = false;
        int i = 0;

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
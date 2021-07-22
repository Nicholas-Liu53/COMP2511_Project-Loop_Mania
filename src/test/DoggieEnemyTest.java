package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.loopmania.character.Character;
import unsw.loopmania.enemies.DoggieEnemy;
import unsw.loopmania.path.PathPosition;

public class DoggieEnemyTest {
/**
 * Unit tests for the doggie enemy class
 */
    @Test
    public void constructTest(){
        // Testing construction + basic getters
        
        DoggieEnemy newDoggie = new DoggieEnemy(null);
        
        // Ensure attributes are correct
        assertEquals(110, newDoggie.getHealth());
        assertEquals(newDoggie.getAttackRadius(), newDoggie.getSupportRadius());
        // 2 Being the equivalent of 1 tile
        assertEquals(2, newDoggie.getAttackRadius());
        assertEquals(2, newDoggie.getSupportRadius());
    }

    @Test
    public void takeDamageTest() {
        DoggieEnemy newDoggie = new DoggieEnemy(null);

        // Testing basic damage
        newDoggie.receiveAttack(10);
        assertEquals(110, newDoggie.getHealth());
        newDoggie.receiveAttack(5);
        assertEquals(100, newDoggie.getHealth());
        newDoggie.receiveAttack(3);
        assertEquals(97, newDoggie.getHealth());
        newDoggie.receiveAttack(0);
        assertEquals(97, newDoggie.getHealth());
        newDoggie.receiveAttack(12);
        assertEquals(85, newDoggie.getHealth());
        newDoggie.receiveAttack(5);
        assertEquals(80, newDoggie.getHealth());
    }

    @Test
    public void giveDamageTest() {
        // Tests that the doggie gives out correct damage in attacks
        Character newChar = new Character(null);
        DoggieEnemy newDoggie = new DoggieEnemy(null);

        newDoggie.launchAttack(newChar);
        assertEquals(75, newChar.getHealth());
        newDoggie.launchAttack(newChar);
        assertEquals(50, newChar.getHealth());
        newDoggie.launchAttack(newChar);
        assertEquals(25, newChar.getHealth());
        newDoggie.launchAttack(newChar);
        assertEquals(0, newChar.getHealth());
    }

    @Test
    public void movementTest() {
        // Tests that the doggie moves around the map
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

        DoggieEnemy newDoggie = new DoggieEnemy(position);

        int initialX = newDoggie.getX();
        int initialY = newDoggie.getY();
        boolean moved = false;
        int i = 0;

        while (i < 100) {
            newDoggie.move();

            if (initialX != newDoggie.getX() || initialY != newDoggie.getY()) {
                moved = true;
            }

            i++;
        }

        assertTrue(moved);
    }
}
}

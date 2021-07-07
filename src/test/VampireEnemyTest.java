package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.VampireEnemy;
import unsw.loopmania.path.PathPosition;
import unsw.loopmania.Character;


/**
 * Unit tests for the vampire enemy class
 */
public class VampireEnemyTest {
    @Test
    public void constructTest(){
        // Testing construction + basic getters
        
        VampireEnemy newVampire = new VampireEnemy(null);
        
        // Ensure attributes are correct
        assertEquals(newVampire.getHealth(), 75);
        assertEquals(newVampire.getAttackRadius(), newVampire.getSupportRadius());
        
        assertEquals(newVampire.getAttackRadius(), 3);
        assertEquals(newVampire.getSupportRadius(), 4);
    }

    @Test
    public void takeDamageTest() {
        VampireEnemy newVampire = new VampireEnemy(null);

        // Testing basic damage, accounting for defence
        newVampire.receiveAttack(10);
        assertEquals(newVampire.getHealth(), 67);
        newVampire.receiveAttack(5);
        assertEquals(newVampire.getHealth(), 63);
        newVampire.receiveAttack(5);
        assertEquals(newVampire.getHealth(), 59);
        newVampire.receiveAttack(0);
        assertEquals(newVampire.getHealth(), 59);
        newVampire.receiveAttack(100);
        assertEquals(newVampire.getHealth(), 0);
    }

    @Test
    public void giveDamageTest() {
        // Testing vampire attack
        VampireEnemy newVampire = new VampireEnemy(null);

        // Checks that both a critical and normal attack have occurred
        boolean critAttack = false;
        boolean normAttack = false;
        int baseHealth = 100;
        int iterator = 0;

        while (iterator < 100) {
            Character newChar = new Character(null);

            newVampire.launchAttack(newChar);

            if (newChar.getHealth() == (baseHealth - 15)) {
                // Normal attack is working
                normAttack = true;
            } else {
                // Testing critical attack is within correct range (btwn 5 and 10)
                assertTrue((newChar.getHealth() >= (baseHealth - 20)));
                assertTrue((newChar.getHealth() <= (baseHealth - 25)));

                // Testing critical attack lasts for correct time (btwn 1 and 3 attacks)
                int i = 0;
                int startHealth = newChar.getHealth();
                while (i < 3) {
                    if (newChar.getHealth() == (startHealth - 15)) {
                        // Crit bonus expired correctly
                        critAttack = true;
                    }

                    startHealth = newChar.getHealth();
                    i++;
                }
            }

            iterator++;
        }

        assertTrue(critAttack);
        assertTrue(normAttack);
    }

    @Test
    public void movementTest() {
        // Tests that the slug moves around the map
        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("/worlds/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Failed to generate PathPostion
            assertTrue(false);
        }

        VampireEnemy newVampire = new VampireEnemy(position);

        int initialX = newVampire.getX();
        int initialY = newVampire.getY();
        boolean moved = false;
        int i = 0;

        System.out.println(initialX);
        System.out.println(initialY);

        while (i < 100) {
            newVampire.move();

            if (initialX != newVampire.getX() || initialY != newVampire.getY()) {
                moved = true;
            }

            i++;
        }

        assertTrue(moved);
    }
}
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.VampireEnemy;


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
        // Testing that the vampire moves as expected
        VampireEnemy newVampire = new VampireEnemy(null);

        int initialX = newVampire.getX();
        int initialY = newVampire.getY();
        boolean posChange = false;
        int i = 0;

        // Checking that Vampire moves
        while (i < 100) {
            newVampire.move();
            if (newVampire.getX() != initialX || newVampire.getY() != initialY) {
                posChange = true;
                break;
            }
        }

        assertTrue(posChange);
    }
}
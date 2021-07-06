package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.VampireEnemy;


/**
 * Unit tests for the slug enemy class
 */
public class VampireEnemyTest {
    @Test
    public void constructTest(){
        // Testing construction + basic getters
        
        // TODO, figure out how to make VampireEnemy init work
        VampireEnemy newVampire = new VampireEnemy();
        
        // Ensure attributes are correct
        assertEquals(newVampire.getHealth(), 75);
        assertEquals(newVampire.getAttackRadius(), newVampire.getSupportRadius());
        
        assertEquals(newVampire.getAttackRadius(), 3);
        assertEquals(newVampire.getSupportRadius(), 4);
    }

    @Test
    public void takeDamageTest() {
        VampireEnemy newVampire = new VampireEnemy();

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
        VampireEnemy newVampire = new VampireEnemy();

        // Checks that both a critical and normal attack have occurred
        boolean critAttack = false;
        boolean normAttack = false;
        int baseHealth = 100;
        int iterator = 0;

        while (iterator < 100) {
            Character newChar = new Character();

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

        assertEquals(critAttack, true);
        assertEquals(normAttack, true);
    }
}
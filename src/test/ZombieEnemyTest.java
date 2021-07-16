package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.loopmania.Ally;
import unsw.loopmania.Character;
import unsw.loopmania.Soldier;
import unsw.loopmania.enemies.ZombieEnemy;
import unsw.loopmania.path.PathPosition;

/**
 * Unit tests for the zombie enemy class
 */
public class ZombieEnemyTest {
    @Test
    public void constructTest() {
        // Testing construction + basic getters

        ZombieEnemy newZombie = new ZombieEnemy(null);

        // Ensure attributes are correct
        assertEquals(50, newZombie.getHealth());
        assertEquals(newZombie.getAttackRadius(), newZombie.getSupportRadius());
        // 2 Being the equivalent of 1 tile
        assertEquals(3, newZombie.getAttackRadius());
        assertEquals(3, newZombie.getSupportRadius());
    }

    @Test
    public void takeDamageTest() {
        ZombieEnemy newZombie = new ZombieEnemy(null);

        // Testing basic damage
        newZombie.receiveAttack(10);
        assertEquals(41, newZombie.getHealth());
        newZombie.receiveAttack(10);
        assertEquals(32, newZombie.getHealth());
        newZombie.receiveAttack(20);
        assertEquals(14, newZombie.getHealth());
        newZombie.receiveAttack(0);
        assertEquals(14, newZombie.getHealth());
        newZombie.receiveAttack(32);
        assertEquals(0, newZombie.getHealth());
        newZombie.receiveAttack(5);
        assertEquals(0, newZombie.getHealth());
    }

    @Test
    public void giveDamageTest() {
        // Tests that the zombie gives out correct damage in attacks
        // Also ensures that zombie attacks slower than other enemies
        Character newChar = new Character(null);
        ZombieEnemy newZombie = new ZombieEnemy(null);

        newZombie.launchAttack(newChar);
        assertEquals(100, newChar.getHealth());
        newZombie.launchAttack(newChar);
        assertEquals(100, newChar.getHealth());
        newZombie.launchAttack(newChar);
        assertEquals(90, newChar.getHealth());
        newZombie.launchAttack(newChar);
        assertEquals(90, newChar.getHealth());
        newZombie.launchAttack(newChar);
        assertEquals(90, newChar.getHealth());
        newZombie.launchAttack(newChar);
        assertEquals(80, newChar.getHealth());
    }

    @Test
    public void movementTest() {
        // Tests that the slug moves around the map
        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Failed to generate PathPostion
            assertTrue(false);
        }

        ZombieEnemy newZombie = new ZombieEnemy(position);

        int initialX = newZombie.getX();
        int initialY = newZombie.getY();
        boolean moved = false;
        int i = 0;

        while (i < 100) {
            newZombie.move();

            if (initialX != newZombie.getX() || initialY != newZombie.getY()) {
                moved = true;
            }

            i++;
        }

        assertTrue(moved);
    }

    @Test
    public void specialAttackTest() {
        // Testing that a vampire successfully creates another vampire
        // using critical bite
        boolean biteOccurred = false;

        for (int i = 0; i < 100; i++) {
            Character newChar = new Character(null);
            newChar.addAlly(new Soldier());
            newChar.addAlly(new Soldier());
            ZombieEnemy newZombie = new ZombieEnemy(null);

            newZombie.launchAttack(newChar);
            newZombie.launchAttack(newChar);

            if (newZombie.launchAttack(newChar)) {
                biteOccurred = true;
                assertEquals(0, newChar.getNumAllies());
            }
        }

        assertEquals(true, biteOccurred);
    }
}

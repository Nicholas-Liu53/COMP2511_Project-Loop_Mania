package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

import unsw.loopmania.enemies.ElanMuskeEnemy;
import unsw.loopmania.character.Character;
import unsw.loopmania.path.PathPosition;

/**
 * Unit tests for the Elan Muske enemy class
 */
public class ElanMuskeEnemyTest {
    @Test
    public void constructTest() {
        // Testing construction + basic getters

        ElanMuskeEnemy newElan = new ElanMuskeEnemy();

        // Ensure attributes are correct
        assertEquals(150, newElan.getHealth());
        assertEquals(newElan.getAttackRadius(), newElan.getSupportRadius());
        // 2 Being the equivalent of 1 tile
        assertEquals(2, newElan.getAttackRadius());
        assertEquals(2, newElan.getSupportRadius());
    }

    @Test
    public void takeDamageTest() {
        
    }
}

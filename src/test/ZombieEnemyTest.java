package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.loopmania.Character;
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
}

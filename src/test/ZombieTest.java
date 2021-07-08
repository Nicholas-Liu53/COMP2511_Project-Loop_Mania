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
public class ZombieTest {
    @Test
    public void constructTest(){
        // Testing construction + basic getters
        
        ZombieEnemy newZombie = new ZombieEnemy(null);
        
        // Ensure attributes are correct
        assertEquals(newZombie.getHealth(), 50);
        assertEquals(newZombie.getAttackRadius(), newZombie.getSupportRadius());
        // 2 Being the equivalent of 1 tile
        assertEquals(newZombie.getAttackRadius(), 3);
        assertEquals(newZombie.getSupportRadius(), 3);
    }
}

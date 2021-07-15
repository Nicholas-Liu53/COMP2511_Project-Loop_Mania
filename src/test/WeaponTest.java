package test;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.items.Staff;
import unsw.loopmania.items.Stake;
import unsw.loopmania.items.Sword;
import unsw.loopmania.items.WeaponStrategy;
import unsw.loopmania.Character;
import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.enemies.SlugEnemy;
import unsw.loopmania.enemies.VampireEnemy;

/**
 * Tests that each weapon deals the correct amount of damage when equipped, also
 * tests to ensure that special attacks work correctly
 */
public class WeaponTest {
    @Test
    public void swordTest() {
        WeaponStrategy weapon = new Sword();
        Character mainChar = new Character(null);
        Enemy enemy = new SlugEnemy(null);

        mainChar.equipItem(weapon);
        // Testing against slug
        mainChar.launchAttack(enemy);
        assertEquals(10, enemy.getHealth());
        mainChar.launchAttack(enemy);
        assertEquals(0, enemy.getHealth());
    }

    @Test
    public void stakeTest() {
        WeaponStrategy weapon = new Stake();
        Character mainChar = new Character(null);
        Enemy enemy = new SlugEnemy(null);

        mainChar.equipItem(weapon);
        // Testing against slug
        mainChar.launchAttack(enemy);
        assertEquals(15, enemy.getHealth());
        mainChar.launchAttack(enemy);
        assertEquals(5, enemy.getHealth());
        mainChar.launchAttack(enemy);
        assertEquals(0, enemy.getHealth());

        // Testing against vampire
        enemy = new VampireEnemy(null);

        mainChar.launchAttack(enemy);
        assertEquals(59, enemy.getHealth());
        mainChar.launchAttack(enemy);
        assertEquals(43, enemy.getHealth());
        mainChar.launchAttack(enemy);
        assertEquals(27, enemy.getHealth());
        mainChar.launchAttack(enemy);
        assertEquals(11, enemy.getHealth());
        mainChar.launchAttack(enemy);
        assertEquals(0, enemy.getHealth());
    }

    @Test
    public void staffTest() {
        WeaponStrategy weapon = new Staff();
        Character mainChar = new Character(null);
        Enemy enemy = new SlugEnemy(null);

        mainChar.equipItem(weapon);
        // Testing against slug
        mainChar.launchAttack(enemy);
        assertEquals(17, enemy.getHealth());
        mainChar.launchAttack(enemy);
        assertEquals(9, enemy.getHealth());
        mainChar.launchAttack(enemy);
        assertEquals(1, enemy.getHealth());
        mainChar.launchAttack(enemy);
        assertEquals(0, enemy.getHealth());
    }
}

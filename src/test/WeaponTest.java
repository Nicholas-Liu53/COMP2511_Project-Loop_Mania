package test;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.items.Staff;
import unsw.loopmania.items.Stake;
import unsw.loopmania.items.Sword;
import unsw.loopmania.items.WeaponStrategy;
import unsw.loopmania.character.Character;
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
        mainChar.launchAttack(enemy, false);
        assertEquals(10, enemy.getHealth());
        mainChar.launchAttack(enemy, false);
        assertEquals(0, enemy.getHealth());
    }

    @Test
    public void stakeTest() {
        WeaponStrategy weapon = new Stake();
        Character mainChar = new Character(null);
        Enemy enemy = new SlugEnemy(null);

        mainChar.equipItem(weapon);
        // Testing against slug
        mainChar.launchAttack(enemy, false);
        assertEquals(15, enemy.getHealth());
        mainChar.launchAttack(enemy, false);
        assertEquals(5, enemy.getHealth());
        mainChar.launchAttack(enemy, false);
        assertEquals(0, enemy.getHealth());

        // Testing against vampire
        enemy = new VampireEnemy(null);

        mainChar.launchAttack(enemy, false);
        assertEquals(59, enemy.getHealth());
        mainChar.launchAttack(enemy, false);
        assertEquals(43, enemy.getHealth());
        mainChar.launchAttack(enemy, false);
        assertEquals(27, enemy.getHealth());
        mainChar.launchAttack(enemy, false);
        assertEquals(11, enemy.getHealth());
        mainChar.launchAttack(enemy, false);
        assertEquals(0, enemy.getHealth());
    }

    @Test
    public void staffTest() {
        WeaponStrategy weapon = new Staff();

        // Testing special attack
        boolean criticalCheck = false;
        for (int i = 0; i < 100; i++) {
            Character mainChar = new Character(null);
            Enemy enemy = new SlugEnemy(null);
            Enemy enemy2 = new SlugEnemy(null);
            mainChar.equipItem(weapon);

            mainChar.addBattle(enemy);
            mainChar.addBattle(enemy2);
            mainChar.launchAttack(enemy, false);

            assertEquals(17, enemy.getHealth());

            if (mainChar.getNumAllies() == 1) {
                criticalCheck = true;
                int beforeHealth = mainChar.getHealth();
                // Simulate enemy ally use in battles
                mainChar.receiveAttack(5);
                assertEquals(beforeHealth, mainChar.getHealth());
            }
        }

        assertEquals(true, criticalCheck);
    }
}

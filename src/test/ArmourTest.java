package test;

import org.junit.Test;

import unsw.loopmania.Character;
import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.enemies.SlugEnemy;
import unsw.loopmania.enemies.VampireEnemy;
import unsw.loopmania.items.BodyArmour;
import unsw.loopmania.items.BodyArmourStrategy;
import unsw.loopmania.items.Helmet;
import unsw.loopmania.items.HelmetStrategy;
import unsw.loopmania.items.Shield;
import unsw.loopmania.items.ShieldStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing the equippable armour items
 */
public class ArmourTest {
    
    /**
     * Testing body armour reduces damage to character
     */
    @Test
    public void BodyArmourTest() {
        BodyArmourStrategy bodyArmour = new BodyArmour();
        Character mainChar = new Character(null);

        mainChar.equipItem(bodyArmour);
        // Testing only body armour
        mainChar.receiveAttack(10);
        assertEquals(95, mainChar.getHealth());
        mainChar.receiveAttack(20);
        assertEquals(85, mainChar.getHealth());
        mainChar.receiveAttack(50);
        assertEquals(60, mainChar.getHealth());
    }

    @Test
    public void ShieldTest() {
        ShieldStrategy shield = new Shield();
        Character mainChar = new Character(null);

        mainChar.equipItem(shield);
        // Testing only shield
        mainChar.receiveAttack(15);
        assertEquals(88, mainChar.getHealth());
        mainChar.receiveAttack(30);
        assertEquals(64, mainChar.getHealth());
        // Testing against vampire attack
        for (int i = 0; i < 100; i++) {
            Character newChar = new Character(null);
            VampireEnemy enemy = new VampireEnemy(null);
            newChar.equipItem(shield);

            enemy.launchAttack(newChar);
        }
    }

    @Test
    public void helmetTest() {
        HelmetStrategy helmet = new Helmet();
        Character mainChar = new Character(null);

        mainChar.equipItem(helmet);
        // Testing only helmet
        mainChar.receiveAttack(10);
        assertEquals(94, mainChar.getHealth());
        mainChar.receiveAttack(20);
        assertEquals(79, mainChar.getHealth());

        // Testing attack reduction
        SlugEnemy enemy = new SlugEnemy(null);
        mainChar.launchAttack(enemy);
        assertEquals(22, enemy.getHealth());
        mainChar.launchAttack(enemy);
        assertEquals(19, enemy.getHealth());
    }
}

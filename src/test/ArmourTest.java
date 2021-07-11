package test;

import org.junit.Test;

import unsw.loopmania.Character;
import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.enemies.SlugEnemy;
import unsw.loopmania.items.BodyArmour;
import unsw.loopmania.items.BodyArmourStrategy;

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
}

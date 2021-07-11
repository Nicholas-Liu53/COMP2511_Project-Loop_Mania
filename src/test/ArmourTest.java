package test;

import org.junit.Test;

import unsw.loopmania.Character;
import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.enemies.SlugEnemy;
import unsw.loopmania.items.BodyArmour;
import unsw.loopmania.items.BodyArmourStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArmourTest {
    
    @Test
    public void BodyArmourTest() {
        BodyArmourStrategy bodyArmour = new BodyArmour();
        Character mainChar = new Character(null);

        mainChar.equipItem(bodyArmour);
        // Testing only body armour
        mainChar.receiveAttack(10);
        assertEquals(93, mainChar.getHealth());
        mainChar.receiveAttack(20);
        assertEquals(79, mainChar.getHealth());
        mainChar.receiveAttack(50);
        assertEquals(44, mainChar.getHealth());
    }
}

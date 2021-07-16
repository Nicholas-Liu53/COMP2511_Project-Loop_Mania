package test;

import org.junit.Test;

import unsw.loopmania.Character;
import unsw.loopmania.Ally;
import unsw.loopmania.enemies.SlugEnemy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllyTest {
    @Test
    public void AllyAttackTest() {
        // Testing that allies do approrpiate damage
        Character mainChar = new Character(null);
        Ally allyOne = new Ally();
        mainChar.addAlly(allyOne);

        // One Ally
        SlugEnemy enemy = new SlugEnemy(null);
        mainChar.launchAttack(enemy, false);
        assertEquals(20, enemy.getHealth());

        // Multiple allies
        Ally allyTwo = new Ally();
        mainChar.addAlly(allyTwo);
        mainChar.launchAttack(enemy, false);
        assertEquals(15, enemy.getHealth());
    }

    @Test
    public void AllyDefendTest() {
        // Testing that allies take appropriate damage
        Character mainChar = new Character(null);
        Ally allyOne = new Ally();
        mainChar.addAlly(allyOne);

        // One ally taking damage
        SlugEnemy enemy = new SlugEnemy(null);
        enemy.launchAttack(mainChar);
        assertEquals(100, mainChar.getHealth());
        assertEquals(7, allyOne.getHealth());
        enemy.launchAttack(mainChar);
        enemy.launchAttack(mainChar);
        enemy.launchAttack(mainChar);
        assertEquals(0, mainChar.getNumAllies());

        // Multiple allies taking damage
        allyOne = new Ally();
        Ally allyTwo = new Ally();
        mainChar.addAlly(allyOne);
        mainChar.addAlly(allyTwo);
        enemy.launchAttack(mainChar);
        assertEquals(7, allyOne.getHealth());
        assertEquals(10, allyTwo.getHealth());
        enemy.launchAttack(mainChar);
        enemy.launchAttack(mainChar);
        enemy.launchAttack(mainChar);
        enemy.launchAttack(mainChar);
        assertEquals(7, allyTwo.getHealth());
    }
}

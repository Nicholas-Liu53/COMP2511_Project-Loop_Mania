package test;

import org.junit.Test;

import unsw.loopmania.Character;
import unsw.loopmania.Soldier;
import unsw.loopmania.enemies.SlugEnemy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoldierTest {
    @Test
    public void SoldierAttackTest() {
        // Testing that allies do approrpiate damage
        Character mainChar = new Character(null);
        Soldier soldierOne = new Soldier();
        mainChar.addAlly(soldierOne);

        // One Soldier
        SlugEnemy enemy = new SlugEnemy(null);
        mainChar.launchAttack(enemy, false);
        assertEquals(20, enemy.getHealth());

        // Multiple allies
        Soldier soldierTwo = new Soldier();
        mainChar.addAlly(soldierTwo);
        mainChar.launchAttack(enemy, false);
        assertEquals(15, enemy.getHealth());
    }

    @Test
    public void SoldierDefendTest() {
        // Testing that allies take appropriate damage
        Character mainChar = new Character(null);
        Soldier soldierOne = new Soldier();
        mainChar.addAlly(soldierOne);

        // One soldier taking damage
        SlugEnemy enemy = new SlugEnemy(null);
        enemy.launchAttack(mainChar);
        assertEquals(100, mainChar.getHealth());
        assertEquals(7, soldierOne.getHealth());
        enemy.launchAttack(mainChar);
        enemy.launchAttack(mainChar);
        enemy.launchAttack(mainChar);
        assertEquals(0, mainChar.getNumAllies());

        // Multiple allies taking damage
        soldierOne = new Soldier();
        Soldier soldierTwo = new Soldier();
        mainChar.addAlly(soldierOne);
        mainChar.addAlly(soldierTwo);
        enemy.launchAttack(mainChar);
        assertEquals(7, soldierOne.getHealth());
        assertEquals(10, soldierTwo.getHealth());
        enemy.launchAttack(mainChar);
        enemy.launchAttack(mainChar);
        enemy.launchAttack(mainChar);
        enemy.launchAttack(mainChar);
        assertEquals(7, soldierTwo.getHealth());
    }
}

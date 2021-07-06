package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.VampireEnemy;
import unsw.loopmania.Character;

public class CharacterTest {
    @Test
    public void constructTest() {
        // Testing construction + basic getters

        Character character = new Character(null);

        // Ensure attributes are correct
        assertEquals(character.getHealth(), 100);
    }

    @Test
    public void takeDamageTest() {
        Character character = new Character(null);

        // Testing basic damage, and checking that health never goes beyond 0
        character.receiveAttack(0);
        assertEquals(character.getHealth(), 100);
        character.receiveAttack(10);
        assertEquals(character.getHealth(), 90);
        character.receiveAttack(5);
        assertEquals(character.getHealth(), 85);
        character.receiveAttack(3);
        assertEquals(character.getHealth(), 82);
        character.receiveAttack(82);
        assertEquals(character.getHealth(), 0);
        character.receiveAttack(3);
        assertEquals(character.getHealth(), 0);
    }

    @Test
    public void movementTest() {
        // Testing that the Character moves as expected
        Character character = new Character(null);

        int initialX = character.getX();
        int initialY = character.getY();
        boolean posChange = false;
        int i = 0;

        // Checking that Character moves
        while (i < 100) {
            character.moveUpPath();
            if (!character.getX().equals(initialX) || !character.getY().equals(initialY)) {
                posChange = true;
                break;
            }
        }

        assertTrue(posChange);

        // maybe add test for direction checkm of upPath and downPath??
    }
}
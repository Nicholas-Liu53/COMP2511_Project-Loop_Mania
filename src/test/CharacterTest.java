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
    public void clockwiseMovementTest() {
        // Testing that the Character moves as expected
        Character character = new Character(null);

        int clockwiseInitialX = character.getX();
        int clockwiseInitialY = character.getY();
        boolean posChange = false;
        int i = 0;

        // Checking that Character moves clockwise
        while (i < 10) {
            character.moveDownPath();
            if (!character.getX().equals(clockwiseInitialX) || !character.getY().equals(clockwiseInitialY)) {
                posChange = true;
                break;
            }
        }

        assertTrue(posChange);
    }

    @Test
    public void clockwiseMovementTest() {
        // Testing that the Character moves as expected
        Character character = new Character(null);

        int anticlockwiseInitialX = character.getX();
        int anticlockwiseInitialY = character.getY();
        boolean posChange = false;
        int i = 0;

        // Checking that Character moves anticlockwise
        while (i < 10) {
            character.moveUpPath();
            if (!character.getX().equals(anticlockwiseInitialX) || !character.getY().equals(anticlockwiseInitialY)) {
                posChange = true;
                break;
            }
        }

        assertTrue(posChange);
    }
}
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.VampireEnemy;
import unsw.loopmania.Character;
import unsw.loopmania.enemies.SlugEnemy;

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
    public void giveDamageTest() {
        // Tests that the character gives out correct damage in attacks, is this
        // applicable? By how many points do we decrease health of the enemy?
        Character character = new Character(null);
        SlugEnemy slug = new SlugEnemy(null);
        VampireEnemy vampire = new VampireEnemy(null);

        character.launchAttack(vampire);
        assertEquals(vampire.getHealth(), ?);
    }

    @Test
    public void clockwiseMovementTest() {
        // Testing that the Character moves as expected
        Character character = new Character(null);

        int clockwiseInitialX = character.getX();
        int clockwiseInitialY = character.getY();
        boolean posChange = false;

        // Checking that Character moves clockwise
        for (int i = 0; i < 10; i++) {
            character.moveDownPath();
            if (clockwiseInitialX != character.getX() || clockwiseInitialY != character.getY())
                posChange = true;
        }

        assertTrue(posChange);
    }

    @Test
    public void anticlockwiseMovementTest() {
        // Testing that the Character moves as expected
        Character character = new Character(null);

        int anticlockwiseInitialX = character.getX();
        int anticlockwiseInitialY = character.getY();
        boolean posChange = false;

        // Checking that Character moves clockwise
        for (int i = 0; i < 10; i++) {
            character.moveUpPath();
            if (anticlockwiseInitialX != character.getX() || anticlockwiseInitialY != character.getY())
                posChange = true;
        }

        assertTrue(posChange);
}
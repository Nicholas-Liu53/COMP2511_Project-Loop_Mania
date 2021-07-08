package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.loopmania.enemies.VampireEnemy;
import unsw.loopmania.Character;
import unsw.loopmania.enemies.SlugEnemy;
import unsw.loopmania.path.PathPosition;

public class CharacterTest {
    @Test
    public void constructTest() {
        // Testing construction + basic getters

        Character character = new Character(null);

        // Ensure attributes are correct
        assertEquals(character.getHealth(), 100);
        assertEquals(character.getDamage(), 5);
        assertEquals(character.getInBattle(), false);
        assertEquals(character.getExperience(), 0);
        assertEquals(character.getGold(), 0);
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
        assertEquals(vampire.getHealth(), 70);
        character.launchAttack(vampire);
        assertEquals(vampire.getHealth(), 65);
        character.launchAttack(slug);
        assertEquals(slug.getHealth(), 20);
        character.launchAttack(vampire);
        assertEquals(vampire.getHealth(), 60);
        character.launchAttack(slug);
        assertEquals(slug.getHealth(), 15);
    }

    @Test
    public void clockwiseMovementTest() {
        // Tests that the character moves around the map in anticlockwise direction
        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Failed to generate PathPostion
            assertTrue(false);
        }

        Character character = new Character(position);

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
        // Tests that the character moves around the map in anticlockwise direction
        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Failed to generate PathPostion
            assertTrue(false);
        }

        Character character = new Character(position);

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

    @Test
    public void healthPotionTest() {
        Character character = new Character(null);
        HealthPotion healthPotion = new HealthPotion();
        character.equipItem(healthPotion);
    }

    @Test
    public void giveExperiencePointsTest() {
        Character character = new Character(null);
        assertEquals(character.getExperience(), 0);
        character.giveExperiencePoints(10);
        assertEquals(character.getExperience(), 10);
        character.giveExperiencePoints(1);
        assertEquals(character.getExperience(), 11);
    }

    @Test
    public void giveGoldTest() {
        Character character = new Character(null);
        assertEquals(character.getGold(), 0);
        character.giveGold(1000);
        assertEquals(character.getGold(), 1000);
        character.giveGold(100);
        assertEquals(character.getGold(), 1100);
    }
}
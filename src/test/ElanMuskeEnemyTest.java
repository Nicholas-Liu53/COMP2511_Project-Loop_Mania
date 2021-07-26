package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import unsw.loopmania.enemies.ElanMuskeEnemy;
import unsw.loopmania.enemies.SlugEnemy;
import unsw.loopmania.enemies.VampireEnemy;
import unsw.loopmania.enemies.ZombieEnemy;
import unsw.loopmania.goals.XpBaseGoal;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.character.Character;
import unsw.loopmania.path.PathPosition;

/**
 * Unit tests for the Elan Muske enemy class
 */
public class ElanMuskeEnemyTest {
    @Test
    public void constructTest() {
        // Testing construction + basic getters

        ElanMuskeEnemy newElan = new ElanMuskeEnemy(null);

        // Ensure attributes are correct
        assertEquals(150, newElan.getHealth());
        assertEquals(newElan.getAttackRadius(), newElan.getSupportRadius());
        // 2 Being the equivalent of 1 tile
        assertEquals(2, newElan.getAttackRadius());
        assertEquals(2, newElan.getSupportRadius());
    }

    @Test
    public void takeDamageTest() {
        ElanMuskeEnemy newElan = new ElanMuskeEnemy(null);

        // Testing basic damage
        newElan.receiveAttack(30);
        assertEquals(129, newElan.getHealth());
        newElan.receiveAttack(30);
        assertEquals(108, newElan.getHealth());
        newElan.receiveAttack(20);
        assertEquals(94, newElan.getHealth());
        newElan.receiveAttack(20);
        assertEquals(80, newElan.getHealth());
        newElan.receiveAttack(100);
        assertEquals(10, newElan.getHealth());
        newElan.receiveAttack(100);
        assertEquals(0, newElan.getHealth());
    }

    @Test
    public void giveDamageTest() {
        // Tests that elan gives out correct damage in attacks
        Character newChar = new Character(null);
        ElanMuskeEnemy newElan = new ElanMuskeEnemy(null);

        newElan.launchAttack(newChar);
        assertEquals(60, newChar.getHealth());
        newElan.launchAttack(newChar);
        assertEquals(20, newChar.getHealth());
        newElan.launchAttack(newChar);
        assertEquals(0, newChar.getHealth());
    }

    @Test
    public void movementTest() {
        // Tests that elan moves around the map
        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Using gradle, different path is needed
            try {
                position = TestHelper.generatePathPosition("src/test/Resources/world_with_twists_and_turns.json");
            } catch (FileNotFoundException ee) {
                // Failed to generate PathPostion
                assertTrue(false);
            }
        }

        ElanMuskeEnemy newElan = new ElanMuskeEnemy(position);

        int initialX = newElan.getX();
        int initialY = newElan.getY();
        boolean moved = false;
        int i = 0;

        while (i < 100) {
            newElan.move();

            if (initialX != newElan.getX() || initialY != newElan.getY()) {
                moved = true;
            }

            i++;
        }

        assertTrue(moved);
    }

    @Test
    public void worldImpactTest() {
        // Testing that Elan heals other enemies and increases DogeCoin price
        List<Pair<Integer, Integer>> orderedPath = null;

        try {
            orderedPath = TestHelper.generatePathTiles("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Using Gradle rather than VSCode, requires different path
            try {
                orderedPath = TestHelper.generatePathTiles("src/test/Resources/world_with_twists_and_turns.json");
            } catch (FileNotFoundException ee) {
                assertEquals(true, false);
            }
        }

        // Creating world with enemies
        LoopManiaWorld world = new LoopManiaWorld(8, 16, orderedPath);
        Character character = new Character(new PathPosition(30, orderedPath));
        ElanMuskeEnemy newElan = new ElanMuskeEnemy(new PathPosition(0, orderedPath));
        SlugEnemy newSlug = new SlugEnemy(new PathPosition(1, orderedPath));
        VampireEnemy newVampire = new VampireEnemy(new PathPosition(2, orderedPath));
        ZombieEnemy newZombie = new ZombieEnemy(new PathPosition(3, orderedPath));

        // Adding enemies to world
        world.setCharacter(character);
        world.setGoals(new XpBaseGoal(1000000));
        world.setEnemy(newElan);
        world.setEnemy(newSlug);
        world.setEnemy(newVampire);
        world.setEnemy(newZombie);

        // Doing damage to enemies
        newSlug.receiveAttack(2);
        newVampire.receiveAttack(2);
        newZombie.receiveAttack(2);
        world.runTickMoves();

        // Asserting that enemies have healed
        assertEquals(25, newSlug.getHealth());
        assertEquals(50, newZombie.getHealth());
        assertEquals(75, newVampire.getHealth());

        // Checking Doggie Coin price
        int price = world.getDoggieCoinPrice();

        if (price < 9000 || price > 11000) {
            assertTrue(false);
        }
    }
}

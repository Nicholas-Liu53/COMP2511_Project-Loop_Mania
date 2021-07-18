package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.FileNotFoundException;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.*;
import unsw.loopmania.path.PathPosition;
import unsw.loopmania.enemies.*;

public class BuildingsTests {

    // NOTE: this test files considers both buildings AND cards

    @Test
    public void herosCastleTest() {
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

        Character mainChar = new Character(new PathPosition(0, orderedPath));
        LoopManiaWorld world = new LoopManiaWorld(8, 16, orderedPath);
        world.setCharacter(mainChar);

        // Test shop appears at the end of cycle 1
        while (world.getCurrCycle() < 1) {
            world.runTickMoves();
            // System.out.println(world.getShowShop());
        }
        assertTrue(world.getShowShop());

        // Test shop appears at the end of cycle 3
        while (world.getCurrCycle() < 3) {
            world.runTickMoves();
            // System.out.println(world.getShowShop());
        }
        assertTrue(world.getShowShop());

        // Test shop appears at the end of cycle 6
        while (world.getCurrCycle() < 6) {
            world.runTickMoves();
            // System.out.println(world.getShowShop());
        }
        assertTrue(world.getShowShop());

        // Test shop appears at the end of cycle 10
        while (world.getCurrCycle() < 10) {
            world.runTickMoves();
            // System.out.println(world.getShowShop());
        }
        assertTrue(world.getShowShop());

        // Test shop appears at the end of cycle 15
        while (world.getCurrCycle() < 15) {
            world.runTickMoves();
            // System.out.println(world.getShowShop());
        }
        assertTrue(world.getShowShop());
    }

    @Test
    public void vampireCastleTest() {
        /*
         * Requirements to test: 1. Can only be placed on non-path tiles adjacent to the
         * path 2. Spawns vampires every 5 cycles 3. Vampires spawns nearby on path 4.
         * Number of vampires increase by 1 every 5 cycles
         */

        // Testing Requirement 1:
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
        Character mainChar = new Character(new PathPosition(0, orderedPath));
        VampireCastleBuilding vampireCastle = new VampireCastleBuilding(orderedPath.get(2));
        LoopManiaWorld world = new LoopManiaWorld(8, 16, orderedPath);
        world.setCharacter(mainChar);
        boolean vampireCastleFound = false;
        for (Building b : world.getBuildingsList())
            if (b.equals(vampireCastle))
                vampireCastleFound = true;
        assertFalse(vampireCastleFound);
    }

    @Test
    public void zombiePitTest() {
        /*
         * Requirements to test: 1. Can only be placed on non-path tiles adjacent to the
         * path 2. Spawns zombies every 2 cycles 3. Zombies spawns nearby on path 4.
         * Number of vampires increase by 1 every 2 cycles
         */

        // Testing Requirement 1:
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
        Character mainChar = new Character(new PathPosition(0, orderedPath));
        ZombiePitBuilding zombiePit = new ZombiePitBuilding(orderedPath.get(2));
        LoopManiaWorld world = new LoopManiaWorld(8, 16, orderedPath);
        world.setCharacter(mainChar);
        boolean zombiePitFound = false;
        for (Building b : world.getBuildingsList())
            if (b.equals(zombiePit))
                zombiePitFound = true;
        assertFalse(zombiePitFound);
    }

    @Test
    public void towerTest() {
        /*
         * Requirements to test: 1. Can only be placed on non-path tiles adjacent to the
         * path 2. Deals 10 damage per second to enemies within 2 tile radius during
         * battles
         */

        // Testing Requirement 1:
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
        Character mainChar = new Character(new PathPosition(0, orderedPath));
        TowerBuilding tower = new TowerBuilding(orderedPath.get(2));
        LoopManiaWorld world = new LoopManiaWorld(8, 16, orderedPath);
        world.setCharacter(mainChar);
        boolean towerFound = false;
        for (Building b : world.getBuildingsList())
            if (b.equals(tower))
                towerFound = true;
        assertFalse(towerFound);
    }

    @Test
    public void villageTest() {
        /*
         * Requirements to test: 1. Can only be placed on path tiles 2. Restores all of
         * characters health points when character walks onto it
         */

        // Testing Requirement 2:
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
        Character mainChar = new Character(new PathPosition(0, orderedPath));
        VillageBuilding village = new VillageBuilding(orderedPath.get(2));
        LoopManiaWorld world = new LoopManiaWorld(8, 16, orderedPath);
        world.setCharacter(mainChar);
        mainChar.receiveAttack(50);
        while (!(village.getX() == mainChar.getX()) && village.getY() == mainChar.getY()) {
            village.notifyTick(mainChar, world);
            assertEquals(mainChar.getHealth(), 50);
            world.runTickMoves();
        }
        village.notifyTick(mainChar, world);
        world.runTickMoves();
        assertEquals(mainChar.getHealth(), 100);

    }

    @Test
    public void barracksTest() {
        /*
         * Requirements to test: 1. Can only be placed on path tiles 2. Gives character
         * an "allied solider" when character walks onto it
         */

        // Testing Requirement 1:

        // Testing Requirement 2:
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

        BarracksBuilding barracks = new BarracksBuilding(orderedPath.get(1));
        Character mainChar = new Character(new PathPosition(0, orderedPath));
        LoopManiaWorld world = new LoopManiaWorld(8, 16, orderedPath);

        barracks.notifyTick(mainChar, world);
        assertEquals(0, mainChar.getAllies().size());

        mainChar.moveDownPath();

        barracks.notifyTick(mainChar, world);
        assertEquals(1, mainChar.getAllies().size());
    }

    @Test
    public void trapTest() {
        /*
         * Requirements to test: 1. Only one trap can only be placed on path tiles 2.
         * Deals 30 damage to enemy when enemy steps upon it 3. Is destroyed after
         * dealing damage to enemy
         */

        // Testing Requirement 1:
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

        TrapBuilding trap = new TrapBuilding(orderedPath.get(1));
        SlugEnemy slug = new SlugEnemy(new PathPosition(1, orderedPath));
        Character mainChar = new Character(new PathPosition(0, orderedPath));
        LoopManiaWorld world = new LoopManiaWorld(8, 16, orderedPath);
        world.setEnemy(slug);

        trap.notifyTick(mainChar, world);
        assertEquals(0, world.getEnemiesList().size());

        TrapBuilding trap2 = new TrapBuilding(orderedPath.get(1));
        ZombieEnemy zombie = new ZombieEnemy(new PathPosition(1, orderedPath));
        world.setEnemy(zombie);
        trap2.notifyTick(mainChar, world);
        assertEquals(23, zombie.getHealth());

        TrapBuilding trap3 = new TrapBuilding(orderedPath.get(1));
        VampireEnemy vampire = new VampireEnemy(new PathPosition(1, orderedPath));
        world.setEnemy(vampire);
        trap3.notifyTick(mainChar, world);
        assertEquals(75, vampire.getHealth());
    }

    @Test
    public void campfireTest() {
        /*
         * Requirements to test: 1. Can only be placed on non-path tiles 2. Character
         * does double damage when within a radius of 4 tiles from campfire 3. Vampire
         * runs away from campfire
         */

        // Testing Requirement 1:
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
        Character mainChar = new Character(new PathPosition(0, orderedPath));
        CampfireBuilding campfire = new CampfireBuilding(orderedPath.get(2));
        LoopManiaWorld world = new LoopManiaWorld(8, 16, orderedPath);
        world.setCharacter(mainChar);
        boolean campfireFound = false;
        campfire.notifyTick(mainChar, world);
        for (Building b : world.getBuildingsList())
            if (b.equals(campfire))
                campfireFound = true;
        assertFalse(campfireFound);

        // Testing Requirement 2:
        campfire = new CampfireBuilding(new Pair<Integer, Integer>(3, 1));
        campfire.notifyTick(mainChar, world);

        VampireEnemy vampire = new VampireEnemy(new PathPosition(0, orderedPath));
        world.setEnemy(vampire);

        world.runTickMoves();
        world.runBattles();
        // System.out.println("[" + mainChar.getHealth() + ", " + vampire.getHealth() +
        // "]");
        assertEquals(vampire.getHealth(), 71);
    }
}

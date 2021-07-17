package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.*;
import unsw.loopmania.Character;
import unsw.loopmania.items.*;
import unsw.loopmania.path.PathPosition;
import unsw.loopmania.enemies.*;
import unsw.loopmania.buildings.*;

public class ItemsTests {

    @Test
    public void healthPotionTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Obtainable through picking up off the ground 3. Purchasing price is 500 gold
         * 4. Selling price is 240 gold
         */

        // Testing Requirement 3 & 4:
        HealthPotion hp = new HealthPotion();
        assertEquals(hp.getClass().getSimpleName(), "HealthPotion");
        assertEquals(100, HealthPotion.getRestoreHealthPoints());
        assertEquals(125, HealthPotion.getPurchasePrice());
        assertEquals(100, HealthPotion.getSellPrice());
    }

    @Test
    public void bodyArmourTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 500 gold 3. Selling price is 400 gold
         */

        // Testing Requirement 2 & 3:
        BodyArmour ba = new BodyArmour();
        assertEquals(ba.getClass().getSimpleName(), "BodyArmour");
        assertEquals(0.5, ba.getDamageReductionFactor());
        assertEquals(500, BodyArmour.getPurchasePrice());
        assertEquals(400, BodyArmour.getSellPrice());
    }

    @Test
    public void helmetTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 200 gold 3. Selling price is 160 gold
         */

        // Testing Requirement 2 & 3:
        Helmet h = new Helmet();
        assertEquals("Helmet", h.getClass().getSimpleName());
        assertEquals(h.getDamageReductionFactor(), 0.8);
        assertEquals(200, Helmet.getPurchasePrice());
        assertEquals(160, Helmet.getSellPrice());
    }

    @Test
    public void shieldTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 300 gold 3. Selling price is 240 gold
         */

        // Testing Requirement 2 & 3:
        Shield s = new Shield();
        assertEquals("Shield", s.getClass().getSimpleName());
        assertEquals(0.7, s.getDamageReductionFactor());
        assertEquals(300, Shield.getPurchasePrice());
        assertEquals(240, Shield.getSellPrice());
    }

    @Test
    public void swordTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 200 gold 3. Selling price is 160 gold
         */

        // Testing Requirement 2 & 3:
        Sword s = new Sword();
        assertEquals("Sword", s.getClass().getSimpleName());
        assertEquals(200, Sword.getPurchasePrice());
        assertEquals(160, Sword.getSellPrice());
    }

    @Test
    public void staffTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 300 gold 3. Selling price is 240 gold
         */

        // Testing Requirement 2 & 3:
        Staff s = new Staff();
        assertEquals("Staff", s.getClass().getSimpleName());
        assertEquals(300, Staff.getPurchasePrice());
        assertEquals(240, Staff.getSellPrice());
    }

    @Test
    public void stakeTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 150 gold 3. Selling price is 120 gold
         */

        // Testing Requirement 2 & 3:
        Stake s = new Stake();
        assertEquals("Stake", s.getClass().getSimpleName());
        assertEquals(150, Stake.getPurchasePrice());
        assertEquals(120, Stake.getSellPrice());
    }

    @Test
    public void freeRun() {
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

        // SlugEnemy slug1 = new SlugEnemy(new PathPosition(2, orderedPath));
        ZombieEnemy zombie1 = new ZombieEnemy(new PathPosition(4, orderedPath));
        VampireEnemy vampire1 = new VampireEnemy(new PathPosition(6, orderedPath));

        Random rand = new Random();
        VampireCastleBuilding vcb = new VampireCastleBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        world.addBuilding(vcb);
        // ZombiePitBuilding zpb = new ZombiePitBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        // world.addBuilding(zpb);
        // TowerBuilding tb = new TowerBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        // world.addBuilding(tb);
        // VillageBuilding vb = new VillageBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        // world.addBuilding(vb);
        // BarracksBuilding bb = new BarracksBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        // world.addBuilding(bb);
        // TrapBuilding tp = new TrapBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        // world.addBuilding(tp);
        // CampfireBuilding cb = new CampfireBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        // world.addBuilding(cb);

        for (int i = 0; i < 5000; i++) {
            if (world.getEnemiesList().size() == 0) {
                // slug1 = new SlugEnemy(new PathPosition(rand.nextInt(orderedPath.size()), orderedPath));
                zombie1 = new ZombieEnemy(new PathPosition(rand.nextInt(orderedPath.size()), orderedPath));
                vampire1 = new VampireEnemy(new PathPosition(rand.nextInt(orderedPath.size()), orderedPath));
                vcb = new VampireCastleBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                // zpb = new ZombiePitBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                // tb = new TowerBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                // vb = new VillageBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                // bb = new BarracksBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                // tp = new TrapBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                // cb = new CampfireBuilding(new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                world.possiblySpawnEnemies();
                world.setEnemy(zombie1);
                world.setEnemy(vampire1);
                world.spawnGoldPile();
                world.spawnHealthPotion();
                world.loadItem("Sword");
                world.loadItem("Stake");
                world.loadItem("Staff");
                world.loadItem("BodyArmour");
                world.loadItem("Helmet");
                world.loadItem("Shield");
                world.loadItem("HealthPotion");
                world.addBuilding(vcb);
                // world.addBuilding(zpb);
                // world.addBuilding(tb);
                // world.addBuilding(vb);
                // world.addBuilding(bb);
                // world.addBuilding(tp);
                // world.addBuilding(cb);
            }
            world.runTickMoves();
            world.runBattles();
            world.attemptToPickUpItems();
            world.drinkHealthPotion();
        }
    }
}

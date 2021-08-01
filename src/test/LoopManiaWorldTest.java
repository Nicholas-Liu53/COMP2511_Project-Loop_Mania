package test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.*;
import unsw.loopmania.path.PathPosition;
import unsw.loopmania.enemies.*;
import unsw.loopmania.goals.XpBaseGoal;
import unsw.loopmania.items.*;
import unsw.loopmania.buildings.*;
import unsw.loopmania.character.Character;
import unsw.loopmania.buildingcards.*;

public class LoopManiaWorldTest {
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
        world.setGoals(new XpBaseGoal(1000000));
        world.addAavailableRareItems("the_one_ring");
        world.addAavailableRareItems("anduril_flame_of_the_west");
        world.addAavailableRareItems("tree_stump");
        assertEquals(List.of("the_one_ring", "anduril_flame_of_the_west", "tree_stump"), world.getAvailableRareItems());

        // SlugEnemy slug1 = new SlugEnemy(new PathPosition(2, orderedPath));
        ZombieEnemy zombie1 = new ZombieEnemy(new PathPosition(4, orderedPath));
        VampireEnemy vampire1 = new VampireEnemy(new PathPosition(6, orderedPath));

        Random rand = new Random();
        // VampireCastleBuilding vcb = new VampireCastleBuilding(new
        // Pair<Integer,Integer>(rand.nextInt(orderedPath.size()),rand.nextInt(orderedPath.size())));
        // world.addBuilding(vcb);
        // ZombiePitBuilding zpb = new ZombiePitBuilding(new
        // Pair<Integer,Integer>(rand.nextInt(orderedPath.size()),rand.nextInt(orderedPath.size())));
        // world.addBuilding(zpb);
        TowerBuilding tb = new TowerBuilding(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        world.addBuilding(tb);
        world.addObserver(tb);
        world.removeBuilding(tb);
        world.removeDeadObservers();
        VillageBuilding vb = new VillageBuilding(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        world.addBuilding(vb);
        BarracksBuilding bb = new BarracksBuilding(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        world.addBuilding(bb);
        TrapBuilding tp = new TrapBuilding(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        world.addBuilding(tp);
        CampfireBuilding cb = new CampfireBuilding(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        world.addBuilding(cb);

        BarracksCard card1 = new BarracksCard(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        CampfireCard card2 = new CampfireCard(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        TowerCard card3 = new TowerCard(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        TrapCard card4 = new TrapCard(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        VillageCard card5 = new VillageCard(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        ZombiePitCard card6 = new ZombiePitCard(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
        VampireCastleCard card7 = new VampireCastleCard(
                new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));

        for (int i = 0; i < 5000; i++) {
            if (world.getEnemiesList().size() == 0) {
                // slug1 = new SlugEnemy(new
                // PathPosition(rand.nextInt(orderedPath.size()),orderedPath));
                zombie1 = new ZombieEnemy(new PathPosition(rand.nextInt(orderedPath.size()), orderedPath));
                vampire1 = new VampireEnemy(new PathPosition(rand.nextInt(orderedPath.size()), orderedPath));
                // vcb = new VampireCastleBuilding(new
                // Pair<Integer,Integer>(rand.nextInt(orderedPath.size()),rand.nextInt(orderedPath.size())));
                // zpb = new ZombiePitBuilding(new
                // Pair<Integer,Integer>(rand.nextInt(orderedPath.size()),rand.nextInt(orderedPath.size())));
                tb = new TowerBuilding(
                        new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                vb = new VillageBuilding(
                        new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                bb = new BarracksBuilding(
                        new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                tp = new TrapBuilding(
                        new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                cb = new CampfireBuilding(
                        new Pair<Integer, Integer>(rand.nextInt(orderedPath.size()), rand.nextInt(orderedPath.size())));
                world.possiblySpawnEnemies();
                world.setEnemy(zombie1);
                world.setEnemy(vampire1);
                assertTrue(world.getEnemiesList().size() > 0);
                world.spawnGoldPile();
                world.spawnHealthPotion();
                world.loadItem("Sword");
                world.loadItem("Stake");
                world.loadItem("Staff");
                world.loadItem("BodyArmour");
                world.loadItem("Helmet");
                world.loadItem("Shield");
                world.loadItem("HealthPotion");
                assertTrue(world.getUnequippedInventoryItems().size() > 0);
                world.loadCard("BarracksCard");
                world.loadCard("CampfireCard");
                world.loadCard("TowerCard");
                world.loadCard("TrapCard");
                world.loadCard("VillageCard");
                world.loadCard("ZombiePitCard");
                world.loadCard("VampireCastleCard");
                assertTrue(world.getCards().size() > 0);
                world.convertCardToBuildingByCoordinates(card1.getX(), card1.getY(), bb.getX(), bb.getY());
                world.convertCardToBuildingByCoordinates(card2.getX(), card2.getY(), cb.getX(), cb.getY());
                world.convertCardToBuildingByCoordinates(card3.getX(), card3.getY(), tb.getX(), tb.getY());
                world.convertCardToBuildingByCoordinates(card4.getX(), card4.getY(), tp.getX(), tp.getY());
                world.convertCardToBuildingByCoordinates(card5.getX(), card5.getY(), vb.getX(), vb.getY());
                world.convertCardToBuildingByCoordinates(card6.getX(), card6.getY(), rand.nextInt(orderedPath.size()),
                        rand.nextInt(orderedPath.size()));
                world.convertCardToBuildingByCoordinates(card7.getX(), card7.getY(), rand.nextInt(orderedPath.size()),
                        rand.nextInt(orderedPath.size()));
                // world.addBuilding(vcb);
                // world.addBuilding(zpb);
                world.addBuilding(tb);
                world.addBuilding(vb);
                world.addBuilding(bb);
                world.addBuilding(tp);
                world.addBuilding(cb);
                assertTrue(world.getBuildingsList().size() > 0);
                world.getGold();
                world.getUnequippedItems();
                world.giveGold(5);
                assertTrue(world.getGold() > 0);
                world.deductGold(5);
                world.cardEntityIsFull();
            }
            world.runTickMoves();
            world.runBattles();
            world.attemptToPickUpItems();
            world.drinkHealthPotion();
        }
    }

    @Test
    public void confusingIntegrationTest() {
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
        world.setGoals(new XpBaseGoal(1000000));
        world.setGamemode("Confusing");
        int seedNum = world.setConfusingGamemodeSeed();
        boolean testedOver50 = false, testedUnder50 = false;
        while (!testedOver50 || !testedUnder50) {
            if (seedNum <= 50) {
                Item item1 = world.loadItem("OneRing");
                Item item2 = world.loadItem("Anduril");
                Item item3 = world.loadItem("TreeStump");

                OneRing oneRing = (OneRing) item1;
                assertTrue(oneRing.getConfusingItem() instanceof TreeStump);

                Anduril anduril = (Anduril) item2;
                assertTrue(anduril.getConfusingItem() instanceof TreeStump);

                TreeStump treeStump = (TreeStump) item3;
                assertTrue(treeStump.getConfusingItem() instanceof Anduril);
                testedUnder50 = true;
            } else {
                Item item1 = world.loadItem("OneRing");
                Item item2 = world.loadItem("Anduril");
                Item item3 = world.loadItem("TreeStump");

                OneRing oneRing = (OneRing) item1;
                assertTrue(oneRing.getConfusingItem() instanceof Anduril);

                Anduril anduril = (Anduril) item2;
                assertTrue(anduril.getConfusingItem() instanceof OneRing);

                TreeStump treeStump = (TreeStump) item3;
                assertTrue(treeStump.getConfusingItem() instanceof OneRing);
                testedOver50 = true;
            }
            seedNum = world.setConfusingGamemodeSeed();
        }
    }

    @Test
    public void confusingIntegrationTest2() {
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
        world.setGoals(new XpBaseGoal(1000000));
        world.setGamemode("Confusing");
        int seedNum = world.setConfusingGamemodeSeed();
        boolean testedOver50 = false, testedUnder50 = false;
        while (!testedOver50 || !testedUnder50) {
            if (seedNum <= 50 && !testedUnder50) {
                Item item1 = world.loadItem("OneRing");
                Item item2 = world.loadItem("Anduril");
                Item item3 = world.loadItem("TreeStump");

                OneRing oneRing = (OneRing) item1;
                assertTrue(oneRing.getConfusingItem() instanceof TreeStump);

                Anduril anduril = (Anduril) item2;
                assertTrue(anduril.getConfusingItem() instanceof TreeStump);

                TreeStump treeStump = (TreeStump) item3;
                assertTrue(treeStump.getConfusingItem() instanceof Anduril);
                testedUnder50 = true;

                mainChar.equipItem((ShieldStrategy) item1);
                mainChar.receiveAttack(10);
                assertEquals(mainChar.getHealth(), 94);

                mainChar.equipItem((ShieldStrategy) item2);
                mainChar.receiveAttack(10);
                assertEquals(mainChar.getHealth(), 88);

                VampireEnemy vampire1 = new VampireEnemy(new PathPosition(6, orderedPath));
                mainChar.equipItem((WeaponStrategy) item3);
                mainChar.launchAttack(vampire1, false);
                assertEquals(vampire1.getHealth(), 59);

            } else if (seedNum > 50 && !testedOver50) {
                Item item1 = world.loadItem("OneRing");
                Item item2 = world.loadItem("Anduril");
                Item item3 = world.loadItem("TreeStump");

                OneRing oneRing = (OneRing) item1;
                assertTrue(oneRing.getConfusingItem() instanceof Anduril);

                Anduril anduril = (Anduril) item2;
                assertTrue(anduril.getConfusingItem() instanceof OneRing);

                TreeStump treeStump = (TreeStump) item3;
                assertTrue(treeStump.getConfusingItem() instanceof OneRing);
                testedOver50 = true;

                VampireEnemy vampire1 = new VampireEnemy(new PathPosition(6, orderedPath));
                mainChar.equipItem((WeaponStrategy) item1);
                mainChar.launchAttack(vampire1, false);
            }
            seedNum = world.setConfusingGamemodeSeed();
        }
    }
}

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
        assertEquals(8, world.getWidth());
        assertEquals(16, world.getHeight());
        world.setCharacter(mainChar);
        world.addEntity(mainChar);
        world.setGoals(new XpBaseGoal(100000000));
        world.setGamemode("Standard");
        assertEquals(new Pair<Integer, Integer>(0, 0), world.getStartingPoint());
        world.addAavailableRareItems("the_one_ring");
        world.addAavailableRareItems("anduril_flame_of_the_west");
        world.addAavailableRareItems("tree_stump");
        assertEquals(List.of("the_one_ring", "anduril_flame_of_the_west", "tree_stump"), world.getAvailableRareItems());
        assertEquals(0, world.getNumOneRing());

        // all string properties that communicate with controller should be null when
        // initialising the game
        assertEquals(null, world.getAndurilProperty().get());
        assertEquals(null, world.getBodyArmourProperty().get());
        assertEquals(null, world.getShieldProperty().get());
        assertEquals(null, world.getSwordProperty().get());
        assertEquals(null, world.getOneRingProperty().get());
        assertEquals(null, world.getTreeStumpProperty().get());
        assertEquals(null, world.getStaffProperty().get());
        assertEquals(null, world.getStakeProperty().get());
        assertEquals(null, world.getDoggieCoinProperty().get());
        assertEquals(null, world.getHealthPotionProperty().get());
        assertEquals(null, world.getHelmetProperty().get());

        ZombieEnemy zombie1 = new ZombieEnemy(new PathPosition(4, orderedPath));
        VampireEnemy vampire1 = new VampireEnemy(new PathPosition(6, orderedPath));

        Random rand = new Random();
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

        for (int i = 0; i < 50000; i++) {
            if (world.getEnemiesList().size() == 0) {
                zombie1 = new ZombieEnemy(new PathPosition(rand.nextInt(orderedPath.size()), orderedPath));
                vampire1 = new VampireEnemy(new PathPosition(rand.nextInt(orderedPath.size()), orderedPath));
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
                Item sw = world.loadItem("Sword");
                world.equipArmourByCoordinates(sw.getX(), sw.getY());
                Item st = world.loadItem("Stake");
                world.equipWeaponByCoordinates(st.getX(), st.getY());
                world.loadItem("Staff");
                Item ba = world.loadItem("BodyArmour");
                world.equipArmourByCoordinates(ba.getX(), ba.getY());
                Item h = world.loadItem("Helmet");
                world.equipArmourByCoordinates(h.getX(), h.getY());
                Item sh = world.loadItem("Shield");
                world.equipArmourByCoordinates(sh.getX(), sh.getY());
                Item hp = world.loadItem("HealthPotion");
                world.addItem(hp);
                Item gp = world.loadItem("GoldPile");
                world.addItem(gp);
                world.loadItem("OneRing");
                world.loadItem("TreeStump");
                world.loadItem("Anduril");
                assertTrue(world.getUnequippedInventoryItems().size() > 0);
                Card card1 = world.loadCard("BarracksCard");
                Card card2 = world.loadCard("CampfireCard");
                Card card3 = world.loadCard("TowerCard");
                Card card4 = world.loadCard("TrapCard");
                Card card5 = world.loadCard("VillageCard");
                Card card6 = world.loadCard("ZombiePitCard");
                Card card7 = world.loadCard("VampireCastleCard");
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
                assertFalse(world.canPlaceCard(new Pair<Integer, Integer>(0, 0), card1));
                assertFalse(world.canPlaceCard(new Pair<Integer, Integer>(2, 3), card2));
                // assertNotEquals(null, world.convertCardToBuildingByCoordinates(card3.getX(),
                // card3.getY(), 1, 1));
                world.addBuilding(tb);
                world.addBuilding(vb);
                world.addBuilding(bb);
                world.addBuilding(tp);
                world.addBuilding(cb);
                assertTrue(world.getBuildingsList().size() > 0);
                assertTrue(world.getBuildingEntities().size() > 0);
                world.getGold();
                world.getUnequippedItems();
                world.giveGold(5);
                assertTrue(world.getGold() > 0);
                world.deductGold(5);
                world.cardEntityIsFull();
                world.giveDoggieCoin();
            }
            world.runTickMoves();
            world.runBattles();
            world.attemptToPickUpItems();
            world.drinkHealthPotion();
            world.giveRandomRewards("withCard");
            world.giveRandomRewards("noCard");
            world.giveRandomRewards("Card");
        }
        world.setShowShopToFalse();
        assertFalse(world.getShowShop());
        assertFalse(world.goalsAchieved());
    }

    @Test
    public void activateOneRingTest() {
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
        assertEquals(8, world.getWidth());
        assertEquals(16, world.getHeight());
        world.setCharacter(mainChar);
        world.addEntity(mainChar);
        world.setGoals(new XpBaseGoal(100000000));
        assertEquals(new Pair<Integer, Integer>(0, 0), world.getStartingPoint());
        world.addAavailableRareItems("the_one_ring");
        assertEquals(List.of("the_one_ring"), world.getAvailableRareItems());
        assertEquals(0, world.getNumOneRing());
        world.loadItem("OneRing");

        SlugEnemy slug1 = new SlugEnemy(new PathPosition(2, orderedPath));
        boolean done = false;
        for (int i = 0; i < 100; i++) {
            slug1.launchAttack(mainChar);
            if (world.getCharacterHealth() == 0) {
                world.activateOneRing();
                done = true;
            }
        }
        assertTrue(done);
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
        assertEquals(world.getGamemode(), "Confusing");
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

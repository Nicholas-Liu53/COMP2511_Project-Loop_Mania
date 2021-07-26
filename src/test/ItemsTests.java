package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.loopmania.items.*;

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
    public void oneRingTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 500 gold 3. Selling price is 400 gold
         */

        // Testing Requirement 2 & 3:
        OneRing ba = new OneRing();
        assertEquals(ba.getClass().getSimpleName(), "OneRing");
        assertEquals(100, HealthPotion.getRestoreHealthPoints());
    }

    @Test
    public void goldPileTest() {
        /**
        * Requirements to test: 1. Contains 100 gold
        */

        // Testing requirement 1
        GoldPile pile = new GoldPile();
        assertEquals(100, pile.getGoldAmount());
    }

    @Test
    public void doggieCoinTest() {
        /**
        * Requirements to test: 1. price randomly fluctuates between
        * 50 and 1000
        */

        // Testing requirements
        DoggieCoin coin = new DoggieCoin();
        assertEquals(coin.getClass().getSimpleName(), "DoggieCoin");
        assertEquals(coin.getPurchasePrice(), 0);

        for (int i = 0; i < 100; i++) {
            // Repeated tests for sell price
            boolean inRange = false;

            if (DoggieCoin.getSellPrice() >= 50 && DoggieCoin.getSellPrice() <= 1000)
                inRange = true;

            assertEquals(inRange, true);
        }
    }
}

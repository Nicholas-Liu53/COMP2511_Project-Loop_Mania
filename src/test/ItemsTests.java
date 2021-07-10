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
        assertEquals("HealthPotion", hp.getItemID());
        assertEquals(100, hp.getRestoreHealthPoints());
        assertEquals(125, hp.getPurchasePrice());
        assertEquals(100, hp.getSellPrice());
    }

    @Test
    public void bodyArmourTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 500 gold 3. Selling price is 400 gold
         */

        // Testing Requirement 2 & 3:
        BodyArmour ba = new BodyArmour();
        assertEquals("BodyArmour", ba.getItemID());
        assertEquals(0.5, ba.getDamageReductionFactor());
        assertEquals(500, ba.getPurchasePrice());
        assertEquals(400, ba.getSellPrice());
    }

    @Test
    public void helmetTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 200 gold 3. Selling price is 160 gold
         */

        // Testing Requirement 2 & 3:
        Helmet h = new Helmet();
        assertEquals("Helmet", h.getItemID());
        assertEquals(0.8, h.getDamageReductionFactor());
        assertEquals(200, h.getPurchasePrice());
        assertEquals(160, h.getSellPrice());
    }

    @Test
    public void shieldTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 300 gold 3. Selling price is 240 gold
         */

        // Testing Requirement 2 & 3:
        Shield s = new Shield();
        assertEquals("Shield", s.getItemID());
        assertEquals(0.7, s.getDamageReductionFactor());
        assertEquals(300, s.getPurchasePrice());
        assertEquals(240, s.getSellPrice());
    }

    @Test
    public void swordTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 200 gold 3. Selling price is 160 gold
         */

        // Testing Requirement 2 & 3:
        Sword s = new Sword();
        assertEquals("Sword", s.getItemID());
        assertEquals(10, s.getDamageIncrease());
        assertEquals(200, s.getPurchasePrice());
        assertEquals(160, s.getSellPrice());
    }

    @Test
    public void staffTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 300 gold 3. Selling price is 240 gold
         */

        // Testing Requirement 2 & 3:
        Staff s = new Staff();
        assertEquals("Staff", s.getItemID());
        assertEquals(3, s.getDamageIncrease());
        assertEquals(300, s.getPurchasePrice());
        assertEquals(240, s.getSellPrice());
    }

    @Test
    public void stakeTest() {
        /*
         * Requirements to test: 1. Obtainable through purchase in the shop 2.
         * Purchasing price is 150 gold 3. Selling price is 120 gold
         */

        // Testing Requirement 2 & 3:
        Stake s = new Stake();
        assertEquals("Stake", s.getItemID());
        assertEquals(5, s.getDamageIncrease());
        assertEquals(15, s.getVampireCrit());
        assertEquals(150, s.getPurchasePrice());
        assertEquals(120, s.getSellPrice());
    }
}

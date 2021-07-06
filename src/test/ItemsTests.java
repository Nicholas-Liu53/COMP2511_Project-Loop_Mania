package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

// import jdk.internal.jshell.tool.resources.l10n;
import unsw.loopmania.*;
import unsw.loopmania.items.*;


public class ItemsTests {
    
    @Test
    public void healthPotionTest() {
        /* 
            Requirements to test:
             1. Obtainable through purchase in the shop
             2. Obtainable through picking up off the ground
             3. Purchasing price is 500 gold
             4. Selling price is 240 gold
        */
        
        // Testing Requirement 3 & 4:
        HealthPotion hp = new HealthPotion();
        assertEquals(hp.getItemID(), "HealthPotion");
        assertEquals(hp.getRestoreHealthPoints(), 100);
        assertEquals(hp.getPurchasePrice(), 125);
        assertEquals(hp.getSellPrice(), 100);
    } 

    @Test 
    public void bodyArmourTest() {
        /* 
            Requirements to test:
             1. Obtainable through purchase in the shop
             2. Purchasing price is 500 gold
             3. Selling price is 400 gold
        */
        
        // Testing Requirement 2 & 3:
        BodyArmour ba = new BodyArmour();
        assertEquals(ba.getItemID(), "BodyArmour");
        assertEquals(ba.getDamageReductionFactor(), 0.5);
        assertEquals(ba.getPurchasePrice(), 500);
        assertEquals(ba.getSellPrice(), 400);
    }

    @Test 
    public void helmetTest() {
        /* 
            Requirements to test:
             1. Obtainable through purchase in the shop
             2. Purchasing price is 200 gold
             3. Selling price is 160 gold
        */
        
        // Testing Requirement 2 & 3:
        Helmet h = new Helmet();
        assertEquals(h.getItemID(), "Helmet");
        assertEquals(h.getDamageReductionFactor(), 0.8);
        assertEquals(h.getPurchasePrice(), 200);
        assertEquals(h.getSellPrice(), 160);
    }

    @Test 
    public void shieldTest() {
        /* 
            Requirements to test:
             1. Obtainable through purchase in the shop
             2. Purchasing price is 300 gold
             3. Selling price is 240 gold
        */
        
        // Testing Requirement 2 & 3:
        Shield s = new Shield();
        assertEquals(s.getItemID(), "Shield");
        assertEquals(s.getDamageReductionFactor(), 0.7);
        assertEquals(s.getPurchasePrice(), 300);
        assertEquals(s.getSellPrice(), 240);
    }

    @Test
    public void swordTest() {
        /* 
            Requirements to test:
             1. Obtainable through purchase in the shop
             2. Purchasing price is 200 gold
             3. Selling price is 160 gold
        */
        
        // Testing Requirement 2 & 3:
        Sword s = new Sword();
        assertEquals(s.getItemID(), "Sword");
        assertEquals(s.getDamageIncrease(), 10);
        assertEquals(s.getPurchasePrice(), 200);
        assertEquals(s.getSellPrice(), 160);
    }
    
    @Test
    public void staffTest() {
        /* 
            Requirements to test:
             1. Obtainable through purchase in the shop
             2. Purchasing price is 300 gold
             3. Selling price is 240 gold
        */
        
        // Testing Requirement 2 & 3:
        Staff s = new Staff();
        assertEquals(s.getItemID(), "Staff");
        assertEquals(s.getDamageIncrease(), 3);
        assertEquals(s.getPurchasePrice(), 300);
        assertEquals(s.getSellPrice(), 240);
    }

    @Test
    public void stakeTest() {
        /* 
            Requirements to test:
             1. Obtainable through purchase in the shop
             2. Purchasing price is 150 gold
             3. Selling price is 120 gold
        */
        
        // Testing Requirement 2 & 3:
        Stake s = new Stake();
        assertEquals(s.getItemID(), "Stake");
        assertEquals(s.getDamageIncrease(), 5);
        assertEquals(s.getVampireCrit(), 15);
        assertEquals(s.getPurchasePrice(), 300);
        assertEquals(s.getSellPrice(), 240);
    }
}

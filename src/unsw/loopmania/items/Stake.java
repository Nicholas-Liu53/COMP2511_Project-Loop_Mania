package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

/**
 * represents an equipped or unequipped stake in the backend world
 */
public class Stake extends Item {
    
    private int vampireCrit = 15;

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Weapon");
        purchasePrice = 150;
        sellPrice = 120;
    }
    public Stake() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2), "Weapon");
        purchasePrice = 150;
        sellPrice = 120;
    }
    
    public int getVampireCrit() { 
        return vampireCrit; 
    }
}

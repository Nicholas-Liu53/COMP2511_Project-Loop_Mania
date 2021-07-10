package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Item {
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Weapon");
        purchasePrice = 200;
        sellPrice = 160;
    }
    public Sword() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2), "Weapon");
        purchasePrice = 200;
        sellPrice = 160;
    }     
}

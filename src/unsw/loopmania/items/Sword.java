package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon {
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        itemID = "Sword";
        damageIncrease = 10;
        purchasePrice = 200;
        sellPrice = 160;
    }

    public Sword() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        itemID = "Sword";
        damageIncrease = 10;
        purchasePrice = 200;
        sellPrice = 160;
    }
}

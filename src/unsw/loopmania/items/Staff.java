package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped staff in the backend world
 */
public class Staff extends Weapon {
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        itemID = "Staff";
        damageIncrease = 3;
        purchasePrice = 300;
        sellPrice = 240;
    }

    public Staff() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        itemID = "Staff";
        damageIncrease = 3;
        purchasePrice = 300;
        sellPrice = 240;
    }
}
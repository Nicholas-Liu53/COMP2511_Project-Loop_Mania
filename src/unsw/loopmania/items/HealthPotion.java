package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

public class HealthPotion extends Item {
    private int restoreHealthPoints;
    
    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y, String itemType) {
        super(x, y, "Potion");
        itemID = "HealthPotion";
        purchasePrice = 125;
        sellPrice = 100;
        restoreHealthPoints = 100;
    }

    public int getRestoreHealthPoints() {
        return this.restoreHealthPoints;
    }
}

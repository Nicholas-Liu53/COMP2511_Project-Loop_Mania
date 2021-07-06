package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

public class HealthPotion extends Item {
    private int restoreHealthPoints;
    
    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Potion");
        itemID = "HealthPotion";
        purchasePrice = 125;
        sellPrice = 100;
        restoreHealthPoints = 100;
    }

    public HealthPotion() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2), "Potion");
        itemID = "HealthPotion";
        purchasePrice = 125;
        sellPrice = 100;
        restoreHealthPoints = 100;
    }

    public int getRestoreHealthPoints() {
        return this.restoreHealthPoints;
    }
}

package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

/**
 * a Item in the world
 * which doesn't move
 */
public abstract class Item extends StaticEntity {
    private String itemType; // Armour or weapon
    protected String itemID; // Specific armour/weapon
    protected int purchasePrice;
    protected int sellPrice;

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y, String itemType) {
        super(x, y);
        this.itemType = itemType;
    }

    public String getItemType() {
        return this.itemType;
    }
    
    public String getItemID() {
        return this.itemID;
    }
}
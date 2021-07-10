package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

/**
 * a Item in the world
 * which doesn't move
 */
public abstract class Item extends StaticEntity {
    private String itemType; // Armour or weapon or potion
    protected int purchasePrice;
    protected int sellPrice;

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y, String itemType) {
        super(x, y);
        this.itemType = itemType;
    }

    public String getItemType() {
        return this.itemType;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    @Override
    public String getStaticEntityType() {
        return "Item";
    }
}
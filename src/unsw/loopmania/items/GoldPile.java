package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.path.PathPosition;

public class GoldPile extends Item {
    private PathPosition position;
    private int goldAmount = 100;

    public GoldPile(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Gold");
        itemID = "GoldPile";
        purchasePrice = 125;
        sellPrice = 100;
    }

    public GoldPile(PathPosition position) {
        super(position.getX(), position.getY(), "Gold");
        itemID = "GoldPile";
        purchasePrice = 125;
        sellPrice = 100;
        this.position = position;
    }

    public GoldPile() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2), "Gold");
        itemID = "GoldPile";
        purchasePrice = 125;
        sellPrice = 100;
    }

    public int getGoldAmount() {
        return goldAmount;
    }
}
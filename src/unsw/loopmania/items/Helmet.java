package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends Armour {
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        purchasePrice = 200;
        sellPrice = 160;
        itemID = "Helmet";
        damageReductionFactor = 0.8;
    }

    public Helmet() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        purchasePrice = 200;
        sellPrice = 160;
        itemID = "Helmet";
        damageReductionFactor = 0.8;
    }

}

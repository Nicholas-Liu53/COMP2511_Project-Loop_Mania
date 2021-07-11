package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

public class Shield extends Armour {
    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        purchasePrice = 300;
        sellPrice = 240;
        damageReductionFactor = 0.7;
    }

    public Shield() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        purchasePrice = 300;
        sellPrice = 240;
        damageReductionFactor = 0.7;
    }

}
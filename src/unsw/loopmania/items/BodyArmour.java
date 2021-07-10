package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

public class BodyArmour extends Armour {
    public BodyArmour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        purchasePrice = 500;
        sellPrice = 400;
        damageReductionFactor = 0.5;
    }

    public BodyArmour() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        purchasePrice = 500;
        sellPrice = 400;
        damageReductionFactor = 0.5;
    }

}

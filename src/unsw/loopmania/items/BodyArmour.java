package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

public class BodyArmour extends Armour implements BodyArmourStrategy {
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

    public int receiveAttack(int damage) {
        // BodyArmour provides 5 defence
        int recvDamage = (int)(damage * 0.5);
        return recvDamage;
    }
}

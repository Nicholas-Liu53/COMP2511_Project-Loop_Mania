package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends Armour implements HelmetStrategy {
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        purchasePrice = 200;
        sellPrice = 160;
        damageReductionFactor = 0.8;
    }

    public Helmet() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        purchasePrice = 200;
        sellPrice = 160;
        damageReductionFactor = 0.8;
    }

    public int receiveAttack(int damage) {
        // Helmet provides 1 defence
        int recvDamage = (int)(damage * 0.1) + 3;
        return recvDamage;
    }

    public int launchAttack() {
        return 2;
    }
}

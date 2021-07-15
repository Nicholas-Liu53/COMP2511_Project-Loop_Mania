package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends Armour implements HelmetStrategy {
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        damageReductionFactor = 0.8;
    }

    public Helmet() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
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

    public static int getPurchasePrice() {
        return 200;
    }

    public static int getSellPrice() {
        return 160;
    }
}

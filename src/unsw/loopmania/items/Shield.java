package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

public class Shield extends Armour implements ShieldStrategy {
    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        damageReductionFactor = 0.7;
    }

    public Shield() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        damageReductionFactor = 0.7;
    }

    public int receiveAttack(int damage) {
        // BodyArmour provides 3 defence
        int recvDamage = (int)(damage * 0.2);
        return recvDamage;
    }

    public static int getPurchasePrice() {
        return 300;
    }

    public static int getSellPrice() {
        return 240;
    }
}
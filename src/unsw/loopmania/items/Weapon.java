package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Weapon extends Item {
    protected int damageIncrease;

    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Weapon");
    }

    public int getDamageIncrease() {
        return this.damageIncrease;
    }
}

package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

public abstract class Armour extends Item {   
    protected double damageReductionFactor;

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Armour");
    }

    public double getDamageReductionFactor() {
        return this.damageReductionFactor;
    }
}

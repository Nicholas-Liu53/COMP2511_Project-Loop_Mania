package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

public abstract class Weapon extends Item {
    protected int damagePoints;
    
    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Weapon");
    }

    public int getDamagePoints() {
        return this.damagePoints;
    }
}

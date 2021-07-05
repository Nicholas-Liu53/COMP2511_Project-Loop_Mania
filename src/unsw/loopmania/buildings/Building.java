package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

/**
 * a Building in the world
 * which doesn't move
 */
public abstract class Building extends StaticEntity {
    // TODO = implement other varieties of card than VampireCastleCard
    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}

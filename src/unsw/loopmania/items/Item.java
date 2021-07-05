package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

/**
 * a Item in the world
 * which doesn't move
 */
public abstract class Item extends StaticEntity {
    // TODO = implement other varieties of card than VampireCastleCard
    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}
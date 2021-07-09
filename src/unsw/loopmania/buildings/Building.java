package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.WorldStateObserver;

/**
 * a Building in the world
 * which doesn't move
 */
public abstract class Building extends StaticEntity implements WorldStateObserver {
    // TODO = implement other varieties of card than VampireCastleCard
    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Implementing WorldStateObserver, by default a building does nothing
     * when notified
     */
    public void notify(LoopManiaWorld worldState) {
        return;
    }
}

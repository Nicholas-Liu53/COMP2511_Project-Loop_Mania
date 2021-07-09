package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;
// import unsw.loopmania.StaticEntity;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.ZombieEnemy;

/**
 * a basic form of building in the world
 */
public class ZombiePitBuilding  extends Building {
    // TODO = add more types of building, and make sure buildings have effects on entities as required by the spec
    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public void notify(LoopManiaWorld worldState) {
        // Spawns zombie from bulding every cycle
        // TODO must figure out location to spawn
        worldState.addNewEnemy(new ZombieEnemy(null));
    }
}

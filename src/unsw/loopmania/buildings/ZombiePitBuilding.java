package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.ZombieEnemy;

/**
 * a basic form of building in the world
 */
public class ZombiePitBuilding extends Building {
    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, -1);
    }

    @Override
    public void notify(LoopManiaWorld worldState) {
        // Spawns zombie from bulding every cycle
        // TODO must figure out location to spawn
        worldState.addNewEnemy(new ZombieEnemy(this.getAdjacentPathTile(worldState.getPath())));
    }
}

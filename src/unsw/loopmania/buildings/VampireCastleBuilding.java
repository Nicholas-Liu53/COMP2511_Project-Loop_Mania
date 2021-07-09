package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;
// import unsw.loopmania.StaticEntity;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.VampireEnemy;

/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends Building {
    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public void notify(LoopManiaWorld worldState) {
        if (worldState.getCurrCycle() % 5 == 0) {
            // TODO must figure out location to spawn
            worldState.addNewEnemy(new VampireEnemy(null));
        }
    }
}

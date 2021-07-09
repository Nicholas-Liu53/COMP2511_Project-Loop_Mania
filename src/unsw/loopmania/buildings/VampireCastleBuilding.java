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
        // Spawns vampire from building ever 5
        if (worldState.getCurrCycle() % 5 == 0) {
            // Generates new enemy at adjacent path position
            worldState.addNewEnemy(new VampireEnemy(this.getAdjacentPathTile(worldState.getPath())));
        }
    }
}

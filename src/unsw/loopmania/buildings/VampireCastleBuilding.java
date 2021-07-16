package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.VampireEnemy;

/**
 * represents a vampire castle building in the backend game world
 */
public class VampireCastleBuilding extends Building {
    public VampireCastleBuilding(Pair<Integer, Integer> position) {
        super(position, -1);
    }

    @Override
    public void notifyCycle(LoopManiaWorld worldState) {
        // Spawns vampire from building every 5 cycles
        if (worldState.getCurrCycle() % 5 == 0) {
            // Generates new enemy at adjacent path position
            worldState.addNewEnemy(new VampireEnemy(this.getAdjacentPathTile(worldState.getPath())));
        }
    }
}

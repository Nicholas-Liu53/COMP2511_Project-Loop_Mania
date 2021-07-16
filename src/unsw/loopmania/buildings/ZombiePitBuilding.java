package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.ZombieEnemy;

/**
 * represents a zombie pit building in the backend game world
 */
public class ZombiePitBuilding extends Building {
    public ZombiePitBuilding(Pair<Integer, Integer> position) {
        super(position, -1);
    }

    @Override
    public void notifyCycle(LoopManiaWorld worldState) {
        // Spawns zombie from bulding every cycle at adjacent path position
        worldState.addNewEnemy(new ZombieEnemy(this.getAdjacentPathTile(worldState.getPath())));
    }
}

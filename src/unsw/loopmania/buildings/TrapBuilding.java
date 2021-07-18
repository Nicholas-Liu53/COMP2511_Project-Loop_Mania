package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.*;
import unsw.loopmania.character.Character;
import unsw.loopmania.enemies.*;

/**
 * represents a trap building in the backend game world
 */
public class TrapBuilding extends Building {
    public TrapBuilding(Pair<Integer, Integer> position) {
        super(position, -1);
    }

    @Override
    public void notifyTick(Character mainChar, LoopManiaWorld world) {
        for (Enemy e : world.getEnemiesList()) {
            if (this.getX() == (e.getX()) && this.getY() == (e.getY())) {
                e.receiveAttack(30);
                world.removeBuilding(this);
                if (e.getHealth() == 0) {
                    world.killEnemy(e);
                }
                break;
            }
        }
        return;
    }
}

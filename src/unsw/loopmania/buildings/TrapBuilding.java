package unsw.loopmania.buildings;

import org.javatuples.Pair;

/**
 * represents a trap building in the backend game world
 */
public class TrapBuilding extends Building {
    public TrapBuilding(Pair<Integer, Integer> position) {
        super(position, -1);
    }
}

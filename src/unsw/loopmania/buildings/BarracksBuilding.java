package unsw.loopmania.buildings;

import org.javatuples.Pair;

/**
 * represents a barracks building in the backend game world
 */
public class BarracksBuilding extends Building {
    public BarracksBuilding(Pair<Integer, Integer> position) {
        super(position, -1);
    }
}

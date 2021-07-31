package unsw.loopmania.buildings;

import org.javatuples.Pair;

/**
 * represents a campfire building in the backend game world
 */
public class CampfireBuilding extends Building {
    public CampfireBuilding(Pair<Integer, Integer> position) {
        super(position, 4);
    }
}

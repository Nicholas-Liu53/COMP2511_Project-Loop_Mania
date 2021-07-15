package unsw.loopmania.buildings;

import org.javatuples.Pair;

/**
 * represents a village building in the backend game world
 */
public class VillageBuilding extends Building {
    public VillageBuilding(Pair<Integer, Integer> position) {
        super(position, -1);
    }
}

package unsw.loopmania.buildings;

import org.javatuples.Pair;

/**
 * represents a hero's castle building in the backend game world
 */
public class HeroesCastle extends Building {
    public HeroesCastle(Pair<Integer, Integer> position) {
        super(position, -1);
    }
}

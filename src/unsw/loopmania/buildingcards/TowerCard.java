package unsw.loopmania.buildingcards;

import org.javatuples.Pair;

/**
 * represents a tower card in the backend game world
 */
public class TowerCard extends Card {
    public TowerCard(Pair<Integer, Integer> position) {
        super(position);
    }

    public TowerCard() {
        super(new Pair<Integer, Integer>(1, 0));
    }
}
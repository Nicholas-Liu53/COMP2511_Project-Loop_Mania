package unsw.loopmania.buildingcards;

import org.javatuples.Pair;

/**
 * represents a vampire castle card in the backend game world
 */
public class VampireCastleCard extends Card {
    public VampireCastleCard(Pair<Integer, Integer> position) {
        super(position);
    }

    public VampireCastleCard() {
        super(new Pair<Integer, Integer>(1, 0));
    }
}

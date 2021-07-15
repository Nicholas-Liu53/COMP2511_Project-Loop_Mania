package unsw.loopmania.buildingcards;

import org.javatuples.Pair;

/**
 * represents a campfire card in the backend game world
 */
public class CampfireCard extends Card {
    public CampfireCard(Pair<Integer, Integer> position) {
        super(position);
    }

    public CampfireCard() {
        super(new Pair<Integer, Integer>(1, 0));
    }
}
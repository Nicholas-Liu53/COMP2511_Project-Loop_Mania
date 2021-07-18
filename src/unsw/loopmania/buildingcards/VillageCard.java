package unsw.loopmania.buildingcards;

import org.javatuples.Pair;

/**
 * represents a village card in the backend game world
 */
public class VillageCard extends Card {
    public VillageCard(Pair<Integer, Integer> position) {
        super(position);
    }
}

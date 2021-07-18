package unsw.loopmania.buildingcards;

import org.javatuples.Pair;

/**
 * represents a trap card in the backend game world
 */
public class TrapCard extends Card {
    public TrapCard(Pair<Integer, Integer> position) {
        super(position);
    }
}
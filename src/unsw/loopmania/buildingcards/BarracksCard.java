package unsw.loopmania.buildingcards;

import org.javatuples.Pair;

/**
 * represents a barracks card in the backend game world
 */
public class BarracksCard extends Card {
    public BarracksCard(Pair<Integer, Integer> position) {
        super(position);
    }
}
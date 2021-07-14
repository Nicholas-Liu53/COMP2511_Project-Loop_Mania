package unsw.loopmania.buildingcards;

import org.javatuples.Pair;

/**
 * represents a zombie pit card in the backend game world
 */
public class ZombiePitCard extends Card {
    public ZombiePitCard(Pair<Integer, Integer> position) {
        super(position);
    }

    public ZombiePitCard() {
        super(new Pair<Integer, Integer>(1, 0));
    }
}
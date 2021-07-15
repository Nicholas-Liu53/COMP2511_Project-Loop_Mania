package unsw.loopmania.buildingcards;

import org.javatuples.Pair;

import unsw.loopmania.StaticEntity;

/**
 * a Card in the world which doesn't move
 */
public abstract class Card extends StaticEntity {
    public Card(Pair<Integer, Integer> position) {
        super(position);
    }

    @Override
    public String getStaticEntityType() {
        return "Card";
    }
}

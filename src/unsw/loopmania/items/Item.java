package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.path.PathPosition;
import unsw.loopmania.StaticEntity;

/**
 * a Item in the world which doesn't move
 */
public abstract class Item extends StaticEntity {
    public Item(Pair<Integer, Integer> position) {
        super(position);
    }

    public Item(PathPosition position) {
        super(position);
    }

    @Override
    public String getStaticEntityType() {
        return "Item";
    }
}
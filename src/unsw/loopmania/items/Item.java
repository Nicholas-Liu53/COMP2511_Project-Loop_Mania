package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.path.PathPosition;
import unsw.loopmania.StaticEntity;

/**
 * a Item in the world which doesn't move
 */
public abstract class Item extends StaticEntity {
    protected int purchasePrice;
    protected int sellPrice;

    public Item(Pair<Integer, Integer> position) {
        super(position);
    }

    public Item(PathPosition position) {
        super(position);
    }

    public int getPurchasePrice() {
        return this.purchasePrice;
    }

    public int getSellPrice() {
        return this.sellPrice;
    }

    @Override
    public String getStaticEntityType() {
        return "Item";
    }
}
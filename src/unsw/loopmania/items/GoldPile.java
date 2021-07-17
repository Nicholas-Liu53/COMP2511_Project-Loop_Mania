package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.path.PathPosition;

public class GoldPile extends Item {
    private final int goldAmount = 100;

    public GoldPile(Pair<Integer, Integer> position) {
        super(position);
    }

    public GoldPile(PathPosition position) {
        super(position);
    }

    public GoldPile() {
        super(new Pair<Integer, Integer>(1, 2));
    }

    public int getGoldAmount() {
        return this.goldAmount;
    }
}
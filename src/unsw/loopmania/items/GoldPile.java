package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.path.PathPosition;

public class GoldPile extends Item {
    private final int goldAmount = 100;

    public GoldPile(Pair<Integer, Integer> position) {
        super(position);
        purchasePrice = 125;
        sellPrice = 100;
    }

    public GoldPile(PathPosition position) {
        super(position);
        purchasePrice = 125;
        sellPrice = 100;
    }

    public GoldPile() {
        super(new Pair<Integer, Integer>(1, 2));
        purchasePrice = 125;
        sellPrice = 100;
    }

    public int getGoldAmount() {
        return goldAmount;
    }
}
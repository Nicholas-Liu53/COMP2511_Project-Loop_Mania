package unsw.loopmania.items;

import org.javatuples.Pair;
import unsw.loopmania.RareItem;

public class OneRing extends Item implements RareItem {
    private Item confusingItem;

    public OneRing(Pair<Integer, Integer> position) {
        super(position);
    }

    public OneRing() {
        super(new Pair<Integer, Integer>(1, 2));
    }

    public static int getRestoreHealthPoints() {
        return 100;
    }

    public void setConfusingItem(Item item) {
        this.confusingItem = item;
    }

    public Item getConfusingItem() {
        return confusingItem;
    }
}
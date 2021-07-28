package unsw.loopmania.items;

import org.javatuples.Pair;

public class OneRing extends Item {
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

    public void setConfusingProperty(Item item) {
        this.confusingItem = item;
    }

    public String getConfusingProperty() {
        return confusingItem.getClass().getSimpleName();
    }
}
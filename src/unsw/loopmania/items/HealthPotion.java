package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.path.PathPosition;

public class HealthPotion extends Item {
    private int restoreHealthPoints;

    public HealthPotion(Pair<Integer, Integer> position) {
        super(position);
    }

    public HealthPotion(PathPosition position) {
        super(position);
    }

    public HealthPotion() {
        super(new Pair<Integer, Integer>(1, 2));
    }

    public int getRestoreHealthPoints() {
        return this.restoreHealthPoints;
    }

    public static int getPurchasePrice() {
        return 125;
    }

    public static int getSellPrice() {
        return 100;
    }
}

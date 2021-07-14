package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.path.PathPosition;

public class HealthPotion extends Item {
    private int restoreHealthPoints;

    public HealthPotion(Pair<Integer, Integer> position) {
        super(position);
        purchasePrice = 125;
        sellPrice = 100;
        restoreHealthPoints = 100;
    }

    public HealthPotion(PathPosition position) {
        super(position);
        purchasePrice = 125;
        sellPrice = 100;
        restoreHealthPoints = 100;
    }

    public HealthPotion() {
        super(new Pair<Integer, Integer>(1, 2));
        purchasePrice = 125;
        sellPrice = 100;
        restoreHealthPoints = 100;
    }

    public int getRestoreHealthPoints() {
        return this.restoreHealthPoints;
    }
}

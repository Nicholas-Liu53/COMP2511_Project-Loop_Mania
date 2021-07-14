package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.path.PathPosition;

public class HealthPotion extends Item {
    private int restoreHealthPoints;

    public HealthPotion(Pair<Integer, Integer> position) {
        super(position);
        this.purchasePrice = 125;
        this.sellPrice = 100;
        this.restoreHealthPoints = 100;
    }

    public HealthPotion(PathPosition position) {
        super(position);
        this.purchasePrice = 125;
        this.sellPrice = 100;
        this.restoreHealthPoints = 100;
    }

    public HealthPotion() {
        super(new Pair<Integer, Integer>(1, 2));
        this.purchasePrice = 125;
        this.sellPrice = 100;
        this.restoreHealthPoints = 100;
    }

    public int getRestoreHealthPoints() {
        return this.restoreHealthPoints;
    }
}

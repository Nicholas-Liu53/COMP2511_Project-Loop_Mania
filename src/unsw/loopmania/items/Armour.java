package unsw.loopmania.items;

import org.javatuples.Pair;

public abstract class Armour extends Item {
    protected double damageReductionFactor;

    public Armour(Pair<Integer, Integer> position) {
        super(position);
    }

    public double getDamageReductionFactor() {
        return this.damageReductionFactor;
    }
}

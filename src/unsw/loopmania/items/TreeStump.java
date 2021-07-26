package unsw.loopmania.items;

import org.javatuples.Pair;

public class TreeStump extends Armour implements ShieldStrategy {
    public TreeStump(Pair<Integer, Integer> position) {
        super(position);
        this.damageReductionFactor = 0.6;
    }

    public TreeStump() {
        super(new Pair<Integer, Integer>(1, 2));
        this.damageReductionFactor = 0.6;
    }
}

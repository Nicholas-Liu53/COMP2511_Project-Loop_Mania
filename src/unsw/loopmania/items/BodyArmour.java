package unsw.loopmania.items;

import org.javatuples.Pair;

public class BodyArmour extends Armour implements BodyArmourStrategy {
    public BodyArmour(Pair<Integer, Integer> position) {
        super(position);
        this.purchasePrice = 500;
        this.sellPrice = 400;
        this.damageReductionFactor = 0.5;
    }

    public BodyArmour() {
        super(new Pair<Integer, Integer>(1, 2));
        this.purchasePrice = 500;
        this.sellPrice = 400;
        this.damageReductionFactor = 0.5;
    }

    public int receiveAttack(int damage) {
        // BodyArmour provides 5 defence points
        int recvDamage = (int) (damage * 0.5);
        return recvDamage;
    }
}

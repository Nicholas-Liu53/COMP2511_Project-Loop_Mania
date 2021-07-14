package unsw.loopmania.items;

import org.javatuples.Pair;

public class BodyArmour extends Armour implements BodyArmourStrategy {
    public BodyArmour(Pair<Integer, Integer> position) {
        super(position);
        purchasePrice = 500;
        sellPrice = 400;
        damageReductionFactor = 0.5;
    }

    public BodyArmour() {
        super(new Pair<Integer, Integer>(1, 2));
        purchasePrice = 500;
        sellPrice = 400;
        damageReductionFactor = 0.5;
    }

    public int receiveAttack(int damage) {
        // BodyArmour provides 5 defence
        int recvDamage = (int) (damage * 0.5);
        return recvDamage;
    }
}

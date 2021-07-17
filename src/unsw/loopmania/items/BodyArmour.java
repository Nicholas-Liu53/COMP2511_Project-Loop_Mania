package unsw.loopmania.items;

import org.javatuples.Pair;

public class BodyArmour extends Armour implements BodyArmourStrategy {
    public BodyArmour(Pair<Integer, Integer> position) {
        super(position);
        this.damageReductionFactor = 0.5;
    }

    public BodyArmour() {
        super(new Pair<Integer, Integer>(1, 2));
        this.damageReductionFactor = 0.5;
    }

    public int receiveAttack(int damage) {
        // BodyArmour provides 5 defence points
        int recvDamage = (int) (damage * 0.5);
        return recvDamage;
    }

    public static int getPurchasePrice() {
        return 500;
    }

    public static int getSellPrice() {
        return 400;
    }
}

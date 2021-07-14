package unsw.loopmania.items;

import org.javatuples.Pair;

public class Shield extends Armour implements ShieldStrategy {
    public Shield(Pair<Integer, Integer> position) {
        super(position);
        this.purchasePrice = 300;
        this.sellPrice = 240;
        this.damageReductionFactor = 0.7;
    }

    public Shield() {
        super(new Pair<Integer, Integer>(1, 2));
        this.purchasePrice = 300;
        this.sellPrice = 240;
        this.damageReductionFactor = 0.7;
    }

    public int receiveAttack(int damage) {
        // BodyArmour provides 3 defence
        int recvDamage = (int) (damage * 0.2);
        return recvDamage;
    }

}
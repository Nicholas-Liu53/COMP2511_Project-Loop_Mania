package unsw.loopmania.items;

import org.javatuples.Pair;

public class Helmet extends Armour implements HelmetStrategy {
    public Helmet(Pair<Integer, Integer> position) {
        super(position);
        purchasePrice = 200;
        sellPrice = 160;
        damageReductionFactor = 0.8;
    }

    public Helmet() {
        super(new Pair<Integer, Integer>(1, 2));
        purchasePrice = 200;
        sellPrice = 160;
        damageReductionFactor = 0.8;
    }

    public int receiveAttack(int damage) {
        // Helmet provides 1 defence
        int recvDamage = (int) (damage * 0.1) + 3;
        return recvDamage;
    }

    public int launchAttack() {
        return 2;
    }
}

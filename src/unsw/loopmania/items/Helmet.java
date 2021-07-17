package unsw.loopmania.items;

import org.javatuples.Pair;

public class Helmet extends Armour implements HelmetStrategy {
    public Helmet(Pair<Integer, Integer> position) {
        super(position);
        this.purchasePrice = 200;
        this.sellPrice = 160;
        this.damageReductionFactor = 0.8;
    }

    public Helmet() {
        super(new Pair<Integer, Integer>(1, 2));
        this.purchasePrice = 200;
        this.sellPrice = 160;
        this.damageReductionFactor = 0.8;
    }

    public int receiveAttack(int damage) {
        // Helmet provides 1 defence
        int recvDamage = 2;
        return recvDamage;
    }

    public int launchAttack() {
        return 2;
    }
}

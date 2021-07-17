package unsw.loopmania.items;

import org.javatuples.Pair;

public class Helmet extends Armour implements HelmetStrategy {
    public Helmet(Pair<Integer, Integer> position) {
        super(position);
        this.damageReductionFactor = 0.8;
    }

    public Helmet() {
        super(new Pair<Integer, Integer>(1, 2));
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

    public static int getPurchasePrice() {
        return 200;
    }

    public static int getSellPrice() {
        return 160;
    }
}

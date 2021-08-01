package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.enemies.Enemy;

public class Shield extends Armour implements ShieldStrategy {
    public Shield(Pair<Integer, Integer> position) {
        super(position);
        this.damageReductionFactor = 0.7;
    }

    public Shield() {
        super(new Pair<Integer, Integer>(1, 2));
        this.damageReductionFactor = 0.7;
    }

    public int receiveAttack(Enemy enemy, int damage) {
        // BodyArmour provides 3 defence
        int recvDamage = (int) (damage * 0.2);
        return recvDamage;
    }

    public static int getPurchasePrice() {
        return 300;
    }

    public static int getSellPrice() {
        return 240;
    }
}
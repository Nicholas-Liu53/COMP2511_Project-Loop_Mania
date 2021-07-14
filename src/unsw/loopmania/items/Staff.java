package unsw.loopmania.items;

import org.javatuples.Pair;
import unsw.loopmania.enemies.Enemy;

/**
 * represents an equipped or unequipped staff in the backend world
 */
public class Staff extends Item implements WeaponStrategy {
    public Staff(Pair<Integer, Integer> position) {
        super(position);
        this.purchasePrice = 300;
        this.sellPrice = 240;
    }

    public Staff() {
        super(new Pair<Integer, Integer>(1, 2));
        this.purchasePrice = 300;
        this.sellPrice = 240;
    }

    public void launchAttack(Enemy enemy, int baseDamage) {
        enemy.receiveAttack(baseDamage + 3);
    }
}
package unsw.loopmania.items;

import org.javatuples.Pair;
import unsw.loopmania.enemies.Enemy;

/**
 * represents an equipped or unequipped staff in the backend world
 */
public class Staff extends Item implements WeaponStrategy {
    public Staff(Pair<Integer, Integer> position) {
        super(position);
    }

    public Staff() {
        super(new Pair<Integer, Integer>(1, 2));
    }

    public void launchAttack(Enemy enemy, int baseDamage) {
        enemy.receiveAttack(baseDamage + 3);
    }

    public static int getPurchasePrice() {
        return 300;
    }

    public static int getSellPrice() {
        return 240;
    }
}
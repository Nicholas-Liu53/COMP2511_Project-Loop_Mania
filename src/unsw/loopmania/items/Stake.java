package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.enemies.VampireEnemy;

/**
 * represents an equipped or unequipped stake in the backend world
 */
public class Stake extends Item implements WeaponStrategy {
    public Stake(Pair<Integer, Integer> position) {
        super(position);
        purchasePrice = 150;
        sellPrice = 120;
    }

    public Stake() {
        super(new Pair<Integer, Integer>(1, 2));
        purchasePrice = 150;
        sellPrice = 120;
    }

    public void launchAttack(Enemy enemy, int baseDamage) {
        if (enemy instanceof VampireEnemy) {
            // Greater damage to vampires
            enemy.receiveAttack(baseDamage + 15);
        } else {
            enemy.receiveAttack(baseDamage + 5);
        }
    }
}

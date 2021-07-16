package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.Character;
import unsw.loopmania.enemies.Enemy;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Item implements WeaponStrategy {
    public Sword(Pair<Integer, Integer> position) {
        super(position);
        this.purchasePrice = 200;
        this.sellPrice = 160;
    }

    public Sword() {
        super(new Pair<Integer, Integer>(1, 2));
        this.purchasePrice = 200;
        this.sellPrice = 160;
    }

    public void launchAttack(Enemy enemy, int baseDamage, Character mainChar) {
        // Simply adds 10 extra damage
        enemy.receiveAttack(baseDamage + 10);
    }
}

package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.character.Character;
import unsw.loopmania.enemies.Enemy;

/**
 * represents an equipped or unequipped anduril in the backend world
 */
public class Anduril extends Item implements WeaponStrategy, RareItem, ShieldStrategy {
    private Item confusingItem;

    public Anduril(Pair<Integer, Integer> position) {
        super(position);
    }

    public Anduril() {
        super(new Pair<Integer, Integer>(1, 2));
    }

    public void launchAttack(Enemy enemy, int baseDamage, Character mainChar) {
        // Causes triple damage against bosses
        enemy.receiveAttack(baseDamage + 15);
    }

    public void setConfusingItem(Item item) {
        this.confusingItem = item;
    }

    public Item getConfusingItem() {
        return confusingItem;
    }

    public int receiveAttack(int damage) {
        // TreeStump provides 3 defence
        ShieldStrategy shield = (ShieldStrategy) confusingItem;
        return shield.receiveAttack(damage);
    }
}

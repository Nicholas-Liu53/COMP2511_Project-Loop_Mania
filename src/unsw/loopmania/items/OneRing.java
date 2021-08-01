package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.character.Character;
import unsw.loopmania.enemies.Enemy;

public class OneRing extends Item implements RareItem, WeaponStrategy, ShieldStrategy {
    private Item confusingItem;

    public OneRing(Pair<Integer, Integer> position) {
        super(position);
    }

    public OneRing() {
        super(new Pair<Integer, Integer>(1, 2));
    }

    public static int getRestoreHealthPoints() {
        return 100;
    }

    public void setConfusingItem(Item item) {
        this.confusingItem = item;
    }

    public Item getConfusingItem() {
        return confusingItem;
    }

    public void launchAttack(Enemy enemy, int baseDamage, Character mainChar) {
        WeaponStrategy weapon = (WeaponStrategy) confusingItem;
        weapon.launchAttack(enemy, baseDamage, mainChar);
    }

    public int receiveAttack(Enemy enemy, int damage) {
        // TreeStump provides 3 defence
        ShieldStrategy shield = (ShieldStrategy) confusingItem;
        return shield.receiveAttack(enemy, damage);
    }
}
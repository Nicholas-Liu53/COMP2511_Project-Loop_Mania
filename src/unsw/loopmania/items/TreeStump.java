package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.character.Character;

public class TreeStump extends Armour implements ShieldStrategy, RareItem, WeaponStrategy {
    private Item confusingItem;

    public TreeStump(Pair<Integer, Integer> position) {
        super(position);
        this.damageReductionFactor = 0.6;
    }

    public TreeStump() {
        super(new Pair<Integer, Integer>(1, 2));
        this.damageReductionFactor = 0.6;
    }

    public int receiveAttack(int damage) {
        // TreeStump provides 3 defence
        int recvDamage = (int) (damage * 0.45);
        return recvDamage;
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
}

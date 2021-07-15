package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.enemies.Enemy;

/**
 * represents an equipped or unequipped staff in the backend world
 */
public class Staff extends Item implements WeaponStrategy {
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Weapon");
    }

    public Staff() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2), "Weapon");
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
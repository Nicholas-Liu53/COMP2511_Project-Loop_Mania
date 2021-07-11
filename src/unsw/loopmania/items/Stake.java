package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.enemies.VampireEnemy;

/**
 * represents an equipped or unequipped stake in the backend world
 */
public class Stake extends Item implements WeaponStrategy {

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Weapon");
        purchasePrice = 150;
        sellPrice = 120;
    }

    public Stake() {
        super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2), "Weapon");
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

package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.enemies.Enemy;

/**
 * Weapon strategy applied to weapon items
 * defines the attack that a weapon can launch against
 * and enemy
 */
public interface WeaponStrategy {
    /**
     * Launch attack method for the weapon, used to inflict damage
     * and special attacks against enemy
     * @param enemy
     * @param baseDamage
     */
    public void launchAttack(Enemy enemy, int baseDamage);
}

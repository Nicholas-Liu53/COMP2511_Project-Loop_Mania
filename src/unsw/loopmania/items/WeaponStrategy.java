package unsw.loopmania.items;

import unsw.loopmania.Character;
import unsw.loopmania.enemies.Enemy;

/**
 * Weapon strategy applied to weapon items defines the attack that a weapon can
 * launch against and enemy
 */
public interface WeaponStrategy extends BasicItemStrategy {
    /**
     * Launch attack method for the weapon, used to inflict damage and special
     * attacks against enemy
     * 
     * @param enemy
     * @param baseDamage
     */
    public void launchAttack(Enemy enemy, int baseDamage, Character mainChar);
}

package unsw.loopmania;

import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.items.BodyArmourStrategy;
import unsw.loopmania.items.WeaponStrategy;

/**
 * Defines basic armour and weapon strategies that the character has
 * equipped at the beginning of the game
 */
public class Melee implements WeaponStrategy, BodyArmourStrategy {
    public void launchAttack(Enemy enemy, int baseDamage) {
        enemy.receiveAttack(baseDamage);
    }

    public int receiveAttack(int damage) {
        return damage;
    }
}

package unsw.loopmania.character;

import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.items.BodyArmourStrategy;
import unsw.loopmania.items.HelmetStrategy;
import unsw.loopmania.items.ShieldStrategy;
import unsw.loopmania.items.WeaponStrategy;

/**
 * Defines basic armour and weapon strategies that the character has
 * equipped at the beginning of the game
 */
public class Melee implements WeaponStrategy, BodyArmourStrategy, ShieldStrategy, HelmetStrategy {
    public void launchAttack(Enemy enemy, int baseDamage, Character mainChar) {
        enemy.receiveAttack(baseDamage);
    }

    public int receiveAttack(int damage) {
        // Provides no damage protection
        return 0;
    }

    public int launchAttack() {
        return 0;
    }
}

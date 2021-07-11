package unsw.loopmania;

import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.items.WeaponStrategy;

public class Melee implements WeaponStrategy {
    public void launchAttack(Enemy enemy, int baseDamage) {
        enemy.receiveAttack(baseDamage);
    }
}

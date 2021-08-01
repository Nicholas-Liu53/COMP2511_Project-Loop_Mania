package unsw.loopmania.items;

import unsw.loopmania.enemies.Enemy;

/**
 * Interface used for implementing armour items
 */
public interface ArmourStrategy {
    /**
     * Receive attack method for the armour, takes in damage received and returns
     * damage received after armour defence is factored in
     * 
     * @param damage
     * @return
     */
    public int receiveAttack(Enemy enemy, int damage);
}
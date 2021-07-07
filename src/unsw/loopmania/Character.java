package unsw.loopmania;

import unsw.loopmania.path.PathPosition;
import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.items.Item;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    // TODO = potentially implement relationships between this class and other
    // classes
    public Character(PathPosition position) {
        super(position);
        // TODO: add initial health for character
    }

    /**
     * @return Characters's health value
     */
    public int getHealth() {
        return 0;
    }

    public boolean getInBattle() {
        return false;
    }

    public void setInBattle(boolean battleStatus) {

    }

    /**
     * Allows the Character to launch an attack against an enemy, resulting in
     * damage and possibly using a special attack using inventory items
     * 
     * @param mainChar
     */
    public void launchAttack(Enemy enemy) {

    }

    /**
     * Allows Character to receive an attack, takes in the amount of damage to be
     * done and subtracts the relavent amount from health after defence is factored
     * in
     * 
     * @param damage
     */
    public void receiveAttack(int damage) {

    }

    public void equipItem(Item item) {

    }

}

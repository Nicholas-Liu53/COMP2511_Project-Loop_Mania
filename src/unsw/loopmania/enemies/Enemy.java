package unsw.loopmania.enemies;

import java.util.Random;

import unsw.loopmania.MovingEntity;
import unsw.loopmania.path.PathPosition;

public abstract class Enemy extends MovingEntity {
    
    /**
     * Takes in path position and spawns enemy there
     * @param position
     */
    public Enemy(PathPosition position) {
        super(position);
    }

    /**
     * move the enemy
     */
    public void move(){
        // TODO = modify this, since this implementation doesn't provide the expected enemy behaviour
        // this basic enemy moves in a random direction... 25% chance up or down, 50% chance not at all...
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }

    /**
     * @return enemy's health value
     */
    public abstract int getHealth();

    /**
     * @return Enemy's support radius
     */
    public abstract int getSupportRadius();

    /**
     * @return Enemy's support radius
     */
    public abstract int getAttackRadius();

    /**
     * Allows the enemy to launch an attack against a character, doing damage and possibly
     * using a special attack
     * @param mainChar
     */
    public abstract void launchAttack(Character mainChar);

    /**
     * Allows enemy to receive an attack, takes in the amount of damage to be done and subtracts
     * the relavent amount from health after defence is factored in 
     * @param damage
     */
    public abstract void receiveAttack(int damage);
}

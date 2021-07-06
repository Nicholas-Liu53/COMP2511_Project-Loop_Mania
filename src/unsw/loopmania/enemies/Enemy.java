package unsw.loopmania.enemies;

import java.util.Random;

import unsw.loopmania.MovingEntity;
import unsw.loopmania.path.PathPosition;

public abstract class Enemy extends MovingEntity {
    // Attribute
    int health;
    int supportRadius;
    int attackRadius;
    int damage;
    float defenceFactor;

    /**
     * Takes in path position and spawns enemy there
     * @param position
     */
    public Enemy(PathPosition position, int health, int supportRadius, int attackRadius, int damage, int defence) {
        super(position);
        this.health = health;
        this.supportRadius = supportRadius;
        this.attackRadius = attackRadius;
        this.damage = damage;
        this.defenceFactor = 1 - (defence / 100);
    }

    /**
     * move the enemy
     */
    public void move(){
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }

    /**
     * @return enemy's health value, should never be less than 0
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * @return Enemy's support radius
     */
    public int getSupportRadius() {
        return this.supportRadius;
    }

    /**
     * @return Enemy's support radius
     */
    public int getAttackRadius() {
        return this.attackRadius;
    }

    /**
     * Allows the enemy to launch an attack against a character, doing damage and possibly
     * using a special attack
     * @param mainChar
     */
    public void launchAttack(Character mainChar) {
        mainChar.receiveAttack(this.damage);
    }

    /**
     * Allows enemy to receive an attack, takes in the amount of damage to be done and subtracts
     * the relavent amount from health after defence is factored in 
     * @param damage
     */
    public void receiveAttack(int recvDamage) {
        // Adjusting for defence
        recvDamage = (int)(recvDamage * this.defenceFactor);
        this.health -= recvDamage;
        // Health should not be less than 0
        if (this.health < 0) {
            this.health = 0;
        }
    }
}

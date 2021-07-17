package unsw.loopmania.enemies;

import java.util.Random;

import unsw.loopmania.MovingEntity;
import unsw.loopmania.Character;
import unsw.loopmania.Ally;
import unsw.loopmania.path.PathPosition;

public abstract class Enemy extends MovingEntity implements Ally {
    // Attribute
    int health;
    int supportRadius;
    int attackRadius;
    int damage;
    int tranceCountdown;
    float defenceFactor;
    boolean inBattle;

    /**
     * Takes in path position and spawns enemy there
     * 
     * @param position
     */
    public Enemy(PathPosition position, int health, int supportRadius, int attackRadius, int damage, int defence) {
        super(position);
        this.health = health;
        this.supportRadius = supportRadius;
        this.attackRadius = attackRadius;
        this.damage = damage;
        this.tranceCountdown = 0;
        this.defenceFactor = 1 - ((float) defence / 100);
        this.inBattle = false;
    }

    /**
     * move the enemy
     */
    public void move() {
        if (!this.inBattle) {
            int directionChoice = (new Random()).nextInt(2);
            if (directionChoice == 0) {
                moveUpPath();
            } else if (directionChoice == 1) {
                moveDownPath();
            }
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
     * @return true if enemy is in a battle, otherwise false
     */
    public boolean getInBattle() {
        return this.inBattle;
    }

    /**
     * @return Number of trance ticks left for enemy
     */
    public int getTranceCount() {
        this.tranceCountdown -= 1;

        if (this.tranceCountdown < 0)
            this.tranceCountdown = 0;

        return this.tranceCountdown;
    }

    /**
     * Sets length of time that enemy is entranced
     * 
     * @param tranceCount
     */
    public void setTranceCount(int tranceCount) {
        this.tranceCountdown = tranceCount;
    }

    /**
     * Sets whether an enemy is currently in battle, preventing it from moving
     * 
     * @param inBattle
     */
    public void setInBattle(boolean inBattle) {
        this.inBattle = inBattle;
    }

    /**
     * Allows the enemy to launch an attack against a character, doing damage and
     * possibly using a special attack
     * 
     * @param mainChar
     * @return whether the enemy has used a special attack that needs to be handled
     */
    public boolean launchAttack(Character mainChar) {
        mainChar.receiveAttack(this.damage);
        return false;
    }

    public void launchAttack(Enemy enemy) {
        enemy.receiveAttack(this.damage);
    }
 
    /**
     * Allows enemy to receive an attack, takes in the amount of damage to be done
     * and subtracts the relavent amount from health after defence is factored in
     * 
     * @param damage
     */
    public void receiveAttack(int recvDamage) {
        this.inBattle = true;
        // Adjusting for defence
        recvDamage = (int) (recvDamage * this.defenceFactor);
        this.health -= recvDamage;
        // Health should not be less than 0
        if (this.health < 0) {
            this.health = 0;
        }
    }
}

package unsw.loopmania;

import unsw.loopmania.path.PathPosition;

import java.util.ArrayList;

import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.items.HealthPotion;
import unsw.loopmania.items.Item;
import unsw.loopmania.items.WeaponStrategy;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    private int health;
    private int damage;
    private WeaponStrategy weaponStrat;
    private ArrayList<Enemy> currentlyBattling;
    private ArrayList<Item> equippedItems;
    private int experience;
    private int gold;

    // TODO = potentially implement relationships between this class and other
    // classes
    public Character(PathPosition position) {
        super(position);
        this.health = 100;
        this.damage = 5;
        this.weaponStrat = new Melee();
        this.currentlyBattling = new ArrayList<Enemy>();
        this.equippedItems = new ArrayList<>();
        this.experience = 0;
        this.gold = 0;
    }

    /**
     * @return Characters's health value, can never be less than 0
     */
    public int getHealth() {
        return this.health;
    }

    public int getDamage() {
        return this.damage;
    }

    /**
     * @return true if the character is in a battle, else false
     */
    public boolean getInBattle() {
        if (this.currentlyBattling.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Item> getEquippedItems() {
        return this.equippedItems;
    }

    public int getExperience() {
        return this.experience;
    }

    public int getGold() {
        return this.gold;
    }

    public WeaponStrategy getWeapon() {
        return this.weaponStrat;
    }

    /**
     * Adds enemy to list of enemies that the character is currently battling
     * only adds enemy it it is not already on the list
     * @param enemy
     */
    public void addBattle(Enemy enemy) {
        if (!this.currentlyBattling.contains(enemy)) {
            this.currentlyBattling.add(enemy);
        }
    }

    /**
     * Removes enemy from list of enemies that the character is currently battling
     * should only be invoked if the enemy is defeated
     * @param enemy
     */
    public void removeBattle(Enemy enemy) {
        if (this.currentlyBattling.contains(enemy)) {
            this.currentlyBattling.remove(enemy);
        }
    }

    /**
     * Allows the Character to launch an attack against an enemy, resulting in
     * damage and possibly using a special attack using inventory items
     * 
     * @param mainChar
     */
    public void launchAttack(Enemy enemy) {
        this.weaponStrat.launchAttack(enemy, this.damage);
    }

    /**
     * Allows Character to receive an attack, takes in the amount of damage to be
     * done and subtracts the relavent amount from health after defence is factored
     * in
     * 
     * @param damage
     */
    public void receiveAttack(int damage) {
        this.health -= damage;
        if (this.health < 0)
            this.health = 0;
    }

    /**
     * Equips health potion
     * @param item
     */
    public void equipItem(HealthPotion item) {
        this.health = 100;
        this.equippedItems.add(item); // equipping item
    }

    /**
     * Equips weapon
     * @param item
     */
    public void equipItem(WeaponStrategy item) {
        this.weaponStrat = item;
    }

    public void giveExperiencePoints(int xp) {
        this.experience += xp; // max xp?
    }

    public void giveGold(int gold) {
        this.gold += gold; // max gold?
    }

    @Override
    public void moveDownPath() {
        if(!this.getInBattle()) {
            super.moveDownPath();
        }
    }

}

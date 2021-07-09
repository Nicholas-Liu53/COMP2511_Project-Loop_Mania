package unsw.loopmania;

import java.util.ArrayList;

import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.items.Item;
import unsw.loopmania.path.PathPosition;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    private int health;
    private int damage;
    private ArrayList<Enemy> enemiesCurrentlyBattling;
    private ArrayList<Item> equippedItems;
    private int experience;
    private int gold;

    /**
     * Creates a new Character in the game world at specified path position with
     * initial health as 100 and ability to damage enemies as 5
     * 
     * @param position
     */
    public Character(PathPosition position) {
        super(position);
        this.health = 100;
        this.damage = 5;
        this.enemiesCurrentlyBattling = new ArrayList<Enemy>();
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

    /**
     * @return damage given to an enemy by Character
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * @return true if the character is currently battling an enemy, else false
     */
    public boolean getInBattle() {
        if (this.enemiesCurrentlyBattling.size() == 0)
            return false;
        else
            return true;
    }

    /**
     * @return list of items equipped by Character in order to defeat the enemy
     */
    public ArrayList<Item> getEquippedItems() {
        return this.equippedItems;
    }

    /**
     * @return experience points collected / earned by Character in the game so far
     */
    public int getExperience() {
        return this.experience;
    }

    /**
     * @return gold collected / earned by Character in the game so far
     */
    public int getGold() {
        return this.gold;
    }

    /**
     * Adds enemy to list of enemies that the character is currently battling, only
     * adds enemy if it is not already on the list
     * 
     * @param enemy
     */
    public void addBattle(Enemy enemy) {
        if (!this.enemiesCurrentlyBattling.contains(enemy))
            this.enemiesCurrentlyBattling.add(enemy);
    }

    /**
     * Removes enemy from list of enemies that the character is currently battling
     * should only be invoked if the enemy is defeated
     * 
     * @param enemy
     */
    public void removeEnemyFromBattle(Enemy enemy) {
        if (this.enemiesCurrentlyBattling.contains(enemy))
            this.enemiesCurrentlyBattling.remove(enemy);
    }

    /**
     * Allows the Character to launch an attack against an enemy, resulting in
     * damage and possibly using a special attack using inventory items
     * 
     * @param mainChar
     */
    public void launchAttack(Enemy enemy) {
        enemy.receiveAttack(this.damage);
    }

    /**
     * Allows Character to receive an attack, takes in the amount of damage to be
     * done and subtracts the relavent amount from health, health can never be less
     * than zero
     * 
     * @param damage
     */
    public void receiveAttack(int damage) {
        this.health -= damage;
        if (this.health < 0)
            this.health = 0;
    }

    /**
     * Allows Character to equip different items to be able to win the battle
     * against enemy
     * 
     * Special Item: Health Potion - restores health to 100
     * 
     * @param item
     */
    public void equipItem(Item item) {
        if (item.getItemID().equals("HealthPotion"))
            this.health = 100;

        this.equippedItems.add(item); // equipping item
    }

    /**
     * Gives Character experience points as specified
     * 
     * @param xp
     */
    public void giveExperiencePoints(int xp) {
        this.experience += xp; // max xp?
    }

    /**
     * Gives Character gold as specified
     * 
     * @param xp
     */
    public void giveGold(int gold) {
        this.gold += gold; // max gold?
    }

    @Override
    public void moveDownPath() {
        if (!this.getInBattle())
            super.moveDownPath();
    }
}

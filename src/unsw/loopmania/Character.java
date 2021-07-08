package unsw.loopmania;

import unsw.loopmania.path.PathPosition;

import java.util.ArrayList;

import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.items.Item;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    private int health;
    private int damage;
    private boolean inBattle;
    private ArrayList<Item> equippedItems;
    private int experience;
    private int gold;

    // TODO = potentially implement relationships between this class and other
    // classes
    public Character(PathPosition position) {
        super(position);
        this.health = 100;
        this.damage = 5;
        this.inBattle = false;
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

    public boolean getInBattle() {
        return this.inBattle;
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

    public void setInBattle(boolean battleStatus) {
        this.inBattle = battleStatus;
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
     * done and subtracts the relavent amount from health after defence is factored
     * in
     * 
     * @param damage
     */
    public void receiveAttack(int damage) {
        this.inBattle = true;
        this.health -= damage;
        if (this.health < 0)
            this.health = 0;
    }

    public void equipItem(Item item) {
        if (item.getItemID().equals("HealthPotion"))
            this.health = 100;

        this.equippedItems.add(item); // equipping item
    }

    public void giveExperiencePoints(int xp) {
        this.experience += xp; // max xp?
    }

    public void giveGold(int gold) {
        this.gold += gold; // max gold?
    }

    @Override
    public void moveUpPath() {
        if(!this.inBattle) {
            super.moveUpPath();
        }
    }

}

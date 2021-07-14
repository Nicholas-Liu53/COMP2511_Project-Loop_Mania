package unsw.loopmania;

import java.util.ArrayList;

import unsw.loopmania.enemies.Enemy;
import unsw.loopmania.items.BodyArmourStrategy;
import unsw.loopmania.items.HelmetStrategy;
import unsw.loopmania.items.Item;
import unsw.loopmania.items.ShieldStrategy;
import unsw.loopmania.path.PathPosition;
import unsw.loopmania.items.WeaponStrategy;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    private int health;
    private final int damage = 5;
    private ArrayList<Enemy> enemiesCurrentlyBattling;
    private WeaponStrategy weaponStrat;
    private BodyArmourStrategy bodyArmourStrat;
    private ShieldStrategy shieldStrat;
    private HelmetStrategy helmetStrat;
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
        this.enemiesCurrentlyBattling = new ArrayList<>();
        this.weaponStrat = new Melee();
        this.bodyArmourStrat = new Melee();
        this.shieldStrat = new Melee();
        this.helmetStrat = new Melee();
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
        return !this.enemiesCurrentlyBattling.isEmpty();
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

    public WeaponStrategy getWeapon() {
        return this.weaponStrat;
    }

    public BodyArmourStrategy getBodyArmour() {
        return this.bodyArmourStrat;
    }

    public ShieldStrategy getShield() {
        return this.shieldStrat;
    }

    public HelmetStrategy getHelmet() {
        return this.helmetStrat;
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
     * @param enemy
     */
    public void launchAttack(Enemy enemy, boolean inCampfireRadius) {
        int giveDamage = this.damage;

        if (inCampfireRadius)
            giveDamage *= 2;

        this.weaponStrat.launchAttack(enemy, (giveDamage - this.helmetStrat.launchAttack()));
    }

    /**
     * Allows Character to receive an attack, takes in the amount of damage to be
     * done and subtracts the relavent amount from health, health can never be less
     * than zero
     * 
     * @param damage
     */
    public void receiveAttack(int damage) {
        // Subtracting armour defence
        int actualDamage = damage - this.bodyArmourStrat.receiveAttack(damage);
        actualDamage = actualDamage - this.helmetStrat.receiveAttack(damage);
        actualDamage = actualDamage - this.shieldStrat.receiveAttack(damage);

        if (actualDamage < 0)
            actualDamage = 0;

        this.health -= actualDamage;
        if (this.health < 0)
            this.health = 0;
    }

    /**
     * Allows Character to equip different items to be able to win the battle
     * against enemy
     * 
     * @param item
     */
    public void equipItem(Item item) {
        this.equippedItems.add(item);
    }

    /**
     * Equips weapon
     * 
     * @param item
     */
    public void equipItem(WeaponStrategy item) {
        this.weaponStrat = item;
    }

    /**
     * Equips body armour
     * 
     * @param item
     */
    public void equipItem(BodyArmourStrategy item) {
        this.bodyArmourStrat = item;
    }

    /**
     * Equips shield
     * 
     * @param item
     */
    public void equipItem(ShieldStrategy item) {
        this.shieldStrat = item;
    }

    /**
     * Equips helmet
     * 
     * @param item
     */
    public void equipItem(HelmetStrategy item) {
        this.helmetStrat = item;
    }

    public void giveExperiencePoints(int xp) {
        this.experience += xp; // max xp?
    }

    /**
     * Gives Character specified amount of gold
     * 
     * @param gold
     */
    public void giveGold(int gold) {
        this.gold += gold; // max gold?
    }

    /**
     * Restores Character's health to maximum
     */
    public void restoreHealthPoints() {
        this.health = 100;
    }

    /**
     * @return true if character has full health
     */
    public boolean isFullHealth() {
        return this.health == 100;
    }

    @Override
    public void moveDownPath() {
        if (!this.getInBattle())
            super.moveDownPath();
    }
}

package unsw.loopmania.character;

import java.util.ArrayList;

import javafx.scene.media.AudioClip;
import unsw.loopmania.MovingEntity;
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
    private int maxHealth;
    private int level;
    private int damage;
    private ArrayList<Enemy> enemiesCurrentlyBattling;
    private WeaponStrategy weaponStrat;
    private BodyArmourStrategy bodyArmourStrat;
    private ShieldStrategy shieldStrat;
    private HelmetStrategy helmetStrat;
    private ArrayList<Item> equippedItems;
    private ArrayList<Ally> allies;
    private int experience;
    private int gold;
    private boolean isStunned;

    /**
     * Creates a new Character in the game world at specified path position with
     * initial health as 100 and ability to damage enemies as 5
     * 
     * @param position
     */
    public Character(PathPosition position) {
        super(position);
        this.health = 100;
        this.maxHealth = 100;
        this.level = 0;
        this.damage = 5;
        this.enemiesCurrentlyBattling = new ArrayList<>();
        this.weaponStrat = new Melee();
        this.bodyArmourStrat = new Melee();
        this.shieldStrat = new Melee();
        this.helmetStrat = new Melee();
        this.equippedItems = new ArrayList<>();
        this.allies = new ArrayList<>();
        this.experience = 0;
        this.gold = 0;
        this.isStunned = false;
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
     * @return character's current level
     */
    public int getLevel() {
        return this.level;
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

    /**
     * @return List of character's current allies
     */
    public ArrayList<Ally> getAllies() {
        return this.allies;
    }

    public int getNumAllies() {
        return this.allies.size();
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

    public void removeAlly() {
        this.allies.remove(this.allies.get(0));
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
     * Checks first ally and removes them if they have run
     * out of health
     */
    private void updateAllies() {
        if (this.allies.get(0).getHealth() == 0) {
            this.allies.remove(this.allies.get(0));
        } else if (this.allies.get(0) instanceof Enemy) {
            // Ally is a tranced enemy
            Enemy enemy = (Enemy) this.allies.get(0);

            if (enemy.getTranceCount() == 0)
                this.allies.remove(this.allies.get(0));

            if (!this.getInBattle()) {
                this.allies.remove(this.allies.get(0));
                enemy.setTranceCount(0);
                this.allies.get(0).receiveAttack(1000);
            }

        }
    }

    /**
     * Adds enemy as ally at index 0 in allies list
     * 
     * @param enemy
     */
    public void addTrancedEnemy(Enemy enemy) {
        if (this.enemiesCurrentlyBattling.size() > 1) {
            enemy.setTranceCount(5);
            this.removeEnemyFromBattle(enemy);
            this.allies.add(0, enemy);
        }
    }

    /**
     * Allows the Character to launch an attack against an enemy, resulting in
     * damage and possibly using a special attack using inventory items
     * 
     * @param enemy
     */
    public void launchAttack(Enemy enemy, boolean inCampfireRadius) {
        int giveDamage = this.damage;

        // Checking if an ally can launch attack first
        if (this.allies.size() != 0) {
            this.allies.get(0).launchAttack(enemy);
            return;
        }
        
        if (isStunned) {
            isStunned = false;
            return;
        }

        AudioClip launchAttackSound = new AudioClip("file:src/sounds/launchattack.wav");
        launchAttackSound.play();

        if (inCampfireRadius)
            giveDamage *= 2;

        this.weaponStrat.launchAttack(enemy, (giveDamage - this.helmetStrat.launchAttack()), this);
    }

    /**
     * Allows Character to receive an attack, takes in the amount of damage to be
     * done and subtracts the relavent amount from health, health can never be less
     * than zero
     * 
     * @param damage
     */
    public void receiveAttack(int damage) {
        // Allies receive damage first
        if (this.allies.size() > 0) {
            Ally currAlly = this.allies.get(0);
            currAlly.receiveAttack(damage);
            this.updateAllies();
        } else {
            // Subtracting armour defence
            int actualDamage = damage - this.bodyArmourStrat.receiveAttack(damage);
            actualDamage = actualDamage - this.helmetStrat.receiveAttack(damage);
            actualDamage = actualDamage - this.shieldStrat.receiveAttack(damage);
            
            if (actualDamage < 0)
            actualDamage = 0;
            
            AudioClip receiveAttackSound = new AudioClip("file:src/sounds/receiveattack.wav");
            receiveAttackSound.play();
            this.health -= actualDamage;
            if (this.health < 0)
                this.health = 0;
        }
    }

    public void receiveStunAttack() {
        this.isStunned = true;
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

    /**
     * Gives character XP, and levels them up each time 1000 xp is acquired
     * 
     * @param xp
     */
    public void giveExperiencePoints(int xp) {
        this.experience += xp;

        // Leveling up
        if ((this.experience / 1000) > this.level) {
            this.level++;
            this.damage++;
            this.maxHealth += 10;
        }
    }

    /**
     * Gives Character specified amount of gold
     * 
     * @param gold
     */
    public void giveGold(int gold) {
        this.gold += gold; // max gold?
    }

    public void removeGold(int gold) {
        this.gold -= gold;
    }

    /**
     * Adds a new ally to the character, given there is space
     * 
     * @param newAlly
     */
    public void addAlly(Ally newAlly) {
        if (this.allies.size() < 4) {
            this.allies.add(newAlly);
        }
    }

    /**
     * Restores Character's health to maximum
     */
    public void restoreHealthPoints() {
        this.health = this.maxHealth;
    }

    /**
     * @return true if character has full health
     */
    public boolean isFullHealth() {
        return this.health == this.maxHealth;
    }

    @Override
    public void moveDownPath() {
        if (!this.getInBattle()) {
            super.moveDownPath();
            AudioClip footsteps = new AudioClip("file:src/sounds/footsteps.wav");
            footsteps.play();
        }
    }
}

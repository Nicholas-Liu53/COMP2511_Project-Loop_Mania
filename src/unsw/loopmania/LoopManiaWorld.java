package unsw.loopmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.javatuples.Pair;

import javafx.scene.media.AudioClip;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import unsw.loopmania.buildingcards.*;
import unsw.loopmania.buildings.*;
import unsw.loopmania.character.Character;
import unsw.loopmania.character.Melee;
import unsw.loopmania.enemies.*;
import unsw.loopmania.goals.ComplexGoalComponent;
// import unsw.loopmania.goals.ComplexGoalComposite;
import unsw.loopmania.items.*;
import unsw.loopmania.path.*;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one entity
 * can occupy the same square.
 */
public class LoopManiaWorld {
    // *-------------------------------------------------------------------------
    // * Variables
    // *-------------------------------------------------------------------------
    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;

    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    /**
     * generic entities - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private Character character;

    private List<Enemy> enemies;

    private List<Card> cardEntities;

    // Rare items specific to the world
    private List<String> rareItemNames;

    private List<Item> unequippedInventoryItems;

    // private List<Item> equippedInventoryItems;

    // The goal for this world
    private ComplexGoalComponent goal;

    private List<Building> buildingEntities;
    private List<Pair<Integer, Integer>> locationOfPlacedBuildings;
    private int numCycles;
    private int cycleShopLinear;
    private int numCyclesToOpenShop;
    private int spawnDoggieCycle;
    private int spawnElanCycle;
    private boolean showShop;
    private List<Item> pathItems;
    private int numGoldPileSpawned;
    private int numHealthPotionSpawned;
    private ArrayList<Enemy> newEnemies;
    private ArrayList<WorldStateObserver> observers;
    private StringProperty charHealthProperty;
    private StringProperty charGoldProperty;
    private StringProperty charXPProperty;
    private StringProperty charAlliesProperty;
    private StringProperty charGoalsProperty;
    private StringProperty charLevelProperty;
    private int numSword;
    private int numStake;
    private int numStaff;
    private int numBodyArmour;
    private int numHelmet;
    private int numShield;
    private int numHealthPotion;
    private int numOneRing;
    private int numDoggieCoin;
    private int numAnduril;
    private int numTreeStump;
    private int doggieCoinPrice;
    private int confusingGamemodeSeed;
    private StringProperty numSwordProperty;
    private StringProperty numStakeProperty;
    private StringProperty numStaffProperty;
    private StringProperty numBodyArmourProperty;
    private StringProperty numHelmetProperty;
    private StringProperty numShieldProperty;
    private StringProperty numHealthPotionProperty;
    private StringProperty numOneRingProperty;
    private StringProperty numDoggieCoinProperty;
    private StringProperty doggieCoinPriceProperty;
    private StringProperty numAndurilProperty;
    private StringProperty numTreeStumpProperty;

    private StringProperty currCycleNumProperty;
    private StringProperty cyclesTillShopProperty;

    private StringProperty gamemodeProperty;
    private StringProperty cycleOrCyclesProperty;

    private String gamemode = "Standard";
    private ArrayList<WorldStateObserver> deadObservers;
    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse
     * them
     */
    private List<Pair<Integer, Integer>> orderedPath;
    private Pair<Integer, Integer> startingPoint;

    // --------------------------------------------------------------------------
    // Constructor
    // --------------------------------------------------------------------------
    /**
     * create the world (constructor)
     * 
     * @param width       width of world in number of cells
     * @param height      height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing
     *                    position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        this.nonSpecifiedEntities = new ArrayList<>();
        this.character = null;
        this.enemies = new ArrayList<>();
        this.cardEntities = new ArrayList<>();
        this.rareItemNames = new ArrayList<>();
        this.unequippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        this.startingPoint = orderedPath.get(0);
        this.buildingEntities = new ArrayList<>();
        this.locationOfPlacedBuildings = new ArrayList<>();
        this.numCycles = 0;
        this.cycleShopLinear = 1;
        this.spawnDoggieCycle = 20;
        this.spawnElanCycle = 40;
        this.numCyclesToOpenShop = 1;
        this.showShop = false;
        this.pathItems = new ArrayList<>();
        this.numGoldPileSpawned = 0;
        this.numHealthPotionSpawned = 0;
        this.newEnemies = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.deadObservers = new ArrayList<>();
        this.charHealthProperty = new SimpleStringProperty();
        this.charGoldProperty = new SimpleStringProperty();
        this.charXPProperty = new SimpleStringProperty();
        this.charAlliesProperty = new SimpleStringProperty();
        this.charLevelProperty = new SimpleStringProperty();
        this.charGoalsProperty = new SimpleStringProperty();

        this.numSwordProperty = new SimpleStringProperty();
        this.numStakeProperty = new SimpleStringProperty();
        this.numStaffProperty = new SimpleStringProperty();
        this.numBodyArmourProperty = new SimpleStringProperty();
        this.numHelmetProperty = new SimpleStringProperty();
        this.numShieldProperty = new SimpleStringProperty();
        this.numHealthPotionProperty = new SimpleStringProperty();
        this.numOneRingProperty = new SimpleStringProperty();
        this.numDoggieCoinProperty = new SimpleStringProperty();
        this.doggieCoinPriceProperty = new SimpleStringProperty();
        this.numAndurilProperty = new SimpleStringProperty();
        this.numTreeStumpProperty = new SimpleStringProperty();

        this.currCycleNumProperty = new SimpleStringProperty();
        this.cyclesTillShopProperty = new SimpleStringProperty();

        this.gamemodeProperty = new SimpleStringProperty();
        this.cycleOrCyclesProperty = new SimpleStringProperty();

        this.numSword = 0;
        this.numStake = 0;
        this.numStaff = 0;
        this.numBodyArmour = 0;
        this.numHelmet = 0;
        this.numShield = 0;
        this.numHealthPotion = 0;
        this.numOneRing = 0;
        this.numDoggieCoin = 0;
        this.numAnduril = 0;
        this.numTreeStump = 0;
        this.confusingGamemodeSeed = 0;
    }

    // --------------------------------------------------------------------------
    // General Methods
    // --------------------------------------------------------------------------
    /**
     * Returns the width of the map
     * 
     * @return width the width of the map
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the map
     * 
     * @return height the height of the map
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns the current cycle the character is in (starting from 0)
     * 
     * @return numcycles the current cycle the character is in
     */
    public int getCurrCycle() {
        return this.numCycles;
    }

    /**
     * Returns the list of coordinates of each tile of the path (in order)
     * 
     * @return orderedPath list of coordinates of each tile of the path
     */
    public List<Pair<Integer, Integer>> getPath() {
        return this.orderedPath;
    }

    /**
     * Returns the character's current X coordinate
     * 
     * @return character's current X coordinate
     */
    public int getCharacterX() {
        return this.character.getX();
    }

    /**
     * Returns the character's current Y coordinate
     * 
     * @return character's current Y coordinate
     */
    public int getCharacterY() {
        return this.character.getY();
    }

    /**
     * Returns a list of buildings that have been placed on the map
     * 
     * @return buildingEntities list of buildings on the map
     */
    public List<Building> getBuildingsList() {
        return this.buildingEntities;
    }

    /**
     * Returns a list of enemies that are alive on the map
     * 
     * @return enemies list of enemies that are alive
     */
    public List<Enemy> getEnemiesList() {
        return this.enemies;
    }

    /**
     * Returns a list of new enemies that are to be spawned into the map
     * 
     * @return newEnemies list of enemies to be spawned
     */
    public List<Enemy> getNewEnemiesList() {
        return this.newEnemies;
    }

    /**
     * Returns the price of the doggie coin (subject to change)
     * 
     * @return doggieCoinPrice current price of the doggie coin
     */
    public int getDoggieCoinPrice() {
        return doggieCoinPrice;
    }

    /**
     * Adds a building to the map
     * 
     * @param b the building to be added
     */
    public void addBuilding(Building b) {
        this.buildingEntities.add(b);
        this.locationOfPlacedBuildings.add(new Pair<Integer, Integer>(b.getX(), b.getY()));
        this.addObserver(b);
    }

    /**
     * Removes a building off the map
     * 
     * @param b the building to be removed
     */
    public void removeBuilding(Building b) {
        this.buildingEntities.remove(b);
        this.deadObservers.add(b);
        Pair<Integer, Integer> temp = new Pair<Integer, Integer>(b.getX(), b.getY());
        this.locationOfPlacedBuildings.remove(temp);
        b.destroy();
    }

    /**
     * Spawns an enemy into the game
     * 
     * @param e enemy to be spawned
     */
    public void setEnemy(Enemy e) {
        this.enemies.add(e);
    }

    /**
     * Removes buildings that have already been removed from the observers list
     */
    public void removeDeadObservers() {
        for (WorldStateObserver wso : this.deadObservers) {
            this.observers.remove(wso);
        }
        this.deadObservers.clear();
    }

    /**
     * Returns character's health points
     * 
     * @return character's current health points
     */
    public int getCharacterHealth() {
        return this.character.getHealth();
    }

    /**
     * Returns character's experience points
     * 
     * @return character's current experience points
     */
    public int getCharacterXp() {
        return this.character.getExperience();
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity
     * out of the file
     * 
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * Sets the goal the player must complete to win the game
     * 
     * @param goals the goal the player must complete
     */
    public void setGoals(ComplexGoalComponent goals) {
        this.goal = goals;
    }

    public List<String> getAvailableRareItems() {
        return this.rareItemNames;
    }

    /**
     * Adds the rare item type that will have a chance of dropping from a slain
     * enemy
     * 
     * @param rareItemType name of the rare item type
     */
    public void addAavailableRareItems(String rareItemType) {
        if (!this.rareItemNames.contains(rareItemType))
            this.rareItemNames.add(rareItemType);
    }

    /**
     * Returns the number of one rings in the inventory
     * 
     * @return number of one rings in the inventory
     */
    public int getNumOneRing() {
        return this.numOneRing;
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the
     * world)
     * 
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        this.nonSpecifiedEntities.add(entity);
    }

    /**
     * Allows external classes to queue new enemies to be spawned on the next tick
     * 
     * @param newEnemy
     */
    public void addNewEnemy(Enemy newEnemy) {
        this.newEnemies.add(newEnemy);
    }

    /**
     * Returns the (x,y) coordinates of the starting point of the character
     * 
     * @return startingPoint (x,y) coordinates of the starting point of the
     *         character
     */
    public Pair<Integer, Integer> getStartingPoint() {
        return this.startingPoint;
    }

    // *-------------------------------------------------------------------------
    // * Spawn
    // *-------------------------------------------------------------------------
    /**
     * spawns enemies if the conditions warrant it, adds to world
     * 
     * @return list of the enemies to be displayed on screen
     */
    public List<Enemy> possiblySpawnEnemies() {
        // Spawn slugs
        Pair<Integer, Integer> slugPos = possiblyGetSlugEnemySpawnPosition();
        List<Enemy> spawningEnemies = new ArrayList<>();
        if (slugPos != null) {
            int indexInPath = this.orderedPath.indexOf(slugPos);
            SlugEnemy slug = new SlugEnemy(new PathPosition(indexInPath, this.orderedPath));
            this.enemies.add(slug);
            spawningEnemies.add(slug);
        }

        // Adding enemies spawned by world state observers
        for (Enemy e : this.newEnemies) {
            this.enemies.add(e);
            spawningEnemies.add(e);
        }

        // All newEnemies added, clearing
        this.newEnemies.clear();

        // Spawn doggie enemy
        if (this.numCycles == spawnDoggieCycle) {
            spawnDoggieCycle += 15;
            Pair<Integer, Integer> doggiePos = getDoggieEnemySpawnPosition();
            int indexInPath2 = this.orderedPath.indexOf(doggiePos);
            DoggieEnemy doggie = new DoggieEnemy(new PathPosition(indexInPath2, this.orderedPath));
            this.enemies.add(doggie);
            spawningEnemies.add(doggie);
        }

        // Spawn elan muske
        if (this.numCycles >= this.spawnElanCycle && this.getCharacterXp() >= 10000) {
            this.spawnElanCycle += 40;
            Pair<Integer, Integer> elanPos = getDoggieEnemySpawnPosition();
            int indexInPath = this.orderedPath.indexOf(elanPos);
            ElanMuskeEnemy elan = new ElanMuskeEnemy(new PathPosition(indexInPath, this.orderedPath));
            this.enemies.add(elan);
            spawningEnemies.add(elan);
        }

        return spawningEnemies;
    }

    /**
     * spawns health potions, if the conditions warrant it, adds to world
     * 
     * @return spawningHealthPotions list of the health potions
     */
    public List<HealthPotion> spawnHealthPotion() {
        List<HealthPotion> spawningHealthPotions = new ArrayList<>();
        if (this.numHealthPotionSpawned < 1) {
            Pair<Integer, Integer> pos = getPathItemSpawnPosition();
            if (pos != null) {
                int indexInPath = this.orderedPath.indexOf(pos);
                HealthPotion hp = new HealthPotion(new PathPosition(indexInPath, this.orderedPath));
                this.pathItems.add(hp);
                spawningHealthPotions.add(hp);
                this.numHealthPotionSpawned++;
            }
        }

        return spawningHealthPotions;
    }

    /**
     * spawns gold, if the conditions warrant it, adds to world
     * 
     * @return spawningGoldPiles list of the gold
     */
    public List<GoldPile> spawnGoldPile() {
        List<GoldPile> spawningGoldPiles = new ArrayList<>();
        if (this.numGoldPileSpawned < 1) {
            Pair<Integer, Integer> pos = getPathItemSpawnPosition();
            if (pos != null) {
                int indexInPath = this.orderedPath.indexOf(pos);
                GoldPile gp = new GoldPile(new PathPosition(indexInPath, this.orderedPath));
                this.pathItems.add(gp);
                spawningGoldPiles.add(gp);
                this.numGoldPileSpawned++;
            }
        }

        return spawningGoldPiles;
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * 
     * @return null if random choice is that wont be spawning an enemy or it isn't
     *         possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetSlugEnemySpawnPosition() {
        // has a chance spawning a basic enemy on a tile the character isn't on or
        // immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); // @TODO: change based on spec... currently low value for dev purposes...
        // @TODO: change based on spec
        if ((choice == 0) && (this.enemies.size() < 2)) {
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = this.orderedPath.indexOf(new Pair<Integer, Integer>(getCharacterX(), getCharacterY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + this.orderedPath.size()) % this.orderedPath.size();
            int endNotAllowed = (indexPosition + 3) % this.orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i = endNotAllowed; i != startNotAllowed; i = (i + 1) % this.orderedPath.size()) {
                orderedPathSpawnCandidates.add(this.orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates
                    .get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }

        return null;
    }

    /**
     * Spawning doggie enemy
     */
    private Pair<Integer, Integer> getDoggieEnemySpawnPosition() {
        // Has a chance spawning a basic enemy on a tile the character isn't on or
        // immediately before or after (currently space required = 2)...
        Random rand = new Random();
        List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
        int indexPosition = this.orderedPath.indexOf(new Pair<Integer, Integer>(getCharacterX(), getCharacterY()));
        // Inclusive start and exclusive end of range of positions not allowed
        int startNotAllowed = (indexPosition - 2 + this.orderedPath.size()) % this.orderedPath.size();
        int endNotAllowed = (indexPosition + 3) % this.orderedPath.size();
        // Note terminating condition has to be != rather than < since wrap around...
        for (int i = endNotAllowed; i != startNotAllowed; i = (i + 1) % this.orderedPath.size()) {
            orderedPathSpawnCandidates.add(this.orderedPath.get(i));
        }

        // Choose random choice
        Pair<Integer, Integer> origin = new Pair<>(0, 0);
        Pair<Integer, Integer> spawnPosition = new Pair<>(0, 0);
        while (spawnPosition.equals(origin)) {
            spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));
        }

        return spawnPosition;
    }

    /**
     * gets an available path tile coordinate for an item to spawn on the path
     * 
     * @return spawnPosition (x, y) coordinates of where the item can spawn on the
     *         map
     */
    private Pair<Integer, Integer> getPathItemSpawnPosition() {
        // has a chance spawning a path item on a tile the character isn't on or
        // immediately before or after (currently space required = 2)...
        Random rand = new Random();
        List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
        int indexPosition = this.orderedPath.indexOf(new Pair<Integer, Integer>(getCharacterX(), getCharacterY()));
        // inclusive start and exclusive end of range of positions not allowed
        int startNotAllowed = (indexPosition - 2 + this.orderedPath.size()) % this.orderedPath.size();
        int endNotAllowed = (indexPosition + 3) % this.orderedPath.size();
        // note terminating condition has to be != rather than < since wrap around...
        for (int i = endNotAllowed; i != startNotAllowed; i = (i + 1) % this.orderedPath.size()) {
            orderedPathSpawnCandidates.add(this.orderedPath.get(i));
        }

        // Choose random choice
        Pair<Integer, Integer> origin = new Pair<>(0, 0);
        Pair<Integer, Integer> spawnPosition = new Pair<>(0, 0);
        while (spawnPosition.equals(origin)) {
            spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));
        }

        return spawnPosition;
    }

    // *-------------------------------------------------------------------------
    // * Items/Inventory
    // *-------------------------------------------------------------------------
    private boolean canPickUpItem(Item item) {
        return Math.pow((getCharacterX() - item.getX()), 2) + Math.pow((getCharacterY() - item.getY()), 2) == 0;
    }

    /**
     * pick up expected items in the world
     * 
     * @return list of items to be picked up
     */
    public List<Item> attemptToPickUpItems() {
        List<Item> pickedUpItems = new ArrayList<>();

        for (Item pathItem : this.pathItems) {
            if (canPickUpItem(pathItem)) {
                if (pathItem instanceof GoldPile) {
                    AudioClip goldSound = new AudioClip("file:src/sounds/sellsound.wav");
                    goldSound.play();
                    this.numGoldPileSpawned--;
                } else
                    this.numHealthPotionSpawned--;
                pickedUpItems.add(pathItem);
                pathItem.destroy();
            }
        }

        for (Item pathItem : pickedUpItems) {
            this.pathItems.remove(pathItem);
        }

        return pickedUpItems;
    }

    /**
     * Spawn a health potion into inventory (after picking up from path)
     * 
     * @param itemToAdd
     * @return a item to be spawned in the controller as a JavaFX node
     */
    public Item addItem(Item itemToAdd) {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if ((firstAvailableSlot == null) && !(itemToAdd instanceof GoldPile)) {
            // Eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            giveRandomRewards("onlyGoldXP");
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        // Insert the new item, as we know we have at least made a slot available...
        if (itemToAdd instanceof HealthPotion) {
            HealthPotion healthPotion = new HealthPotion(firstAvailableSlot);
            this.unequippedInventoryItems.add(healthPotion);
            increaseUnequippedInventoryItemCount(healthPotion);
            updateItemProperty(healthPotion);
            return healthPotion;
        } else {
            character.giveGold(100);
        }

        return itemToAdd;
    }

    /**
     * spawn a item in the world and return the item entity
     * 
     * @param itemType
     * @return a item to be spawned in the controller as a JavaFX node
     */
    public Item loadItem(String itemType) {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            // giving some cash/experience rewards for the discarding of the oldest item
            giveRandomRewards("onlyGoldXP");
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        Item item = null;
        switch (itemType) {
            case "Helmet":
                item = new Helmet(firstAvailableSlot);
                break;
            case "BodyArmour":
                item = new BodyArmour(firstAvailableSlot);
                break;
            case "Shield":
                item = new Shield(firstAvailableSlot);
                break;
            case "Staff":
                item = new Staff(firstAvailableSlot);
                break;
            case "Stake":
                item = new Stake(firstAvailableSlot);
                break;
            case "Sword":
                item = new Sword(firstAvailableSlot);
                break;
            case "HealthPotion":
                item = new HealthPotion(firstAvailableSlot);
                break;
            case "OneRing":
                item = new OneRing(firstAvailableSlot);
                if (gamemode.equals("Confusing")) {
                    item = processConfusingItem(item);
                }
                break;
            case "DoggieCoin":
                item = new DoggieCoin(firstAvailableSlot);
                break;
            case "Anduril":
                item = new Anduril(firstAvailableSlot);
                if (gamemode.equals("Confusing")) {
                    item = processConfusingItem(item);
                }
                break;
            case "TreeStump":
                item = new TreeStump(firstAvailableSlot);
                if (gamemode.equals("Confusing")) {
                    item = processConfusingItem(item);
                }
                break;
            default:
                break;
        }

        if (item != null) {
            addToUnequippedInventory(item);
            increaseUnequippedInventoryItemCount(item);
            updateItemProperty(item);
        }

        return item;
    }

    /**
     * remove an item from the unequipped inventory
     * 
     * @param item item to be removed
     */
    public void removeUnequippedInventoryItem(Entity item) {
        Item temp = (Item) item;
        decreaseUnequippedInventoryItemCount(temp);
        updateItemProperty(temp);
        item.destroy();
        this.unequippedInventoryItems.remove(item);
    }

    /**
     * Takes weapon from the inventory and equips it as the character's weapon any
     * currently equipped weapon is placed back into the inventory
     * 
     * @param x
     * @param y
     * @return weapon replaced by equip
     */
    public WeaponStrategy equipWeaponByCoordinates(int x, int y) {
        WeaponStrategy oldWeapon = character.getWeapon();
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        this.unequippedInventoryItems.remove(item);
        decreaseUnequippedInventoryItemCount((Item) item);
        updateItemProperty((Item) item);
        character.equipItem((WeaponStrategy) item);

        if (oldWeapon instanceof Melee) {
            // Melee shouldn't be placed in the inventory
            return null;
        }

        return oldWeapon;
    }

    /**
     * Takes armour from inventory and equips it to the character, places currently
     * equipped armour back into the inventory
     * 
     * @param x
     * @param y
     * @return Helmet replaced by equip
     */
    public ArmourStrategy equipArmourByCoordinates(int x, int y) {
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        ArmourStrategy oldItem = null;

        if (item instanceof HelmetStrategy) {
            oldItem = character.getHelmet();
            character.equipItem((HelmetStrategy) item);
        } else if (item instanceof ShieldStrategy) {
            oldItem = character.getShield();
            character.equipItem((ShieldStrategy) item);
        } else if (item instanceof BodyArmourStrategy) {
            oldItem = character.getBodyArmour();
            character.equipItem((BodyArmourStrategy) item);
        }

        this.unequippedInventoryItems.remove(item);
        decreaseUnequippedInventoryItemCount((Item) item);
        updateItemProperty((Item) item);

        if (oldItem instanceof Melee) {
            // Melee shouldn't be placed in the inventory
            return null;
        }

        return oldItem;
    }

    /**
     * remove item at a particular index in the unequipped inventory items list
     * (this is ordered based on age in the starter code)
     * 
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index) {
        Item item = this.unequippedInventoryItems.get(index);
        decreaseUnequippedInventoryItemCount(item);
        updateItemProperty(this.unequippedInventoryItems.get(0));
        item.destroy();
        this.unequippedInventoryItems.remove(index);
    }

    /**
     * return an unequipped inventory item by x and y coordinates assumes that no 2
     * unequipped inventory items share x and y coordinates
     * 
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    public Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y) {
        for (Entity e : this.unequippedInventoryItems) {
            if ((e.getX() == x) && (e.getY() == y)) {
                return e;
            }
        }

        return null;
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the
     * unequipped inventory
     * 
     * @return x,y coordinate pair
     */
    public Pair<Integer, Integer> getFirstAvailableSlotForItem() {
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available
        // slot defined by looking row by row
        for (int y = 0; y < unequippedInventoryHeight; y++) {
            for (int x = 0; x < unequippedInventoryWidth; x++) {
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null) {
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }

        return null;
    }

    /**
     * Drinks health potions
     */
    public void drinkHealthPotion() {
        if (character.isFullHealth())
            return;

        boolean potionFound = false;
        for (Item item : this.unequippedInventoryItems) {
            if (item instanceof HealthPotion) {
                removeUnequippedInventoryItem(item);
                potionFound = true;
                break;
            }
        }

        if (potionFound) {
            character.restoreHealthPoints();
            AudioClip drinkHealthPotionSound = new AudioClip("file:src/sounds/healthpotion.wav");
            drinkHealthPotionSound.play();
        }

        healthProperty();
    }

    /**
     * Activates one ring
     * 
     * @return activatedOneRing
     */
    public Item activateOneRing() {
        Item activatedOneRing = null;
        if ((getCharacterHealth() > 0) || (getNumOneRing() == 0)) {
            return activatedOneRing;
        }

        boolean oneRingAvailable = false;
        for (Item item : this.unequippedInventoryItems) {
            if (item instanceof RareItem) {
                RareItem rareItem = (RareItem) item;
                if (rareItem instanceof OneRing || rareItem.getConfusingItem() instanceof OneRing) {
                    activatedOneRing = (Item) rareItem;
                    removeUnequippedInventoryItem(activatedOneRing);
                    oneRingAvailable = true;
                    break;
                }
            }
        }

        if (oneRingAvailable) {
            character.restoreHealthPoints();
        }

        healthProperty();

        return activatedOneRing;
    }

    /**
     * Get list of unequipped items in inventory
     * 
     * @return this.unequippedInventoryItems list of unequipped items in inventory
     */
    public List<Item> getUnequippedItems() {
        return this.unequippedInventoryItems;
    }

    /**
     * Returns the amount of gold the character has
     * 
     * @return amount of gold the character has
     */
    public int getGold() {
        return character.getGold();
    }

    /**
     * Deducting gold from the character's possession
     * 
     * @param num the amount of gold to be deducted
     */
    public void deductGold(int num) {
        character.removeGold(num);
    }

    /**
     * Give more gold to the character
     * 
     * @param num the amount of gold to be added
     */
    public void giveGold(int num) {

        character.giveGold(num);
    }

    /**
     * Add a doggie coin into the character's inventory
     * 
     * @return doggieCoin The DoggieCoin object to be added to the inventory
     */
    public StaticEntity giveDoggieCoin() {
        StaticEntity doggieCoin = loadItem("DoggieCoin");
        return doggieCoin;
    }

    /**
     * Add the item to list of unequippedInventoryItems
     * 
     * @param item item to be added to the unequippedInventoryItems list
     */
    public void addToUnequippedInventory(Item item) {
        unequippedInventoryItems.add(item);
    }

    /**
     * Increase the individual item's count by 1
     * 
     * @param item item whose counter is to be increased
     */
    public void increaseUnequippedInventoryItemCount(Item item) {
        String itemType = item.getClass().getSimpleName();
        switch (itemType) {
            case "Sword":
                numSword++;
                break;
            case "Stake":
                numStake++;
                break;
            case "Staff":
                numStaff++;
                break;
            case "BodyArmour":
                numBodyArmour++;
                break;
            case "Helmet":
                numHelmet++;
                break;
            case "Shield":
                numShield++;
                break;
            case "HealthPotion":
                numHealthPotion++;
                break;
            case "OneRing":
                numOneRing++;
                break;
            case "DoggieCoin":
                numDoggieCoin++;
                break;
            case "Anduril":
                numAnduril++;
            case "TreeStump":
                numTreeStump++;
                break;
            default:
                break;
        }
    }

    /**
     * Decrease the individual item's count by 1
     * 
     * @param item item whose counter is to be decreased
     */
    public void decreaseUnequippedInventoryItemCount(Item item) {
        String itemType = item.getClass().getSimpleName();
        switch (itemType) {
            case "Sword":
                numSword--;
                break;
            case "Stake":
                numStake--;
                break;
            case "Staff":
                numStaff--;
                break;
            case "BodyArmour":
                numBodyArmour--;
                break;
            case "Helmet":
                numHelmet--;
                break;
            case "Shield":
                numShield--;
                break;
            case "HealthPotion":
                numHealthPotion--;
                break;
            case "OneRing":
                numOneRing--;
                break;
            case "DoggieCoin":
                numDoggieCoin--;
                break;
            case "Anduril":
                numAnduril--;
            case "TreeStump":
                numTreeStump--;
                break;
            default:
                break;
        }
    }

    /**
     * Update the item's string property
     * 
     * @param item item whose string property is to be updated
     */
    public void updateItemProperty(Item item) {
        String itemType = item.getClass().getSimpleName();
        switch (itemType) {
            case "Sword":
                this.numSwordProperty.set(String.valueOf(numSword));
                break;
            case "Stake":
                this.numStakeProperty.set(String.valueOf(numStake));
                break;
            case "Staff":
                this.numStaffProperty.set(String.valueOf(numStaff));
                break;
            case "BodyArmour":
                this.numBodyArmourProperty.set(String.valueOf(numBodyArmour));
                break;
            case "Helmet":
                this.numHelmetProperty.set(String.valueOf(numHelmet));
                break;
            case "Shield":
                this.numShieldProperty.set(String.valueOf(numShield));
                break;
            case "HealthPotion":
                this.numHealthPotionProperty.set(String.valueOf(numHealthPotion));
                break;
            case "OneRing":
                this.numOneRingProperty.set(String.valueOf(numOneRing));
                break;
            case "DoggieCoin":
                this.numDoggieCoinProperty.set(String.valueOf(numDoggieCoin));
                break;
            case "Anduril":
                this.numAndurilProperty.set(String.valueOf(numAnduril));
            case "TreeStump":
                this.numTreeStumpProperty.set(String.valueOf(numTreeStump));
                break;
            default:
                break;
        }
    }

    // *-------------------------------------------------------------------------
    // * Battles
    // *-------------------------------------------------------------------------
    /**
     * kill an enemy
     * 
     * @param enemy enemy to be killed
     */
    public void killEnemy(Enemy enemy) {
        enemy.destroy();
        this.enemies.remove(enemy);
    }

    /**
     * Run the expected battles in the world, based on current world state
     * 
     * @return list of enemies which have been killed
     */
    public List<Enemy> runBattles() {
        List<Enemy> defeatedEnemies = new ArrayList<>();
        for (Enemy e : this.enemies) {
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius

            // Currently the character attacks every enemy and vice versa
            if (Math.pow((getCharacterX() - e.getX()), 2) + Math.pow((getCharacterY() - e.getY()), 2) < Math
                    .pow(e.getAttackRadius(), 2) && (e.getTranceCount() == 0)) {

                if ((e instanceof ElanMuskeEnemy) && (new Random().nextInt(100) > 20) && (!e.getInBattle())) {
                    // Elan jumping implementation
                    e.moveUpPath();
                    e.moveUpPath();
                    e.moveUpPath();
                    e.moveUpPath();
                } else {
                    // fight...
                    character.addBattle(e);
                    boolean doubleDamage = false;
                    for (Building b : this.buildingEntities) {
                        if (b instanceof CampfireBuilding && inBuildingRadius(e, b)) {
                            doubleDamage = true;
                            break;
                        }
                    }
                    character.launchAttack(e, doubleDamage);
                    boolean specialAttack = e.launchAttack(character);

                    if (specialAttack && (e instanceof ZombieEnemy)) {
                        this.addNewEnemy(new ZombieEnemy(e.getPathPosition()));
                    }

                    attackEnemyInTowerRadiusDuringBattle(e);

                    if (e.getHealth() == 0) {
                        // Remove enemy
                        AudioClip enemyDeathSound = new AudioClip("file:src/sounds/enemydeath.wav");
                        enemyDeathSound.play();
                        defeatedEnemies.add(e);
                        character.removeEnemyFromBattle(e);
                    }
                }

                // @TODO: handle character death

            } else if (Math.pow((getCharacterX() - e.getX()), 2) + Math.pow((getCharacterY() - e.getY()), 2) < Math
                    .pow(e.getSupportRadius(), 2) && character.getInBattle() == true) {
                // Support radius logic
                if (e.getPathIndex() < character.getPathIndex() || (e.getPathIndex() - character.getPathIndex()) > 5) {
                    e.moveUpPath();
                } else {
                    e.moveDownPath();
                }
            }
        }

        for (Enemy e : defeatedEnemies) {
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from
            // the enemies list
            // if we killEnemy in prior loop, we get
            // java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            if (e instanceof ElanMuskeEnemy) {
                ElanMuskeEnemy elan = (ElanMuskeEnemy) e;
                elan.stopElanSong();
            } else if (e instanceof DoggieEnemy) {
                DoggieEnemy dog = (DoggieEnemy) e;
                dog.stopDoggieSong();
            }
            killEnemy(e);
        }

        return defeatedEnemies;
    }

    /**
     * Checks if the elan boss is present on the map
     * 
     * @return true if elan is present, else false
     */
    public boolean elanCheck() {
        for (Enemy e : this.enemies) {
            if (e instanceof ElanMuskeEnemy)
                return true;
        }
        return false;
    }

    // *-------------------------------------------------------------------------
    // * Building Cards
    // *-------------------------------------------------------------------------
    /**
     * checks if card pile if full i.e. has attained it max width, if so, then
     * removes card the oldest card of cards (as per position in gridpane of
     * unplayed cards)
     * 
     * @return boolean
     */
    public boolean cardEntityIsFull() {
        if (this.cardEntities.size() > this.getWidth()) {
            Card c = this.cardEntities.get(0);
            int x = c.getX();
            c.destroy();
            this.cardEntities.remove(0);
            shiftCardsDownFromXCoordinate(x);
            return true;
        }

        return false;
    }

    /**
     * spawn a card in the world and return the card entity
     * 
     * @param cardType
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public Card loadCard(String cardType) {
        // braedon said something about using ItemType instead of string (this is a good
        // design, so yay)
        // simple integer property design is ugly, maybe improve it wait pair ??
        Pair<Integer, Integer> position = new Pair<Integer, Integer>(this.cardEntities.size(), 0);
        Card card = BuildingCardFactory.getCard(cardType, position);

        if (card != null)
            this.cardEntities.add(card);

        return card;
    }

    /**
     * Remove a card by its x, y coordinates
     * 
     * @param cardNodeX     x index from 0 to width-1 of card to be removed
     * @param cardNodeY     y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     * @return newBuilding
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c : this.cardEntities) {
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) {
                card = c;
                break;
            }
        }

        Pair<Integer, Integer> newLocation = new Pair<Integer, Integer>(buildingNodeX, buildingNodeY);
        if (!canPlaceCard(newLocation, card))
            return null;

        // Taking advantage of factory pattern
        Building newBuilding = BuildingFactory.getBuilding(card, newLocation);

        if (newBuilding != null) {
            this.addObserver(newBuilding);
            this.buildingEntities.add(newBuilding);
            this.locationOfPlacedBuildings.add(newLocation);

            // Destroy the card
            card.destroy();
            this.cardEntities.remove(card);
            shiftCardsDownFromXCoordinate(cardNodeX);
        }

        return newBuilding;
    }

    /**
     * shift card coordinates down starting from x coordinate
     * 
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x) {
        for (Card c : this.cardEntities) {
            if (c.getX() >= x) {
                c.x().set(c.getX() - 1);
            }
        }
    }

    /**
     * Helper Function to check if location is adjacent to path
     */
    private boolean adjacentToPath(Pair<Integer, Integer> location) {
        for (int i = location.getValue0() - 1; i <= location.getValue0() + 1; i++) {
            for (int j = location.getValue1() - 1; j <= location.getValue1() + 1; j++) {
                if (i != location.getValue0() && j != location.getValue1()
                        && this.orderedPath.contains(new Pair<Integer, Integer>(i, j)))
                    return true;
            }
        }

        return false;
    }

    // *-------------------------------------------------------------------------
    // * Movement
    // *-------------------------------------------------------------------------
    /**
     * Run moves which occur with every tick without needing to spawn anything
     * immediately
     */
    public void runTickMoves() {
        character.moveDownPath();
        moveEnemies();
        healthProperty();
        goldProperty();
        xpProperty();
        alliesProperty();
        levelProperty();
        goalsProperty();
        getNumCyclesProperty();
        getCyclesTillShopProperty();
        getGamemodeProperty();
        getCycleOrCyclesProperty();
        getDoggieCoinPriceProperty();

        if (getCharacterX() == this.startingPoint.getValue0() && getCharacterY() == this.startingPoint.getValue1()
                && !character.getInBattle()) {
            updateCharacterCycles();
        } else {
            showShop = false;
        }

        // Healing enemies if Elan present
        if (elanCheck()) {
            for (Enemy e : this.enemies) {
                if (!(e instanceof ElanMuskeEnemy))
                    e.addHealth(2);
            }
        }

        // Notifying world state observers of new tick
        for (WorldStateObserver observer : this.observers) {
            observer.notifyTick(this.character, this);
        }
        removeDeadObservers();

    }

    /**
     * Returns the showShop boolean value
     * 
     * @return showShop showShop boolean value
     */
    public boolean getShowShop() {
        return this.showShop;
    }

    /**
     * Sets the value of showShop boolean to false
     */
    public void setShowShopToFalse() {
        this.showShop = false;
    }

    /**
     * Update number of cycles character has completed in the loop
     */
    private void updateCharacterCycles() {
        this.numCycles++;
        if (this.numCycles == this.numCyclesToOpenShop) {
            this.cycleShopLinear++;
            this.numCyclesToOpenShop += this.cycleShopLinear;
            showShop = true;
        }

        // Notifying world state observers of new cycle
        for (WorldStateObserver observer : this.observers) {
            observer.notifyCycle(this);
        }
        // showShop = false;
    }

    /**
     * Move all enemies
     */
    private void moveEnemies() {
        for (Enemy e : this.enemies) {
            if ((e instanceof VampireEnemy) && (!e.getInBattle())) {
                determineNextVampireMoveAwayFromCampfire(e);
                continue;
            }
            e.move();
        }
    }

    /**
     * Checks if a building card can can be placed on the given location
     * 
     * @param newlocation where the card is to be placed
     * @param card        building card to be placed
     * @return true if card can be placed on newLocation
     */
    public boolean canPlaceCard(Pair<Integer, Integer> newLocation, Card card) {
        if (this.locationOfPlacedBuildings.contains(newLocation))
            return false;

        if ((card instanceof VillageCard) || (card instanceof BarracksCard) || (card instanceof TrapCard)) {
            if (!this.orderedPath.contains(newLocation) || newLocation
                    .equals(new Pair<Integer, Integer>(this.startingPoint.getValue0(), this.startingPoint.getValue1())))
                return false;
        } else {
            if ((card instanceof CampfireCard) && (this.orderedPath.contains(newLocation)))
                return false;
            else {
                if ((!adjacentToPath(newLocation)) || (this.orderedPath.contains(newLocation)))
                    return false;
            }
        }

        return true;
    }

    // *-------------------------------------------------------------------------
    // * Rewards
    // *-------------------------------------------------------------------------
    /**
     * Gives various rewards on type on mode selected Various modes are withCard,
     * noCard, and OnlyGoldXP
     * 
     * @param rewardSetting to account for various types of rewards
     * @return a buliding card or an item as a reward
     */
    public StaticEntity giveRandomRewards(String rewardSetting) {
        List<String> rewards = new ArrayList<>(List.of("gold", "experience", "equipment", "buildingCard"));
        List<Integer> values = new ArrayList<>(List.of(50, 100, 200, 300, 400, 500));
        List<String> buildingCards = new ArrayList<>(List.of("BarracksCard", "CampfireCard", "TowerCard", "TrapCard",
                "VampireCastleCard", "VillageCard", "ZombiePitCard"));
        List<String> equipments = new ArrayList<>(
                List.of("Helmet", "BodyArmour", "Shield", "Staff", "Stake", "Sword", "HealthPotion"));
        String reward = null;
        StaticEntity rewarded = null;

        Random rand = new Random();

        // small chance to get a oneRing when a battle is won
        // i.e. rewardSetting is "withCard"
        if (rewardSetting.equals("withCard")) {
            int rareItemRandom = rand.nextInt(100);
            if (this.rareItemNames.contains("the_one_ring")) {
                if (rareItemRandom == 50)
                    return loadItem("OneRing");
            }
            if (this.rareItemNames.contains("anduril_flame_of_the_west")) {
                if (rareItemRandom == 25)
                    return loadItem("Anduril");
            }
            if (this.rareItemNames.contains("tree_stump")) {
                if (rareItemRandom == 75)
                    return loadItem("TreeStump");
            }
        }

        switch (rewardSetting) {
            case "withCard":
                reward = rewards.get(rand.nextInt(4));
                break;
            case "noCard":
                reward = rewards.get(rand.nextInt(3));
                break;
            case "onlyGoldXP":
                reward = rewards.get(rand.nextInt(2));
                break;
            default:
                break;
        }

        if (reward != null) {
            switch (reward) {
                case "gold":
                    character.giveGold(values.get(rand.nextInt(6)));
                    break;
                case "experience":
                    character.giveExperiencePoints(values.get(rand.nextInt(2)));
                    break;
                case "buildingCard":
                    rewarded = loadCard(buildingCards.get(rand.nextInt(7)));
                    break;
                case "equipment":
                    rewarded = loadItem(equipments.get(rand.nextInt(7)));
                    break;
                default:
                    break;
            }
        }

        return rewarded;
    }

    // *-------------------------------------------------------------------------
    // * UIS
    // *-------------------------------------------------------------------------
    /**
     * Updates the string property of health points
     * 
     * @return charHealthProperty updated string property of character's health
     */
    public StringProperty healthProperty() {
        this.charHealthProperty.set(String.valueOf(character.getHealth()));
        return this.charHealthProperty;
    }

    /**
     * Updates the string property of number of allies
     * 
     * @return charAlliesProperty updated string property of allies count
     */
    public StringProperty alliesProperty() {
        this.charAlliesProperty.set(String.valueOf(character.getNumAllies()));
        return this.charAlliesProperty;
    }

    public StringProperty levelProperty() {
        this.charLevelProperty.set(String.valueOf(character.getLevel()));
        return this.charLevelProperty;
    }

    /**
     * Updates the string property of goals
     * 
     * @return charGoalsProperty updated string property of goals
     */
    public StringProperty goalsProperty() {
        this.charGoalsProperty.set("Get " + this.goal.getGoalString());
        return this.charGoalsProperty;
    }

    // public Double healthProperty() {
    // charHealthProperty = character.getHealth() / 100.0;
    // return charHealthProperty;
    // }

    /**
     * Updates the string property of gold
     * 
     * @return charGoldProperty updated string property of gold amount
     */
    public StringProperty goldProperty() {
        this.charGoldProperty.set(String.valueOf(character.getGold()));
        return this.charGoldProperty;
    }

    /**
     * Updates the string property of xp
     * 
     * @return charXPProperty updated string property of xp
     */
    public StringProperty xpProperty() {
        this.charXPProperty.set(String.valueOf(character.getExperience()));
        return this.charXPProperty;
    }

    /**
     * Updates the string property of sword count in inventory
     * 
     * @return numSwordProperty updated string property of number of swords in
     *         inventory
     */
    public StringProperty getSwordProperty() {
        return this.numSwordProperty;
    }

    /**
     * Updates the string property of staff count in inventory
     * 
     * @return numStaffProperty updated string property of number of staffs in
     *         inventory
     */
    public StringProperty getStaffProperty() {
        return this.numStaffProperty;
    }

    /**
     * Updates the string property of stake count in inventory
     * 
     * @return numStakeProperty updated string property of number of stakes in
     *         inventory
     */
    public StringProperty getStakeProperty() {
        return this.numStakeProperty;
    }

    /**
     * Updates the string property of body armour count in inventory
     * 
     * @return numBodyArmourProperty updated string property of number of body
     *         armours in inventory
     */
    public StringProperty getBodyArmourProperty() {
        return this.numBodyArmourProperty;
    }

    /**
     * Updates the string property of helmet count in inventory
     * 
     * @return numHelmetProperty updated string property of number of helmets in
     *         inventory
     */
    public StringProperty getHelmetProperty() {
        return this.numHelmetProperty;
    }

    /**
     * Updates the string property of shield count in inventory
     * 
     * @return numShieldProperty updated string property of number of shields in
     *         inventory
     */
    public StringProperty getShieldProperty() {
        return this.numShieldProperty;
    }

    /**
     * Updates the string property of health potion count in inventory
     * 
     * @return numHealthPotionProperty updated string property of number of health
     *         potions in inventory
     */
    public StringProperty getHealthPotionProperty() {
        return this.numHealthPotionProperty;
    }

    /**
     * Updates the string property of doggie coin count in inventory
     * 
     * @return numDoggieCoinProperty updated string property of number of doggie
     *         coins in inventory
     */
    public StringProperty getDoggieCoinProperty() {
        return this.numDoggieCoinProperty;
    }

    /**
     * Updates the string property of one ring count in inventory
     * 
     * @return numOneRingProperty updated string property of number of one rings in
     *         inventory
     */
    public StringProperty getOneRingProperty() {
        return this.numOneRingProperty;
    }

    /**
     * Updates the string property of one ring count in inventory
     * 
     * @return numAndurilProperty updated string property of number of andruils in
     *         inventory
     */
    public StringProperty getAndurilProperty() {
        return this.numAndurilProperty;
    }

    /**
     * Updates the string property of tree stump count in inventory
     * 
     * @return numTreeStumpProperty updated string property of number of tree stumps
     *         in inventory
     */
    public StringProperty getTreeStumpProperty() {
        return this.numTreeStumpProperty;
    }

    /**
     * Updates the string property of cycle number (starting from 1)
     * 
     * @return currCycleNumProperty updated string property of cycle number
     */
    public StringProperty getNumCyclesProperty() {
        this.currCycleNumProperty.set(String.valueOf(this.numCycles + 1));
        return this.currCycleNumProperty;
    }

    /**
     * Updates the string property of number of cycles until the shop appears
     * 
     * @return numBodyArmourProperty updated string property of number of cycles
     *         until the shop appears
     */
    public StringProperty getCyclesTillShopProperty() {
        this.cyclesTillShopProperty.set(String.valueOf(this.numCyclesToOpenShop - this.numCycles));
        return this.cyclesTillShopProperty;
    }

    /**
     * Updates the string property of the gamemode
     * 
     * @return gamemodeProperty updated string property of the gamemode
     */
    public StringProperty getGamemodeProperty() {
        this.gamemodeProperty.set(gamemode);
        return this.gamemodeProperty;
    }

    /**
     * Updates the string property of the word "cycle." after the number of cycles
     * till shop
     * 
     * @return cycleOrCyclesProperty the updated string property of the word
     *         "cycle."
     */
    public StringProperty getCycleOrCyclesProperty() {
        if (this.numCyclesToOpenShop - this.numCycles == 1)
            this.cycleOrCyclesProperty.set("cycle.");
        else
            this.cycleOrCyclesProperty.set("cycles.");
        return this.cycleOrCyclesProperty;
    }

    /**
     * Updates the string property of the doggie coin price
     * 
     * @return doggieCoinPriceProperty the updated string property of the doggie
     *         coin price
     */
    public StringProperty getDoggieCoinPriceProperty() {
        this.doggieCoinPrice = DoggieCoin.getSellPrice();
        if (elanCheck())
            this.doggieCoinPrice += 9000;
        this.doggieCoinPriceProperty.set(String.valueOf(this.doggieCoinPrice));
        return this.doggieCoinPriceProperty;
    }

    // *-------------------------------------------------------------------------
    // * Buildings Helper Functions
    // *-------------------------------------------------------------------------

    /**
     * Returns boolean value of whether the moving entity is in the campfire radius
     * 
     * @param me the moving entity being checked
     * 
     * @return boolean value of whether the moving entity is in the campfire radius
     */
    public boolean inBuildingRadius(MovingEntity me, Building b) {
        return (Math.pow(b.getX() - me.getX(), 2) + Math.pow(b.getY() - me.getY(), 2)) < Math.pow(b.getBuildingRadius(),
                2);
    }

    /**
     * Returns the distance from the nearest campfire from the tile position given
     * 
     * @param x the x coordinate of the tile position
     * @param y the y coordinate of the tile position
     * 
     * @return the distance from the nearest campfire
     */
    private double getShortestRadiusFromCampfire(int x, int y) {
        double shortestRadius = 0;
        boolean first = true;
        for (Building b : this.buildingEntities) {
            if (b instanceof CampfireBuilding) {
                double currentRadius = Math.pow(Math.pow(b.getX() - x, 2) + Math.pow(b.getY() - y, 2), 0.5);
                if (first) {
                    shortestRadius = currentRadius;
                    first = false;
                } else if (shortestRadius > currentRadius)
                    shortestRadius = currentRadius;
            }
        }
        return shortestRadius;
    }

    /**
     * Returns the coordinates of the next path position of the moving entity
     * 
     * @param me the moving entity to be checked
     * 
     * @return the coordinates of the next path position
     */
    private ArrayList<Pair<Integer, Integer>> nextPathTilesCoordinates(MovingEntity me) {
        int pathIndex = me.getPathIndex();
        int beforeIndex = pathIndex - 1;
        int afterIndex = pathIndex + 1;
        if (pathIndex == this.orderedPath.size() - 1)
            afterIndex = 0;
        else if (pathIndex == 0)
            beforeIndex = this.orderedPath.size() - 1;
        ArrayList<Pair<Integer, Integer>> pathList = (ArrayList<Pair<Integer, Integer>>) this.orderedPath;
        Pair<Integer, Integer> before = pathList.get(beforeIndex);
        Pair<Integer, Integer> after = pathList.get(afterIndex);
        ArrayList<Pair<Integer, Integer>> nextPathCoordinates = new ArrayList<Pair<Integer, Integer>>(
                Arrays.asList(before, after));
        return nextPathCoordinates;
    }

    /**
     * Determines which way the vampire should move based on where the nearest
     * campfire is
     * 
     * @param v the vampire to be moved
     */
    private void determineNextVampireMoveAwayFromCampfire(MovingEntity v) {
        ArrayList<Pair<Integer, Integer>> nextPathCoordinates = nextPathTilesCoordinates(v);
        Pair<Integer, Integer> backCoordinates = nextPathCoordinates.get(0);
        Pair<Integer, Integer> frontCoordinates = nextPathCoordinates.get(1);
        double backDistanceFromCampfire = getShortestRadiusFromCampfire(backCoordinates.getValue0(),
                backCoordinates.getValue1());
        double frontDistanceFromCampfire = getShortestRadiusFromCampfire(frontCoordinates.getValue0(),
                frontCoordinates.getValue1());
        if (backDistanceFromCampfire > frontDistanceFromCampfire)
            v.moveUpPath();
        else
            v.moveDownPath();
    }

    /**
     * Function that executes the tower attacking enemies in battle within its
     * radius
     * 
     * @param e the enemy to be attacked
     */
    private void attackEnemyInTowerRadiusDuringBattle(Enemy e) {
        // During a battle within its shooting radius, enemies will be attacked by the
        // tower
        // i.e.
        // if the battle is occuring within the shooting radius of tower,
        // enemies will recieve damage of 10 evry 3 secs
        int counter = 0;
        for (Building b : this.buildingEntities) {
            if (b instanceof TowerBuilding) {
                if (Math.pow((b.getX() - e.getX()), 2) + Math.pow((b.getY() - e.getY()), 2) < Math
                        .pow(b.getBuildingRadius(), 2)) {
                    if (counter == 3) {
                        e.receiveAttack(10);
                        counter = 0;
                    }
                    counter++;
                }
            }
        }
    }

    /**
     * Returns the list of building entities in the map
     * 
     * @return buildingEntities list of building entities in the map
     */
    public List<Building> getBuildingEntities() {
        return this.buildingEntities;
    }

    // *-------------------------------------------------------------------------
    // * Observer
    // *-------------------------------------------------------------------------
    /**
     * Adds an observer to connect this backend to a frontend
     * 
     * @param wc the LoopManiaWorldController to observe this backend
     */
    public void addObserver(WorldStateObserver wc) {
        this.observers.add(wc);
    }

    // *-------------------------------------------------------------------------
    // * Game Mode
    // *-------------------------------------------------------------------------
    /**
     * Sets the gamemode of the game
     * 
     * @param gamemode the gamemode to be set
     */
    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
        if (gamemode.equals("Confusing")) {
            setConfusingGamemodeSeed();
        }
    }

    /**
     * Returns the gamemode the game is currently in
     * 
     * @return gamemode gamemode the game is currently in
     */
    public String getGamemode() {
        return this.gamemode;
    }

    public int setConfusingGamemodeSeed() {
        Random rand = new Random();
        confusingGamemodeSeed = rand.nextInt(100);
        return confusingGamemodeSeed;
    }

    public Item processConfusingItem(Item rareItem) {
        Item confusingItemToAdd;
        if (rareItem.getClass().getSimpleName().equals("OneRing")) {
            if (confusingGamemodeSeed > 50) {
                confusingItemToAdd = new Anduril();
            } else {
                confusingItemToAdd = new TreeStump();
            }
            OneRing tempOneRing = (OneRing) rareItem;
            tempOneRing.setConfusingItem(confusingItemToAdd);
            rareItem = (Item) tempOneRing;
        } else if (rareItem.getClass().getSimpleName().equals("Anduril")) {
            if (confusingGamemodeSeed > 50) {
                confusingItemToAdd = new OneRing();
                increaseUnequippedInventoryItemCount(confusingItemToAdd);
            } else {
                confusingItemToAdd = new TreeStump();
            }
            Anduril tempAnduril = (Anduril) rareItem;
            rareItem = (Item) tempAnduril;
            tempAnduril.setConfusingItem(confusingItemToAdd);
        } else if (rareItem.getClass().getSimpleName().equals("TreeStump")) {
            if (confusingGamemodeSeed > 50) {
                confusingItemToAdd = new OneRing();
                increaseUnequippedInventoryItemCount(confusingItemToAdd);
            } else {
                confusingItemToAdd = new Anduril();
            }
            TreeStump tempTreeStump = (TreeStump) rareItem;
            tempTreeStump.setConfusingItem(confusingItemToAdd);
            rareItem = (Item) tempTreeStump;
        }
        return rareItem;
    }

    // *-------------------------------------------------------------------------
    // * Goals
    // *-------------------------------------------------------------------------
    /**
     * Returns boolean of whether the goal has been achieved
     * 
     * @return boolean of whether the goal has been achieved
     */
    public boolean goalsAchieved() {
        return this.goal.achieved(this);
    }

    public List<Card> getCards() {
        return this.cardEntities;
    }

    public List<Item> getUnequippedInventoryItems() {
        return this.unequippedInventoryItems;
    }
}

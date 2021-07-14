package unsw.loopmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import unsw.loopmania.buildingcards.*;
import unsw.loopmania.buildings.*;
import unsw.loopmania.enemies.*;
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

    private List<Item> unequippedInventoryItems;
    private List<Item> equippedInventoryItems;

    private List<Building> buildingEntities;
    private List<Pair<Integer, Integer>> loactionOfPlacedBuildings;
    private int numCycles;
    private int cycleShopLinear;
    private int cycleShopTotal;
    private boolean showShop;
    private List<Item> pathItems;
    private int numGoldPileSpawned;
    private int numHealthPotionSpawned;
    private int numGold;
    private ArrayList<Enemy> newEnemies;
    private ArrayList<WorldStateObserver> observers;
    private StringProperty charHealth;
    // private Double charHealth;
    private StringProperty charGold;
    private StringProperty charXP;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse
     * them
     */
    private List<Pair<Integer, Integer>> orderedPath;
    private Pair<Integer, Integer> startingPoint;

    // List of building locations by type
    private List<Pair<Integer, Integer>> trapsList;
    private List<Pair<Integer, Integer>> villagesList;
    private List<Pair<Integer, Integer>> campfireList;

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
        unequippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        startingPoint = orderedPath.get(0);
        buildingEntities = new ArrayList<>();
        loactionOfPlacedBuildings = new ArrayList<>();
        numCycles = 0;
        cycleShopLinear = 1;
        cycleShopTotal = 1;
        showShop = false;
        pathItems = new ArrayList<>();
        numGoldPileSpawned = 0;
        numHealthPotionSpawned = 0;
        numGold = 0;
        newEnemies = new ArrayList<>();
        observers = new ArrayList<>();
        charHealth = new SimpleStringProperty();
        // charHealth = 0.0;
        charGold = new SimpleStringProperty();
        charXP = new SimpleStringProperty();
        trapsList = new ArrayList<Pair<Integer, Integer>>();
        villagesList = new ArrayList<Pair<Integer, Integer>>();
        campfireList = new ArrayList<Pair<Integer, Integer>>();
    }

    // --------------------------------------------------------------------------
    // General Methods
    // --------------------------------------------------------------------------
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getCurrCycle() {
        return this.numCycles;
    }

    public List<Pair<Integer, Integer>> getPath() {
        return this.orderedPath;
    }

    public int getCharacterX() {
        return this.character.getX();
    }

    public int getCharacterY() {
        return this.character.getY();
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
     * add a generic entity (without it's own dedicated method for adding to the
     * world)
     * 
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        // TODO = if more specialised types being added from main menu, add more methods
        // like this with specific input types...
        this.nonSpecifiedEntities.add(entity);
    }

    /**
     * Allows external classes to queue new enemies to be spawned on the next tick
     * 
     * @param newEnemy
     */
    public void addNewEnemy(Enemy newEnemy) {
        newEnemies.add(newEnemy);
    }

    public Pair<Integer, Integer> getStartingPoint() {
        return startingPoint;
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
            int indexInPath = orderedPath.indexOf(slugPos);
            SlugEnemy enemy = new SlugEnemy(new PathPosition(indexInPath, orderedPath));
            this.enemies.add(enemy);
            spawningEnemies.add(enemy);
        }

        // Adding enemies spawned by world state observers
        for (Enemy e : newEnemies) {
            this.enemies.add(e);
            spawningEnemies.add(e);
        }

        // All newEnemies added, clearing
        newEnemies.clear();

        return spawningEnemies;
    }

    /**
     * spawns health potions + gold if the conditions warrant it, adds to world
     * 
     * @return list of the health potions + gold
     */
    public List<HealthPotion> spawnHealthPotion() {
        List<HealthPotion> spawningHealthPotions = new ArrayList<>();
        if (numHealthPotionSpawned < 1) {
            Pair<Integer, Integer> pos = getPathItemSpawnPosition();
            if (pos != null) {
                int indexInPath = orderedPath.indexOf(pos);
                HealthPotion hp = new HealthPotion(new PathPosition(indexInPath, orderedPath));
                pathItems.add(hp);
                spawningHealthPotions.add(hp);
                numHealthPotionSpawned++;
            }
        }

        return spawningHealthPotions;
    }

    /**
     * spawns health potions + gold if the conditions warrant it, adds to world
     * 
     * @return list of the health potions + gold
     */
    public List<GoldPile> spawnGoldPile() {
        List<GoldPile> spawningGoldPiles = new ArrayList<>();
        if (numGoldPileSpawned < 1) {
            Pair<Integer, Integer> pos = getPathItemSpawnPosition();
            if (pos != null) {
                int indexInPath = orderedPath.indexOf(pos);
                GoldPile gp = new GoldPile(new PathPosition(indexInPath, orderedPath));
                pathItems.add(gp);
                spawningGoldPiles.add(gp);
                numGoldPileSpawned++;
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
        // TODO = modify this

        // has a chance spawning a basic enemy on a tile the character isn't on or
        // immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); // TODO = change based on spec... currently low value for dev purposes...
        // TODO = change based on spec
        if ((choice == 0) && (this.enemies.size() < 2)) {
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(getCharacterX(), getCharacterY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size()) % orderedPath.size();
            int endNotAllowed = (indexPosition + 3) % orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i = endNotAllowed; i != startNotAllowed; i = (i + 1) % orderedPath.size()) {
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates
                    .get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }

        return null;
    }

    private Pair<Integer, Integer> getPathItemSpawnPosition() {
        // has a chance spawning a path item on a tile the character isn't on or
        // immediately before or after (currently space required = 2)...
        Random rand = new Random();
        List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
        int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(getCharacterX(), getCharacterY()));
        // inclusive start and exclusive end of range of positions not allowed
        int startNotAllowed = (indexPosition - 2 + orderedPath.size()) % orderedPath.size();
        int endNotAllowed = (indexPosition + 3) % orderedPath.size();
        // note terminating condition has to be != rather than < since wrap around...
        for (int i = endNotAllowed; i != startNotAllowed; i = (i + 1) % orderedPath.size()) {
            orderedPathSpawnCandidates.add(orderedPath.get(i));
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
        // List<Item> itemsToRemove
        for (Item pathItem : pathItems) {
            if (canPickUpItem(pathItem)) {
                if (pathItem.getClass().getSimpleName().equals("Gold")) {
                    numGoldPileSpawned--;
                } else {
                    numHealthPotionSpawned--;
                }
                pickedUpItems.add(pathItem);
                pathItem.destroy();
            }
        }
        for (Item pathItem : pickedUpItems) {
            pathItems.remove(pathItem);
        }

        return pickedUpItems;
    }

    /**
     * spawn a item in the world and return the item entity
     * 
     * @param itemToAdd
     * @return a item to be spawned in the controller as a JavaFX node
     */
    public Item addItem(Item itemToAdd) {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null && !itemToAdd.getClass().getSimpleName().equals("GoldPile")) {
            // Eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            giveRandomRewards("onlyGoldXP");
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        // Insert the new item, as we know we have at least made a slot available...
        if (itemToAdd.getClass().getSimpleName().equals("HealthPotion")) {
            HealthPotion healthPotion = new HealthPotion(firstAvailableSlot);
            unequippedInventoryItems.add(healthPotion);
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
            default:
                break;
        }

        if (item != null)
            unequippedInventoryItems.add(item);

        return item;
    }

    public boolean unequippedItemInventoryIsFull() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // Eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            return true;
        }

        return false;
    }

    /**
     * remove an item from the unequipped inventory
     * 
     * @param item item to be removed
     */
    private void removeUnequippedInventoryItem(Entity item) {
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * remove an item by x,y coordinates
     * 
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y) {
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
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
        unequippedInventoryItems.remove(item);
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

        unequippedInventoryItems.remove(item);

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
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

    /**
     * return an unequipped inventory item by x and y coordinates assumes that no 2
     * unequipped inventory items share x and y coordinates
     * 
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    private Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y) {
        for (Entity e : unequippedInventoryItems) {
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
    private Pair<Integer, Integer> getFirstAvailableSlotForItem() {
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

    // Drinks health potion
    public void drinkHealthPotion() {
        if (character.isFullHealth())
            return;

        boolean potionFound = false;
        for (Item item : unequippedInventoryItems) {
            if (item.getClass().getSimpleName().equals("HealthPotion")) {
                removeUnequippedInventoryItem(item);
                potionFound = true;
                break;
            }
        }

        if (potionFound)
            character.restoreHealthPoints();

        healthProperty();
    }

    // Get list of items in
    public List<Item> getUnequippedItems() {
        return unequippedInventoryItems;
    }

    // *-------------------------------------------------------------------------
    // * Battles
    // *-------------------------------------------------------------------------
    /**
     * kill an enemy
     * 
     * @param enemy enemy to be killed
     */
    private void killEnemy(Enemy enemy) {
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
                    .pow(e.getAttackRadius(), 2)) {
                // fight...
                character.addBattle(e);
                character.launchAttack(e, inCampfireRadius(e));
                e.launchAttack(character);
                attackEnemyInTowerRadiusDuringBattle(e);

                if (e.getHealth() == 0) {
                    // Remove enemy
                    defeatedEnemies.add(e);
                    character.removeEnemyFromBattle(e);
                }
                // TODO handle character death

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
            killEnemy(e);
        }

        return defeatedEnemies;
    }

    // *-------------------------------------------------------------------------
    // * Building Cards
    // *-------------------------------------------------------------------------
    /**
     * 
     * @return boolean
     */
    public boolean cardEntityIsFull() {
        // make this conscise with remove card function, same for items as well
        if (this.cardEntities.size() > this.getWidth()) {
            this.removeCard(0);
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
        Card card = null;
        Pair<Integer, Integer> position = new Pair<Integer, Integer>(this.cardEntities.size(), 0);

        switch (cardType) {
            case "BarracksCard":
                card = new BarracksCard(position);
                break;
            case "CampfireCard":
                card = new CampfireCard(position);
                break;
            case "TowerCard":
                card = new TowerCard(position);
                break;
            case "TrapCard":
                card = new TrapCard(position);
                break;
            case "VillageCard":
                card = new VillageCard(position);
                break;
            case "ZombiePitCard":
                card = new ZombiePitCard(position);
                break;
            case "VampireCastleCard":
                card = new VampireCastleCard(position);
                break;
            default:
                break;
        }

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

        String buildingForCard = card.getClass().getSimpleName();
        Building newBuilding = null;
        switch (buildingForCard) {
            case "BarracksCard":
                newBuilding = new BarracksBuilding(new SimpleIntegerProperty(buildingNodeX),
                        new SimpleIntegerProperty(buildingNodeY));
                break;
            case "CampfireCard":
                newBuilding = new CampfireBuilding(new SimpleIntegerProperty(buildingNodeX),
                        new SimpleIntegerProperty(buildingNodeY));
                campfireList.add(new Pair<Integer, Integer>(buildingNodeX, buildingNodeY));
                break;
            case "TowerCard":
                newBuilding = new TowerBuilding(new SimpleIntegerProperty(buildingNodeX),
                        new SimpleIntegerProperty(buildingNodeY));
                break;
            case "TrapCard":
                newBuilding = new TrapBuilding(new SimpleIntegerProperty(buildingNodeX),
                        new SimpleIntegerProperty(buildingNodeY));
                trapsList.add(new Pair<Integer, Integer>(buildingNodeX, buildingNodeY));
                break;
            case "VampireCastleCard":
                newBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(buildingNodeX),
                        new SimpleIntegerProperty(buildingNodeY));
                break;
            case "VillageCard":
                newBuilding = new VillageBuilding(new SimpleIntegerProperty(buildingNodeX),
                        new SimpleIntegerProperty(buildingNodeY));
                villagesList.add(new Pair<Integer, Integer>(buildingNodeX, buildingNodeY));
                break;
            case "ZombiePitCard":
                newBuilding = new ZombiePitBuilding(new SimpleIntegerProperty(buildingNodeX),
                        new SimpleIntegerProperty(buildingNodeY));
                break;
            default:
                break;
        }

        if (newBuilding != null) {
            observers.add(newBuilding);
            buildingEntities.add(newBuilding);
            loactionOfPlacedBuildings.add(new Pair<Integer, Integer>(buildingNodeX, buildingNodeY));

            // Destroy the card
            card.destroy();
            this.cardEntities.remove(card);
            shiftCardsDownFromXCoordinate(cardNodeX);
        }

        return newBuilding;
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed
     * cards)
     * 
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index) {
        Card c = this.cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        this.cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
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
                        && orderedPath.contains(new Pair<Integer, Integer>(i, j)))
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
        restoreHealthIfInVillage();
        if (getCharacterX() == startingPoint.getValue0() && getCharacterY() == startingPoint.getValue1()) {
            updateCharacterCycles();
        }
    }

    /**
     * Update number of cycles character has completed in the loop
     */
    private void updateCharacterCycles() {
        numCycles++;
        if (numCycles == cycleShopTotal) {
            cycleShopLinear++;
            cycleShopTotal += cycleShopLinear;
            showShop = true;
        } else {
            showShop = false;
        }

        // Notifying world state observers of new cycle
        for (WorldStateObserver observer : observers) {
            observer.notify(this);
        }

        // TODO Observer pattern
        // if (showShop) {
        // //! shopMenu.showMenu();
        // }
    }

    /**
     * Move all enemies
     */
    private void moveEnemies() {
        List<Enemy> deadEnemies = new ArrayList<>();
        for (Enemy e : this.enemies) {
            if (e.getClass().getSimpleName().equals("VampireEnemy") && !e.getInBattle()/* && inCampfireRadius(e) */) {
                determineNextVampireMoveAwayFromCampfire(e);
                continue;
            }
            e.move();
            if (checkIfEnemyStepOnTrapAndDies(e))
                deadEnemies.add(e);
        }
        for (Enemy e : deadEnemies) {
            killEnemy(e);
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
        if (loactionOfPlacedBuildings.contains(newLocation))
            return false;

        if ((card instanceof VillageCard) || (card instanceof BarracksCard) || (card instanceof TrapCard)) {
            if (!orderedPath.contains(newLocation) || newLocation
                    .equals(new Pair<Integer, Integer>(startingPoint.getValue0(), startingPoint.getValue1())))
                return false;
        } else {
            if ((card instanceof CampfireCard) && (orderedPath.contains(newLocation)))
                return false;
            else {
                if ((!adjacentToPath(newLocation)) || (orderedPath.contains(newLocation)))
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
            }
        }

        return rewarded;
    }

    // *-------------------------------------------------------------------------
    // * UIS
    // *-------------------------------------------------------------------------
    public StringProperty healthProperty() {
        charHealth.set(String.valueOf(character.getHealth()));
        return charHealth;
    }

    // public Double healthProperty() {
    // charHealth = character.getHealth() / 100.0;
    // return charHealth;
    // }

    public StringProperty goldProperty() {
        charGold.set(String.valueOf(character.getGold()));
        return charGold;
    }

    public StringProperty xpProperty() {
        charXP.set(String.valueOf(character.getExperience()));
        return charXP;
    }

    // *-------------------------------------------------------------------------
    // * Buildings Helper Functions
    // *-------------------------------------------------------------------------
    private void restoreHealthIfInVillage() {
        if (inVillage())
            character.restoreHealthPoints();
    }

    private boolean inVillage() {
        for (Pair<Integer, Integer> village : villagesList) {
            if (village.getValue0().equals(getCharacterX()) && village.getValue1().equals(getCharacterY()))
                return true;
        }
        return false;
    }

    // private void inVillage() {
    // for (Pair<Integer,Integer> village : villagesList) {
    // if (village.getValue0().equals(getCharacterX()) &&
    // village.getValue1().equals(getCharacterY())) {
    // character.restoreHealthPoints();
    // return;
    // }
    // }
    // }

    private boolean checkIfEnemyStepOnTrapAndDies(Enemy enemy) {
        List<Building> usedTraps = new ArrayList<>();
        for (Pair<Integer, Integer> trap : trapsList) {
            if (trap.getValue0().equals(enemy.getX()) && trap.getValue1().equals(enemy.getY())) {
                enemy.receiveAttack(30);
                // Remove Building
                for (Building b : buildingEntities) {
                    if (b.getX() == trap.getValue0() && b.getY() == trap.getValue1()) {
                        usedTraps.add(b);
                        break;
                    }
                }
                break;
            }
        }
        for (Building b : usedTraps) {
            buildingEntities.remove(b);
            b.destroy();
        }
        if (enemy.getHealth() == 0) {
            return true;
        }
        return false;
    }

    public boolean inCampfireRadius(MovingEntity me) {
        for (Pair<Integer, Integer> campfire : campfireList) {
            if (Math.pow(campfire.getValue0() - me.getX(), 2) + Math.pow(campfire.getValue1() - me.getY(), 2) < 16)
                return true;
        }
        return false;
    }

    private double getShortestRadiusFromCampfire(int x, int y) {
        double shortestRadius = 0;
        boolean first = true;
        for (Pair<Integer, Integer> campfire : campfireList) {
            double currentRadius = Math
                    .pow(Math.pow(campfire.getValue0() - x, 2) + Math.pow(campfire.getValue1() - y, 2), 0.5);
            if (first) {
                shortestRadius = currentRadius;
                first = false;
            } else if (shortestRadius > currentRadius)
                shortestRadius = currentRadius;
        }
        return shortestRadius;
    }

    private ArrayList<Pair<Integer, Integer>> nextPathTilesCoordinates(MovingEntity me) {
        int pathIndex = me.getPathIndex();
        int beforeIndex = pathIndex - 1;
        int afterIndex = pathIndex + 1;
        if (pathIndex == orderedPath.size() - 1)
            afterIndex = 0;
        else if (pathIndex == 0)
            beforeIndex = orderedPath.size() - 1;
        ArrayList<Pair<Integer, Integer>> pathList = (ArrayList<Pair<Integer, Integer>>) orderedPath;
        Pair<Integer, Integer> before = pathList.get(beforeIndex);
        Pair<Integer, Integer> after = pathList.get(afterIndex);
        ArrayList<Pair<Integer, Integer>> nextPathCoordinates = new ArrayList<Pair<Integer, Integer>>(
                Arrays.asList(before, after));
        return nextPathCoordinates;
    }

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

    private void attackEnemyInTowerRadiusDuringBattle(Enemy e) {
        // During a battle within its shooting radius, enemies will be attacked by the
        // tower
        // i.e.
        // if the battle is occuring within the shooting radius of tower,
        // enemies will recieve damage of 10 evry 3 secs
        int counter = 0;
        for (Building b : buildingEntities) {
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

}

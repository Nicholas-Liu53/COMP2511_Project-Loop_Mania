package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.buildingcards.*;
import unsw.loopmania.buildings.*;
import unsw.loopmania.enemies.*;
import unsw.loopmania.items.*;
import unsw.loopmania.path.*;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {
    //*-------------------------------------------------------------------------
    //*                             Variables
    //*-------------------------------------------------------------------------
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

    // TODO = add more lists for other entities, for equipped inventory items, etc...

    // TODO = expand the range of enemies
    private List<Enemy> enemies;

    // TODO = expand the range of cards
    private List<Card> cardEntities;

    // TODO = expand the range of items
    private List<Entity> unequippedInventoryItems;
    private List<Item> equippedInventoryItems;

    // TODO = expand the range of buildings
    private List<VampireCastleBuilding> buildingEntities;

    private int numCycles;
    private int cycleShopLinear;
    private int cycleShopTotal;
    private boolean showShop;
    private List<Item> pathItems;
    private int numGoldPileSpawned;
    private int numHealthPotionSpawned;
    private int numGold;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    //--------------------------------------------------------------------------
    //                             Constructor
    //--------------------------------------------------------------------------
    /**
     * create the world (constructor)
     * 
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        nonSpecifiedEntities = new ArrayList<>();
        character = null;
        enemies = new ArrayList<>();
        cardEntities = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        numCycles = 0;
        cycleShopLinear = 1;
        cycleShopTotal = 1;
        showShop = false;
        pathItems = new ArrayList<>();
        numGoldPileSpawned = 0;
        numHealthPotionSpawned = 0;
        numGold = 0;
    }

    //--------------------------------------------------------------------------
    //                             General Methods
    //--------------------------------------------------------------------------
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCurrCycle() {
        return this.numCycles;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        // TODO = if more specialised types being added from main menu, add more methods like this with specific input types...
        nonSpecifiedEntities.add(entity);
    }

    //*-------------------------------------------------------------------------
    //*                                 Spawn
    //*-------------------------------------------------------------------------
    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<Enemy> possiblySpawnEnemies(){
        // TODO = expand this very basic version
        Pair<Integer, Integer> pos = possiblyGetSlugEnemySpawnPosition();
        List<Enemy> spawningEnemies = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            SlugEnemy enemy = new SlugEnemy(new PathPosition(indexInPath, orderedPath));
            enemies.add(enemy);
            spawningEnemies.add(enemy);
        }
        return spawningEnemies;
    }

    /**
     * spawns health potions + gold if the conditions warrant it, adds to world
     * @return list of the health potions + gold 
     */
    public List<HealthPotion> spawnHealthPotion(){
        List<HealthPotion> spawningPathItems = new ArrayList<>();
        if (numHealthPotionSpawned < 1) {
            Pair<Integer, Integer> pos = getPathItemSpawnPosition();
            if (pos != null){
                int indexInPath = orderedPath.indexOf(pos);
                HealthPotion hp = new HealthPotion(new PathPosition(indexInPath, orderedPath));
                pathItems.add(hp);
                spawningPathItems.add(hp);
                numHealthPotionSpawned++;
            }
        } 
        return spawningPathItems;
    }

    /**
     * spawns health potions + gold if the conditions warrant it, adds to world
     * @return list of the health potions + gold 
     */
    public List<GoldPile> spawnGoldPile(){
        List<GoldPile> spawningPathItems = new ArrayList<>();
        if (numGoldPileSpawned < 1) {
            Pair<Integer, Integer> pos = getPathItemSpawnPosition();
            if (pos != null){
                int indexInPath = orderedPath.indexOf(pos);
                GoldPile gp = new GoldPile(new PathPosition(indexInPath, orderedPath));
                pathItems.add(gp);
                spawningPathItems.add(gp);
                numGoldPileSpawned++;
            }
        } 
        return spawningPathItems;
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetSlugEnemySpawnPosition(){
        // TODO = modify this
        
        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); // TODO = change based on spec... currently low value for dev purposes...
        // TODO = change based on spec
        if ((choice == 0) && (enemies.size() < 2)){
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    private Pair<Integer, Integer> getPathItemSpawnPosition() {
        // has a chance spawning a path item on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
        int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
        // inclusive start and exclusive end of range of positions not allowed
        int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
        int endNotAllowed = (indexPosition + 3)%orderedPath.size();
        // note terminating condition has to be != rather than < since wrap around...
        for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
            orderedPathSpawnCandidates.add(orderedPath.get(i));
        }

        // Choose random choice
        Pair<Integer, Integer> origin = new Pair<>(0,0);
        Pair<Integer, Integer> spawnPosition = new Pair<>(0,0);
        while (spawnPosition.equals(origin)) {
            spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));
        }
        return spawnPosition;
    }

    //*-------------------------------------------------------------------------
    //*                             Items/Inventory
    //*-------------------------------------------------------------------------
    /**
     * pick up an item
     * @param item item to be picked up
     */
    // private void attemptToPickUpPathItem(Item item){
    //     if (Math.pow((character.getX()-item.getX()), 2) +  Math.pow((character.getY()-item.getY()), 2) == 0) {
    //         if (item.getItemType().equals("Gold")) {
    //             numGoldPileSpawned--;
    //         } else {
    //             numHealthPotionSpawned--;
    //         }
    //         item.destroy();
    //         pathItems.remove(item);
    //     }
    // }

    private boolean canPickUpItem(Item item) {
        if (Math.pow((character.getX()-item.getX()), 2) +  Math.pow((character.getY()-item.getY()), 2) == 0) {
            return true;
        }
        return false;
    }

    /**
     * pick up expected items in the world
     * @return list of items to be picked up
     */
    public List<Item> attemptToPickUpItems() {
        List<Item> pickedUpItems = new ArrayList<Item>();
        for (Item pathItem: pathItems) {
            if (canPickUpItem(pathItem)) {
                if (pathItem.getItemType().equals("Gold")) {
                    numGoldPileSpawned--;
                } else {
                    numHealthPotionSpawned--;
                }
                pickedUpItems.add(pathItem);
                pathItems.remove(pathItem);
                pathItem.destroy();
            }
        }
        return pickedUpItems;
    }

    /**
     * spawn a item in the world and return the item entity
     * @return a item to be spawned in the controller as a JavaFX node
     */
    public Item addItem(Item itemToAdd){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // Eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
            // TODO = give some cash/experience rewards for the discarding of the oldest item
        }
        
        // Insert the new item, as we know we have at least made a slot available...
        if (itemToAdd.getItemID().equals("HealthPotion")) { 
            HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            unequippedInventoryItems.add(healthPotion);
            return healthPotion;
        } else {
            numGold += 100;
        }
        return itemToAdd;
    }

    /**
     * spawn a sword in the world and return the sword entity
     * @return a sword to be spawned in the controller as a JavaFX node
     */
    public Sword addUnequippedSword(){
        // TODO = expand this - we would like to be able to add multiple types of items, apart from swords
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            // TODO = give some cash/experience rewards for the discarding of the oldest sword
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        
        // now we insert the new sword, as we know we have at least made a slot available...
        Sword sword = new Sword(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(sword);
        return sword;
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    private void removeUnequippedInventoryItem(Entity item){
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }
    
    /**
     * remove item at a particular index in the unequipped inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index){
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    private Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Entity e: unequippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<unequippedInventoryHeight; y++){
            for (int x=0; x<unequippedInventoryWidth; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    //*-------------------------------------------------------------------------
    //*                                 Battles
    //*-------------------------------------------------------------------------
    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    private void killEnemy(Enemy enemy){
        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<Enemy> runBattles() {
        // TODO = modify this - currently the character automatically wins all battles without any damage!
        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        for (Enemy e: enemies){
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius
            // TODO = you should implement different RHS on this inequality, based on influence radii and battle radii
            if (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) < 4){
                // fight...
                defeatedEnemies.add(e);
            }
        }
        for (Enemy e: defeatedEnemies){
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            killEnemy(e);
        }
        return defeatedEnemies;
    }

    //*-------------------------------------------------------------------------
    //*                             Building Cards
    //*-------------------------------------------------------------------------
    /**
     * spawn a card in the world and return the card entity
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public VampireCastleCard loadVampireCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            // TODO = give some cash/experience/item rewards for the discarding of the oldest card
            removeCard(0);
        }
        VampireCastleCard vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0));
        cardEntities.add(vampireCastleCard);
        return vampireCastleCard;
    }

    /**
     * Remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public VampireCastleBuilding convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c: cardEntities){
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)){
                card = c;
                break;
            }
        }
        
        // Spawn building
        VampireCastleBuilding newBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
        buildingEntities.add(newBuilding);

        // Destroy the card
        card.destroy();
        cardEntities.remove(card);
        shiftCardsDownFromXCoordinate(cardNodeX);

        return newBuilding;
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index){
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x){
        for (Card c: cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    //*-------------------------------------------------------------------------
    //*                                 Movement
    //*-------------------------------------------------------------------------
    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public void runTickMoves(){
        character.moveDownPath();
        moveEnemies();
        if (character.getX() == 0 && character.getY() == 0) {
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

        //TODO Observer pattern
        // if (showShop) {
        //     //! shopMenu.showMenu();
        // }
    }

    /**
     * move all enemies
     */
    private void moveEnemies() {
        // TODO = expand to more types of enemy
        for (Enemy e: enemies){
            e.move();
        }
    }
}

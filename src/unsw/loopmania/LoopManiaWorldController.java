package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
// import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import unsw.loopmania.buildingcards.*;
import unsw.loopmania.buildings.*;
import unsw.loopmania.character.Character;
import unsw.loopmania.enemies.*;
import unsw.loopmania.items.*;

import java.util.EnumMap;

import java.io.File;
import java.io.IOException;

// test
/**
 * the draggable types. If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE {
    CARD, ITEM, WEAPON, BODYARMOUR, SHIELD, HELMET,
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application
 * thread:
 * https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 * Note in
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html
 * under heading "Threading", it specifies animation timelines are run in the
 * application thread. This means that the starter code does not need locks
 * (mutexes) for resources shared between the timeline KeyFrame, and all of the
 * event handlers (including between different event handlers). This will make
 * the game easier for you to implement. However, if you add time-consuming
 * processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend: using Task
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by
 * itself or within a Service
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 * Tasks ensure that any changes to public properties, change notifications for
 * errors or cancellation, event handlers, and states occur on the JavaFX
 * Application thread, so is a better alternative to using a basic Java Thread:
 * https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm The Service class
 * is used for executing/reusing tasks. You can run tasks without Service,
 * however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need
 * to implement locks on resources shared with the application thread (i.e.
 * Timeline KeyFrame and drag Event handlers). You can check whether code is
 * running on the JavaFX application thread by running the helper method
 * printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and
 * https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using
 * Platform.runLater
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 * This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController implements WorldStateObserver {
    /**
     * squares gridpane includes path images, enemies, character, empty grass,
     * buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot
     * stretches over the entire game world, so we can detect dragging of
     * cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

    @FXML
    private Label healthNum;

    // @FXML
    // private ProgressBar healthBar;

    @FXML
    private Label goldNum;

    @FXML
    private Label xpNum;

    @FXML
    private Label alliesNum;

    @FXML
    private Label currCycleNum;

    @FXML
    private Label cyclesTillShop;

    @FXML
    private Label gamemodeLabel;

    @FXML
    private Label cycleOrCycles;

    // All image views including tiles, character, enemies, cards... even though
    // cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here
     * and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;

    /**
     * runs the periodic game logic - second-by-second moving of character through
     * maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    // Building Cards
    private Image barracksCardImage;
    private Image campfireCardImage;
    private Image towerCardImage;
    private Image trapCardImage;
    private Image vampireCastleCardImage;
    private Image villageCardImage;
    private Image zombiePitCardImage;

    // Buildings
    private Image barracksImage;
    private Image campfireImage;
    private Image towerImage;
    private Image trapImage;
    private Image villageImage;
    private Image zombiePitImage;
    private Image vampireCastleBuildingImage;
    private Image heroesCastleImage;

    // Items
    private Image bodyArmourImage;
    private Image healthPotionImage;
    private Image helmetImage;
    private Image shieldImage;
    private Image staffImage;
    private Image stakeImage;
    private Image swordImage;
    private Image goldPileImage;
    private Image theOneRingImage;
    private Image treeStumpImage;
    private Image doggieCoinImage;

    // Enemies
    private Image slugEnemyImage;
    private Image vampireEnemyImage;
    private Image zombieEnemyImage;

    // Bosses
    private Image doggieEnemyImage;

    private int spawnCycle;

    // Path Starting position
    private Pair<Integer, Integer> startingPoint;

    /**
     * the image currently being dragged, if there is one, otherwise null. Holding
     * the ImageView being dragged allows us to spawn it again in the drop location
     * if appropriate.
     */
    // TODO = it would be a good idea for you to instead replace this with the
    // building/item which should be dropped
    private ImageView currentlyDraggedImage;

    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;
    //
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged into the boundaries of its appropriate
     * gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged outside of the boundaries of its
     * appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;
    private MenuSwitcher shopMenuSwitcher;
    private MenuSwitcher gameoverSwitcher;
    private MenuSwitcher gamewonSwitcher;
    private MenuSwitcher goalsMenuSwitcher;
    private GoalsMenuController goalsMenuController;
    private ShopMenuController shopMenuController;

    public void setShopController(ShopMenuController shopMenuController) {
        this.shopMenuController = shopMenuController;
    }

    public void setGoalsMenuController(GoalsMenuController goalsMenuController) {
        this.goalsMenuController = goalsMenuController;
    }

    /**
     * @param world           world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be
     *                        loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        world.addObserver(this);
        spawnCycle = 0;
        entityImages = new ArrayList<>(initialEntities);
        // BuildingCards
        barracksCardImage = new Image((new File("src/images/barracks_card.png")).toURI().toString());
        campfireCardImage = new Image((new File("src/images/campfire_card.png")).toURI().toString());
        towerCardImage = new Image((new File("src/images/tower_card.png")).toURI().toString());
        trapCardImage = new Image((new File("src/images/trap_card.png")).toURI().toString());
        vampireCastleCardImage = new Image((new File("src/images/vampire_castle_card.png")).toURI().toString());
        villageCardImage = new Image((new File("src/images/village_card.png")).toURI().toString());
        zombiePitCardImage = new Image((new File("src/images/zombie_pit_card.png")).toURI().toString());

        // Buildings
        barracksImage = new Image((new File("src/images/barracks.png")).toURI().toString());
        campfireImage = new Image((new File("src/images/campfire.png")).toURI().toString());
        towerImage = new Image((new File("src/images/tower.png")).toURI().toString());
        trapImage = new Image((new File("src/images/trap.png")).toURI().toString());
        villageImage = new Image((new File("src/images/village.png")).toURI().toString());
        zombiePitImage = new Image((new File("src/images/zombie_pit.png")).toURI().toString());
        vampireCastleBuildingImage = new Image(
                (new File("src/images/vampire_castle_building_purple_background.png")).toURI().toString());
        heroesCastleImage = new Image((new File("src/images/heros_castle.png")).toURI().toString());

        // Enemies
        slugEnemyImage = new Image((new File("src/images/slug.png")).toURI().toString());
        vampireEnemyImage = new Image((new File("src/images/vampire.png")).toURI().toString());
        zombieEnemyImage = new Image((new File("src/images/zombie.png")).toURI().toString());
        doggieEnemyImage = new Image((new File("src/images/doggie.png")).toURI().toString());

        // Items
        bodyArmourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        healthPotionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        goldPileImage = new Image((new File("src/images/gold_pile.png")).toURI().toString());
        theOneRingImage = new Image((new File("src/images/the_one_ring.png")).toURI().toString());
        doggieCoinImage = new Image((new File("src/images/doggiecoin.png")).toURI().toString());
        treeStumpImage = new Image((new File("src/images/tree_stump.png")).toURI().toString());
        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // Path starting point
        startingPoint = world.getStartingPoint();

        // Initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

    @FXML
    public void initialize() {
        // TODO = load more images/entities during initialization

        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // Add the ground first so it is below all other entities (including all the
        // twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // Load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages) {
            squares.getChildren().add(entity);
        }

        addHeroesCastle();

        // Add the ground underneath the cards
        for (int x = 0; x < world.getWidth(); x++) {
            ImageView groundView = new ImageView(pathTilesImage);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // Add the empty slot images for the unequipped inventory
        for (int x = 0; x < LoopManiaWorld.unequippedInventoryWidth; x++) {
            for (int y = 0; y < LoopManiaWorld.unequippedInventoryHeight; y++) {
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        // Create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);

        // healthBar = new ProgressBar();
        // healthBar.setProgress(0.25);
        world.healthProperty().bindBidirectional(healthNum.textProperty());
        world.goldProperty().bindBidirectional(goldNum.textProperty());
        world.xpProperty().bindBidirectional(xpNum.textProperty());
        world.alliesProperty().bindBidirectional(alliesNum.textProperty());

        world.getNumCyclesProperty().bindBidirectional(currCycleNum.textProperty());
        world.getCyclesTillShopProperty().bindBidirectional(cyclesTillShop.textProperty());
        world.getGamemodeProperty().bindBidirectional(gamemodeLabel.textProperty());
        world.getCycleOrCyclesProperty().bindBidirectional(cycleOrCycles.textProperty());
    }

    /**
     * Create and run the timer
     */
    public void startTimer() {
        System.out.println("starting timer");
        isPaused = false;
        // trigger adding code to process main game logic to queue. JavaFX will target
        // framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            world.runTickMoves();

            // Recieve rewards on defeating enemies
            List<Enemy> defeatedEnemies = world.runBattles();
            for (Enemy e : defeatedEnemies) {
                reactToEnemyDefeat(e);
            }

            // Pick up items on path
            List<Item> itemsOnPath = world.attemptToPickUpItems();
            for (Item i : itemsOnPath) {
                putItemInInventory(i);
            }

            // Spawn enemies randomly
            List<Enemy> newEnemies = world.possiblySpawnEnemies();
            for (Enemy newEnemy : newEnemies) {
                onLoad(newEnemy);
            }

            checkCardPileHasOneSlot();

            // Spawn health potion + gold randomly
            if (world.getCurrCycle() == spawnCycle) {
                List<HealthPotion> newHealthPotions = world.spawnHealthPotion();
                for (HealthPotion newHP : newHealthPotions) {
                    onLoad((Item) newHP, true);
                }

                List<GoldPile> newGoldPiles = world.spawnGoldPile();
                for (GoldPile newGP : newGoldPiles) {
                    onLoad((Item) newGP, true);
                }
                spawnCycle++;
            }

            // Check if character is dead
            if (world.getCharacterHealth() == 0) {
                pause();
                if (world.getNumOneRing() > 0) {
                    Item activatedOneRing = world.activateOneRing();
                    world.decreaseUnequippedInventoryItemCount(activatedOneRing);
                    startTimer();
                } else {
                    gameoverSwitcher.switchMenu();
                }
            }

            // Check if goals have been achieved
            if (world.goalsAchieved() == true) {
                pause();
                gamewonSwitcher.switchMenu();
            }

            printThreadingNotes("HANDLED TIMER");
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Pause the execution of the game loop the human player can still drag and drop
     * items during the game pause
     */
    public void pause() {
        isPaused = true;
        System.out.println("pausing");
        timeline.stop();
    }

    /**
     * Terminate the execution of the game loop
     */
    public void terminate() {
        pause();
    }

    /**
     * Pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * 
     * @param entity backend entity to be paired with view
     * @param view   frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * Load Hero's Castle to the GUI
     */
    private void addHeroesCastle() {
        ImageView heroesCastleView = new ImageView(heroesCastleImage);
        squares.add(heroesCastleView, startingPoint.getValue0(), startingPoint.getValue1());
    }

    /**
     * Run GUI events after an enemy is defeated, such as spawning
     * items/experience/gold/cards
     * 
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(Enemy enemy) {
        // provide different benefits to defeating the enemy based on the type of enemy
        switch (enemy.getClass().getSimpleName()) {
            case "SlugEnemy":
                giveRandomRewardsWithCards(2);
                break;
            case "VampireEnemy":
                giveRandomRewardsWithCards(6);
                break;
            case "ZombieEnemy":
                giveRandomRewardsWithCards(4);
                break;
            case "DoggieEnemy":
                // ! give doggieCoin;
                giveRandomRewardsWithCards(6);
            default:
                break;
        }
    }

    // Check card pile is the correct size
    public void checkCardPileHasOneSlot() {
        if (world.cardEntityIsFull()) {
            StaticEntity compensation = world.giveRandomRewards("noCard");
            if ((compensation != null) && (compensation.getStaticEntityType().equals("Item")))
                loadItem((Item) compensation);
        }
    }

    /**
     * Run GUI events after an enemy is defeated, such as spawning
     * items/experience/gold/cards
     * 
     * @param times i.e. the number of rewards to be given, it is different for
     *              different types of enemies
     */
    public void giveRandomRewardsWithCards(int times) {
        for (int i = 0; i < times; i++) {
            checkCardPileHasOneSlot();
            StaticEntity reward = world.giveRandomRewards("withCard");
            if (reward != null) {
                if (reward.getStaticEntityType().equals("Card")) {
                    loadCard((Card) reward);
                } else if (reward.getStaticEntityType().equals("Item")) {
                    loadItem((Item) reward);
                }
            }
        }
    }

    /**
     * Run GUI events after an character steps on item, putting item in inventory
     * 
     * @param item picked up item for which we should put in the inventory
     */
    private void putItemInInventory(Item item) {
        // Load it into inventory
        loadPathItem(item);
    }

    public boolean isPaused() {
        return isPaused;
    }

    // *-------------------------------------------------------------------------
    // * Loaders
    // *-------------------------------------------------------------------------
    /**
     * Load a card from the world, and pair it with an image in the GUI
     * 
     * @param card
     */
    private void loadCard(Card card) {
        onLoad(card);
    }

    /**
     * Load an item from the world, and pair it with an image in the GUI
     * 
     * @param item
     */
    public void loadItem(Item item) {
        onLoad(item, false);
    }

    /**
     * Load a path item from the world, and pair it with an image in the GUI
     * 
     * @param item
     */
    private void loadPathItem(Item item) {
        // Start by getting first available coordinates
        Item itemToLoad = world.addItem(item);
        if (!itemToLoad.equals(item)) {
            onLoad(itemToLoad, false);
        }
    }

    /**
     * Load a card into the GUI. Particularly, we must connect to the drag detection
     * event handler, and load the image into the cards GridPane.
     * 
     * @param card
     */
    public void onLoad(Card card) {
        ImageView view = null;

        if (card instanceof BarracksCard)
            view = new ImageView(barracksCardImage);
        else if (card instanceof CampfireCard)
            view = new ImageView(campfireCardImage);
        else if (card instanceof TowerCard)
            view = new ImageView(towerCardImage);
        else if (card instanceof TrapCard)
            view = new ImageView(trapCardImage);
        else if (card instanceof VampireCastleCard)
            view = new ImageView(vampireCastleCardImage);
        else if (card instanceof VillageCard)
            view = new ImageView(villageCardImage);
        else if (card instanceof ZombiePitCard)
            view = new ImageView(zombiePitCardImage);

        if (view != null) {
            // FROM
            // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
            // note target setOnDragOver and setOnDragEntered defined in initialize method
            addDragEventHandlers(card, view, DRAGGABLE_TYPE.CARD, cards, squares);

            addEntity(card, view);
            cards.getChildren().add(view);
        }
    }

    /**
     * Load a item into the inventory Particularly, we must connect to the drag
     * detection event handler, and load the image into the unequippedInventory
     * GridPane.
     * 
     * @param item
     */
    private void onLoad(Item item, boolean spawnOnPath) {
        ImageView view = null;

        if (!spawnOnPath) {
            if (item instanceof Helmet) {
                view = new ImageView(helmetImage);
                addDragEventHandlers(item, view, DRAGGABLE_TYPE.HELMET, unequippedInventory, equippedItems);
            } else if (item instanceof BodyArmour) {
                view = new ImageView(bodyArmourImage);
                addDragEventHandlers(item, view, DRAGGABLE_TYPE.BODYARMOUR, unequippedInventory, equippedItems);
            } else if (item instanceof Shield) {
                view = new ImageView(shieldImage);
                addDragEventHandlers(item, view, DRAGGABLE_TYPE.SHIELD, unequippedInventory, equippedItems);
            } else if (item instanceof Staff) {
                view = new ImageView(staffImage);
                addDragEventHandlers(item, view, DRAGGABLE_TYPE.WEAPON, unequippedInventory, equippedItems);
            } else if (item instanceof Stake) {
                view = new ImageView(stakeImage);
                addDragEventHandlers(item, view, DRAGGABLE_TYPE.WEAPON, unequippedInventory, equippedItems);
            } else if (item instanceof Sword) {
                view = new ImageView(swordImage);
                addDragEventHandlers(item, view, DRAGGABLE_TYPE.WEAPON, unequippedInventory, equippedItems);
            } else if (item instanceof HealthPotion) {
                view = new ImageView(healthPotionImage);
                addDragEventHandlers(item, view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedItems);
            } else if (item instanceof OneRing) {
                view = new ImageView(theOneRingImage);
                addDragEventHandlers(item, view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedItems);
            } else if (item instanceof DoggieCoin) {
                view = new ImageView(doggieCoinImage);
                addDragEventHandlers(item, view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedItems);
            } else if (item instanceof TreeStump) {
                view = new ImageView(treeStumpImage);
                addDragEventHandlers(item, view, DRAGGABLE_TYPE.SHIELD, unequippedInventory, equippedItems);
            }

            if (view != null) {
                addEntity(item, view);
                unequippedInventory.getChildren().add(view);
            }
        } else {
            if (item instanceof HealthPotion)
                view = new ImageView(healthPotionImage);
            else if (item instanceof GoldPile)
                view = new ImageView(goldPileImage);

            if (view != null) {
                addEntity(item, view);
                squares.getChildren().add(view);
            }
        }
    }

    /**
     * Load an enemy into the GUI
     * 
     * @param enemy
     */
    private void onLoad(Enemy enemy) {
        ImageView view = null;

        if (enemy instanceof SlugEnemy) {
            view = new ImageView(slugEnemyImage);
        } else if (enemy instanceof VampireEnemy) {
            view = new ImageView(vampireEnemyImage);
        } else if (enemy instanceof ZombieEnemy) {
            view = new ImageView(zombieEnemyImage);
        } else if (enemy instanceof DoggieEnemy) {
            view = new ImageView(doggieEnemyImage);
        }

        if (view != null) {
            addEntity(enemy, view);
            squares.getChildren().add(view);
        }
    }

    /**
     * Load a building into the GUI
     * 
     * @param building
     */
    private void onLoad(Building building) {
        ImageView view = null;

        if (building instanceof BarracksBuilding) {
            view = new ImageView(barracksImage);
        } else if (building instanceof CampfireBuilding) {
            view = new ImageView(campfireImage);
        } else if (building instanceof TowerBuilding) {
            view = new ImageView(towerImage);
        } else if (building instanceof TrapBuilding) {
            view = new ImageView(trapImage);
        } else if (building instanceof VampireCastleBuilding) {
            view = new ImageView(vampireCastleBuildingImage);
        } else if (building instanceof VillageBuilding) {
            view = new ImageView(villageImage);
        } else if (building instanceof ZombiePitBuilding) {
            view = new ImageView(zombiePitImage);
        }

        if (view != null) {
            addEntity(building, view);
            squares.getChildren().add(view);
        }
    }

    // *-------------------------------------------------------------------------
    // * Coordinates
    // *-------------------------------------------------------------------------
    /**
     * Remove the card from the world, and spawn and return a building instead where
     * the card was dropped
     * 
     * @param cardNodeX     the x coordinate of the card which was dragged, from 0
     *                      to width-1
     * @param cardNodeY     the y coordinate of the card which was dragged (in
     *                      starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card,
     *                      where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card,
     *                      where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * Remove an item from the unequipped inventory by its x and y coordinates in
     * the unequipped inventory gridpane
     * 
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    // private void removeItemByCoordinates(int nodeX, int nodeY) {
    // world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    // }

    // *-------------------------------------------------------------------------
    // * Drag EventHandlers
    // *-------------------------------------------------------------------------
    /**
     * add drag event handlers for dropping into gridpanes, dragging over the
     * background, dropping over the background. These are not attached to invidual
     * items such as swords/cards.
     * 
     * @param draggableType  the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to
     *                       (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane,
            GridPane targetGridPane) {
        // TODO = be more selective about where something can be dropped
        // for example, in the specification, villages can only be dropped on path,
        // whilst vampire castles cannot go on the path

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /*
                 * You might want to design the application so dropping at an invalid location
                 * drops at the most recent valid location hovered over, or simply allow the
                 * card/item to return to its slot (the latter is easier, as you won't have to
                 * store the last valid drop location!)
                 */
                if (currentlyDraggedType == draggableType) {
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    // Data dropped
                    // If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != targetGridPane && db.hasImage()) {
                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        // Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());

                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType) {
                            case CARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Building newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                if (newBuilding != null) {
                                    onLoad(newBuilding);
                                } else {
                                    // Return card to original spot if human player tries to drag onto invalid tile
                                    if (currentlyDraggedType == draggableType) {
                                        if (node != anchorPaneRoot && db.hasImage()) {
                                            currentlyDraggedImage.setVisible(true);
                                            draggedEntity.setVisible(false);
                                            draggedEntity.setMouseTransparent(false);
                                            removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                            currentlyDraggedImage = null;
                                            currentlyDraggedType = null;
                                        }
                                    }
                                    // Let the source know whether the image was successfully transferred and used
                                    event.setDropCompleted(true);
                                    event.consume();
                                    return;
                                }
                                break;
                            case ITEM:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                // spawn an item in the new location. The above code for spawning a
                                // building will help, it is very similar
                                // Return item to original spot if human player tries to drag onto invalid tile
                                if (currentlyDraggedType == draggableType) {
                                    if (node != anchorPaneRoot && db.hasImage()) {
                                        currentlyDraggedImage.setVisible(true);
                                        draggedEntity.setVisible(false);
                                        draggedEntity.setMouseTransparent(false);
                                        removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                        currentlyDraggedImage = null;
                                        currentlyDraggedType = null;
                                    }
                                }
                                break;
                            case WEAPON:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                WeaponStrategy oldWeapon = world.equipWeaponByCoordinates(nodeX, nodeY);
                                // Place weapon back in inventory
                                if (oldWeapon instanceof Sword)
                                    loadItem(world.loadItem("Sword"));
                                else if (oldWeapon instanceof Staff)
                                    loadItem(world.loadItem("Staff"));
                                else if (oldWeapon instanceof Stake)
                                    loadItem(world.loadItem("Stake"));
                                // Placing in sword cell
                                targetGridPane.add(image, 0, 1, 1, 1);
                                break;
                            case BODYARMOUR:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                BodyArmourStrategy oldBodyArmour = (BodyArmourStrategy) world
                                        .equipArmourByCoordinates(nodeX, nodeY);
                                // Place armour back in inventory
                                if (oldBodyArmour instanceof BodyArmour)
                                    loadItem(world.loadItem("BodyArmour"));
                                // Placing in body armour cell
                                targetGridPane.add(image, 1, 1, 1, 1);
                                break;
                            case SHIELD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                ShieldStrategy oldShield = (ShieldStrategy) world.equipArmourByCoordinates(nodeX,
                                        nodeY);
                                // Place shield back in inventory
                                if (oldShield instanceof Shield)
                                    loadItem(world.loadItem("Shield"));
                                // Placing in shield cell
                                targetGridPane.add(image, 2, 1, 1, 1);
                                break;
                            case HELMET:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                HelmetStrategy oldHelmet = (HelmetStrategy) world.equipArmourByCoordinates(nodeX,
                                        nodeY);
                                // Place helmet back in inventory
                                if (oldHelmet instanceof Helmet)
                                    loadItem(world.loadItem("Helmet"));
                                // Placing in helmet cell
                                targetGridPane.add(image, 1, 0, 1, 1);
                                break;
                            default:
                                break;
                        }
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        // Remove drag event handlers before setting currently dragged image to null
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a
                // sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read
                // https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // This doesn't fire when we drag over GridPane because in the event handler for
        // dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>() {
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    if (event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null) {
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                // for (int i = 0; i < 100; i++) System.out.println("Yes");
                event.consume();
            }
        });

        // This doesn't fire when we drop over GridPane because in the event handler for
        // dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    // Data dropped
                    // If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != anchorPaneRoot && db.hasImage()) {
                        // Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);

                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                // Let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * add drag event handlers to an ImageView
     * 
     * @param view           the view to attach drag event handlers to
     * @param draggableType  the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be
     *                       dragged
     * @param targetGridPane the relevant gridpane to which the entity would be
     *                       dragged to
     */
    private void addDragEventHandlers(StaticEntity staticEntity, ImageView view, DRAGGABLE_TYPE draggableType,
            GridPane sourceGridPane, GridPane targetGridPane) {
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can
                                              // detect it...
                currentlyDraggedType = draggableType;
                // Drag was detected, start drap-and-drop gesture
                // Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);

                // Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType) {
                    case CARD:
                        Card card = (Card) staticEntity;
                        if (card instanceof VampireCastleCard)
                            draggedEntity.setImage(vampireCastleCardImage);
                        else if (card instanceof BarracksCard)
                            draggedEntity.setImage(barracksCardImage);
                        else if (card instanceof ZombiePitCard)
                            draggedEntity.setImage(zombiePitCardImage);
                        else if (card instanceof CampfireCard)
                            draggedEntity.setImage(campfireCardImage);
                        else if (card instanceof TowerCard)
                            draggedEntity.setImage(towerCardImage);
                        else if (card instanceof TrapCard)
                            draggedEntity.setImage(trapCardImage);
                        else
                            draggedEntity.setImage(villageCardImage);
                        break;
                    case ITEM:
                        Item item = (Item) staticEntity;
                        if (item instanceof BodyArmour)
                            draggedEntity.setImage(bodyArmourImage);
                        else if (item instanceof Helmet)
                            draggedEntity.setImage(helmetImage);
                        else if (item instanceof Shield)
                            draggedEntity.setImage(shieldImage);
                        else if (item instanceof Staff)
                            draggedEntity.setImage(staffImage);
                        else if (item instanceof Stake)
                            draggedEntity.setImage(stakeImage);
                        else if (item instanceof Sword)
                            draggedEntity.setImage(swordImage);
                        else if (item instanceof HealthPotion)
                            draggedEntity.setImage(healthPotionImage);
                        else if (item instanceof OneRing)
                            draggedEntity.setImage(theOneRingImage);
                        else if (item instanceof DoggieCoin)
                            draggedEntity.setImage(doggieCoinImage);
                        else if (item instanceof TreeStump)
                            draggedEntity.setImage(treeStumpImage);
                        break;
                    default:
                        break;
                }

                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED,
                        anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n : targetGridPane.getChildren()) {
                    // events for entering and exiting are attached to squares children because that
                    // impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - if it cannot be
                        // dropped in the location, the location shouldn't be highlighted!
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
                                switch (draggableType) {
                                    case CARD:
                                        // The drag-and-drop gesture entered the target
                                        // show the user that it is an actual gesture target
                                        Card card = (Card) staticEntity;
                                        Node node = event.getPickResult().getIntersectedNode();
                                        Integer cIndex = GridPane.getColumnIndex(node);
                                        Integer rIndex = GridPane.getRowIndex(node);
                                        int x = cIndex == null ? 0 : cIndex;
                                        int y = rIndex == null ? 0 : rIndex;
                                        // System.out.println(x + " " + y);
                                        if (world.canPlaceCard(new Pair<Integer, Integer>(x, y), card)) {
                                            if (event.getGestureSource() != n && event.getDragboard().hasImage()) {
                                                n.setOpacity(0.7);
                                            }
                                        }
                                        break;
                                    case ITEM:
                                        // The drag-and-drop gesture entered the target
                                        // show the user that it is an actual gesture target
                                        if (event.getGestureSource() != n && event.getDragboard().hasImage()) {
                                            n.setOpacity(0.7);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                // if (currentlyDraggedType == draggableType){
                                // n.setOpacity(0.7);
                                // }

                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        // since being more selective about whether highlighting changes, you
                        // could program the game so if the new highlight location is invalid the
                        // highlighting doesn't change, or leave this as-is
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
                                n.setOpacity(1);
                            }

                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }

        });
    }

    /**
     * remove drag event handlers so that we don't process redundant events this is
     * particularly important for slower machines such as over VLAB.
     * 
     * @param draggableType  either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane) {
        // remove event handlers from nodes in children squares, from anchorPaneRoot,
        // and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n : targetGridPane.getChildren()) {
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    // *-------------------------------------------------------------------------
    // * Key Press
    // *-------------------------------------------------------------------------

    /**
     * handle the pressing of keyboard keys. Specifically, we should pause when
     * pressing SPACE
     * 
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE: // pause game
                if (isPaused)
                    startTimer();
                else
                    pause();
                break;
            case P: // Equip health potion
                world.drinkHealthPotion();
                break;
            default:
                break;
        }
    }

    // *-------------------------------------------------------------------------
    // * Menu Switch
    // *-------------------------------------------------------------------------

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher) {
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    public void setShopMenuSwitcher(MenuSwitcher shopMenuSwitcher) {
        this.shopMenuSwitcher = shopMenuSwitcher;
    }

    public void setGoalsMenuSwitcher(MenuSwitcher goalsMenuSwitcher) {
        this.goalsMenuSwitcher = goalsMenuSwitcher;
    }

    public void setGameoverSwitcher(MenuSwitcher gameoverSwitcher) {
        this.gameoverSwitcher = gameoverSwitcher;
    }

    public void setGamewonSwitcher(MenuSwitcher gamewonSwitcher) {
        this.gamewonSwitcher = gamewonSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * 
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        // TODO = possibly set other menu switchers
        pause();
        mainMenuSwitcher.switchMenu();
    }

    @FXML
    void switchToGoalMenu() throws IOException {
        pause();
        goalsMenuSwitcher.switchMenu();
    }

    @FXML
    private void switchToShopMenu() throws IOException {
        pause();
        shopMenuController.initialiseNumColours();
        shopMenuController.resetResponseText();
        shopMenuController.setCountersToZero();
        shopMenuSwitcher.switchMenu();
    }

    private void switchToShopMenu2() throws IOException {
        pause();
        shopMenuController.initialiseNumColours();
        shopMenuController.resetResponseText();
        shopMenuController.setCountersToZero();
        shopMenuSwitcher.switchMenu();
    }

    public LoopManiaWorld getWorld() {
        return this.world;
    }

    public void replaceWorld(LoopManiaWorld newWorld) {
        this.world = newWorld;
    }

    public List<Item> getUnequippedItems() {
        return world.getUnequippedItems();
    }

    // *-------------------------------------------------------------------------
    // * Track Position
    // *-------------------------------------------------------------------------
    /**
     * Set a node in a GridPane to have its position track the position of an entity
     * in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the model
     * will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we
     * need to track positions of spawned entities such as enemy or items which
     * might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So
     * it is vital this is handled in this Controller class
     * 
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        // TODO = tweak this slightly to remove items from the equipped inventory?
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from
        // equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                .onAttach((o, l) -> o.addListener(xListener)).onDetach((o, l) -> {
                    o.removeListener(xListener);
                    entityImages.remove(node);
                    squares.getChildren().remove(node);
                    cards.getChildren().remove(node);
                    equippedItems.getChildren().remove(node);
                    unequippedInventory.getChildren().remove(node);
                }).buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                .onAttach((o, l) -> o.addListener(yListener)).onDetach((o, l) -> {
                    o.removeListener(yListener);
                    entityImages.remove(node);
                    squares.getChildren().remove(node);
                    cards.getChildren().remove(node);
                    equippedItems.getChildren().remove(node);
                    unequippedInventory.getChildren().remove(node);
                }).buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here,
        // position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    // *-------------------------------------------------------------------------
    // * Threading
    // *-------------------------------------------------------------------------
    /**
     * we added this method to help with debugging so you could check your code is
     * running on the application thread. By running everything on the application
     * thread, you will not need to worry about implementing locks, which is outside
     * the scope of the course. Always writing code running on the application
     * thread will make the project easier, as long as you are not running
     * time-consuming tasks. We recommend only running code on the application
     * thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel) {
        System.out.println("\n###########################################");
        System.out.println("current method = " + currentMethodLabel);
        System.out.println("In application thread? = " + Platform.isFxApplicationThread());
        System.out.println("Current system time = " + java.time.LocalDateTime.now().toString().replace('T', ' '));
    }

    public void notifyCycle(LoopManiaWorld world) {
        // Open the show
        if (world.getShowShop() == true) {
            pause();
        } else {
            return;
        }

        try {
            switchToShopMenu2();
        } catch (Exception e) {
            System.out.println("Error: Shop Cannot be Opened");
            return;
        }
    }

    public void notifyTick(Character mainChar, LoopManiaWorld world) {
        return;
    }
}

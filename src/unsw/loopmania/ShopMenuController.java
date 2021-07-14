package unsw.loopmania;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
>>>>>>> master
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
<<<<<<< HEAD
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
=======
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
>>>>>>> master
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
<<<<<<< HEAD
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import java.util.EnumMap;

import java.io.File;
import java.io.IOException;

import unsw.loopmania.items.Item;

/**
 * the draggable types. If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE {
    ITEM
}

public class ShopMenuController {
    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot
     * stretches over the entire game world, so we can ****detect dragging of
     * cards/items***** over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
=======
import javafx.util.Duration;
import unsw.loopmania.items.Item;

/**
 * controller for the shop menu.
 * 
 */
public class ShopMenuController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;

>>>>>>> master
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

<<<<<<< HEAD
    // all image views including tiles, character, enemies, cards... even though
    // cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we ***drag a card/item***, the picture for whatever we're dragging is
     * set here and we actually drag this node
     */
    private DragIcon draggedEntity;

    private LoopManiaWorld world;

    /**
     * Item Images
     */
=======
    // All image views including tiles, character, enemies, cards... even though
    // cards in separate gridpane...
    private List<ImageView> entityImages;

    private LoopManiaWorld world;

    // Item Images
>>>>>>> master
    private Image bodyArmourImage;
    private Image healthPotionImage;
    private Image helmetImage;
    private Image shieldImage;
    private Image staffImage;
    private Image stakeImage;
    private Image swordImage;
    private Image goldPileImage;
    private Image theOneRingImage;

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
<<<<<<< HEAD
    private DRAGGABLE currentlyDraggedType;
=======
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot
     * stretches over the entire game world, so we can detect dragging of
     * cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;
    
    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here
     * and we actually drag this node
     */
    private DragIcon draggedEntity;
>>>>>>> master

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped over its appropriate gridpane
     */
<<<<<<< HEAD
    private EnumMap<DRAGGABLE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
=======
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
>>>>>>> master
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged over the background
     */
<<<<<<< HEAD
    private EnumMap<DRAGGABLE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
=======
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
>>>>>>> master
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped in the background
     */
<<<<<<< HEAD
    private EnumMap<DRAGGABLE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
=======
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
>>>>>>> master
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged into the boundaries of its appropriate
     * gridpane
     */
<<<<<<< HEAD
    private EnumMap<DRAGGABLE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
=======
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
>>>>>>> master
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged outside of the boundaries of its
     * appropriate gridpane
     */
<<<<<<< HEAD
    private EnumMap<DRAGGABLE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the game
     */
    private MenuSwitcher gameSwitcher;
=======
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;
>>>>>>> master

    public ShopMenuController() {
        // this.world = world;
        // entityImages = new ArrayList<>(initialEntities);

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
        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // Initialize them all...
<<<<<<< HEAD
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE, EventHandler<DragEvent>>(DRAGGABLE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE, EventHandler<DragEvent>>(DRAGGABLE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE, EventHandler<DragEvent>>(DRAGGABLE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE, EventHandler<DragEvent>>(DRAGGABLE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE, EventHandler<DragEvent>>(DRAGGABLE.class);
=======
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
>>>>>>> master
    }

    @FXML
    public void initialize() {
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
<<<<<<< HEAD
        // Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // add the empty slot images for the unequipped inventory
=======
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        
        // Add the empty slot images for the unequipped inventory
>>>>>>> master
        for (int x = 0; x < LoopManiaWorld.unequippedInventoryWidth; x++) {
            for (int y = 0; y < LoopManiaWorld.unequippedInventoryHeight; y++) {
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

<<<<<<< HEAD
        // create the draggable icon
=======
        // // Create the draggable icon
>>>>>>> master
        // draggedEntity = new DragIcon();
        // draggedEntity.setVisible(false);
        // draggedEntity.setOpacity(0.7);
        // anchorPaneRoot.getChildren().add(draggedEntity);
    }
<<<<<<< HEAD

    public void setGameSwitcher(MenuSwitcher gameSwitcher) {
=======
    //
    public void setGameSwitcher(MenuSwitcher gameSwitcher){
>>>>>>> master
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
<<<<<<< HEAD
     * 
=======
>>>>>>> master
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }

    public void setWorld(LoopManiaWorld world) {
        this.world = world;
    }

    public LoopManiaWorld getWorld() {
        return this.world;
    }

<<<<<<< HEAD
    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * 
     * @param entity backend entity to be paired with view
     * @param view   frontend imageview to be paired with backend entity
     */
    // private void addEntity(Entity entity, ImageView view) {
    // trackPosition(entity, view);
    // entityImages.add(view);
    // }

=======
>>>>>>> master
    public void addItemsInInventory(List<Item> unequippedInventoryItems) {
        // int i = 0, j = 0;
        for (Item unequippedInventoryItem : unequippedInventoryItems) {
            unequippedInventory.getChildren().add(new ImageView(getImageForItem(unequippedInventoryItem)));
            // i++;
            // if (i % 4 == 0) {
<<<<<<< HEAD
            // i = 0;
            // j++;
=======
            //     i = 0;
            //     j++;
>>>>>>> master
            // }
        }
    }

    // Helper functions
    private Image getImageForItem(Item item) {
<<<<<<< HEAD
        if (item.getClass().getSimpleName().equals("BodyArmour"))
            return bodyArmourImage;
        else if (item.getClass().getSimpleName().equals("HealthPotion"))
            return healthPotionImage;
        else if (item.getClass().getSimpleName().equals("Helmet"))
            return helmetImage;
        else if (item.getClass().getSimpleName().equals("Shield"))
            return shieldImage;
        else if (item.getClass().getSimpleName().equals("Staff"))
            return staffImage;
        else if (item.getClass().getSimpleName().equals("Stake"))
            return stakeImage;
        else if (item.getClass().getSimpleName().equals("Sword"))
            return swordImage;
        // if (item.getClass().getSimpleName().equals("GoldPile")) return goldPileImage;
        else
            return theOneRingImage;
=======
        if (item.getClass().getSimpleName().equals("BodyArmour")) return bodyArmourImage;
        else if (item.getClass().getSimpleName().equals("HealthPotion")) return healthPotionImage;
        else if (item.getClass().getSimpleName().equals("Helmet")) return helmetImage;
        else if (item.getClass().getSimpleName().equals("Shield")) return shieldImage;
        else if (item.getClass().getSimpleName().equals("Staff")) return staffImage;
        else if (item.getClass().getSimpleName().equals("Stake")) return stakeImage;
        else if (item.getClass().getSimpleName().equals("Sword")) return swordImage;
        // if (item.getClass().getSimpleName().equals("GoldPile")) return goldPileImage;
        else return theOneRingImage;
>>>>>>> master
    }
}

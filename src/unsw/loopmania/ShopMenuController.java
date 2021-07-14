package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
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
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

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
    private DRAGGABLE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged into the boundaries of its appropriate
     * gridpane
     */
    private EnumMap<DRAGGABLE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged outside of the boundaries of its
     * appropriate gridpane
     */
    private EnumMap<DRAGGABLE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the game
     */
    private MenuSwitcher gameSwitcher;

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
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE, EventHandler<DragEvent>>(DRAGGABLE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE, EventHandler<DragEvent>>(DRAGGABLE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE, EventHandler<DragEvent>>(DRAGGABLE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE, EventHandler<DragEvent>>(DRAGGABLE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE, EventHandler<DragEvent>>(DRAGGABLE.class);
    }

    @FXML
    public void initialize() {
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        // Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // add the empty slot images for the unequipped inventory
        for (int x = 0; x < LoopManiaWorld.unequippedInventoryWidth; x++) {
            for (int y = 0; y < LoopManiaWorld.unequippedInventoryHeight; y++) {
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        // create the draggable icon
        // draggedEntity = new DragIcon();
        // draggedEntity.setVisible(false);
        // draggedEntity.setOpacity(0.7);
        // anchorPaneRoot.getChildren().add(draggedEntity);
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher) {
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * 
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

    public void addItemsInInventory(List<Item> unequippedInventoryItems) {
        // int i = 0, j = 0;
        for (Item unequippedInventoryItem : unequippedInventoryItems) {
            unequippedInventory.getChildren().add(new ImageView(getImageForItem(unequippedInventoryItem)));
            // i++;
            // if (i % 4 == 0) {
            // i = 0;
            // j++;
            // }
        }
    }

    // Helper functions
    private Image getImageForItem(Item item) {
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
    }
}

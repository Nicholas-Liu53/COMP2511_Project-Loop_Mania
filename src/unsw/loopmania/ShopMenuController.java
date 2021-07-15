package unsw.loopmania;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.javatuples.Pair;
import unsw.loopmania.items.*;

/**
 * controller for the shop menu.
 * 
 */
public class ShopMenuController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;

    // All image views including tiles, character, enemies, cards... even though
    // cards in separate gridpane...
    private List<ImageView> entityImages;

    private LoopManiaWorld world;
    private LoopManiaWorldController worldController;
    
    @FXML
    private Label shopGoldNum;

    // Item Images
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
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot
     * stretches over the entire game world, so we can detect dragging of
     * cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;
    
    public ShopMenuController() {
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
    }

    @FXML
    public void initialize() {
        world.goldProperty().bindBidirectional(shopGoldNum.textProperty());
        // HashMap<String, Integer> itemDict = new HashMap<String, Integer>();
        // for (Item item: world.getUnequippedItems()) {
        //     try {
        //         itemDict.put(item.getClass().getSimpleName(), itemDict.get(item.getClass().getSimpleName()) + 1);
        //     } catch (Exception e) {
        //         itemDict.put(item.getClass().getSimpleName(), 1);
        //     }
        // }
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }

    public void setWorld(LoopManiaWorld world) {
        this.world = world;
    }

    public void setWorldController(LoopManiaWorldController worldController) {
        this.worldController = worldController;
    }

    public LoopManiaWorld getWorld() {
        return this.world;
    }

    
    // General purchase function
    private boolean purchase(String string) {
        Item item = null;
        Pair<Integer, Integer> slotPos = world.getFirstAvailableSlotForItem();
        
        if (slotPos == null) {
            return false;
        } 
        if (string.equals("Sword")) {
            if (!canAfford(Sword.getPurchasePrice())) {
                return false;
            } 
            item = new Sword(new SimpleIntegerProperty(slotPos.getValue0()), new SimpleIntegerProperty(slotPos.getValue1()));
            world.deductGold(Sword.getPurchasePrice());
        } else if (string.equals("Stake")) {
            if (!canAfford(Stake.getPurchasePrice())) {
                return false;
            } 
            item = new Stake(new SimpleIntegerProperty(slotPos.getValue0()), new SimpleIntegerProperty(slotPos.getValue1()));
            world.deductGold(Stake.getPurchasePrice());
        } else if (string.equals("Staff")) {
            if (!canAfford(Staff.getPurchasePrice())) {
                return false;
            } 
            item = new Staff(new SimpleIntegerProperty(slotPos.getValue0()), new SimpleIntegerProperty(slotPos.getValue1()));
            world.deductGold(Staff.getPurchasePrice());
        } else if (string.equals("BodyArmour")) {
            if (!canAfford(BodyArmour.getPurchasePrice())) {
                return false;
            } 
            item = new BodyArmour(new SimpleIntegerProperty(slotPos.getValue0()), new SimpleIntegerProperty(slotPos.getValue1()));
            world.deductGold(BodyArmour.getPurchasePrice());
        } else if (string.equals("Shield")) {
            if (!canAfford(Shield.getPurchasePrice())) {
                return false;
            } 
            item = new Shield(new SimpleIntegerProperty(slotPos.getValue0()), new SimpleIntegerProperty(slotPos.getValue1()));
            world.deductGold(Shield.getPurchasePrice());
        } else if (string.equals("Helmet")) {
            if (!canAfford(Helmet.getPurchasePrice())) {
                return false;
            } 
            item = new Helmet(new SimpleIntegerProperty(slotPos.getValue0()), new SimpleIntegerProperty(slotPos.getValue1()));
            world.deductGold(Helmet.getPurchasePrice());
        } else if (string.equals("HealthPotion")) {
            if (!canAfford(HealthPotion.getPurchasePrice())) {
                return false;
            } 
            item = new HealthPotion(new SimpleIntegerProperty(slotPos.getValue0()), new SimpleIntegerProperty(slotPos.getValue1()));
            world.deductGold(HealthPotion.getPurchasePrice());
        } else {
            return false;
        }
        worldController.loadItem(item);
        return true;
    }
    
    @FXML
    private boolean purchaseSword() {
        return purchase("Sword");
    }

    @FXML
    private boolean purchaseStake() {
        return purchase("Stake");
    }

    @FXML
    private boolean purchaseStaff() {
        return purchase("Staff");
    }
    
    @FXML
    private boolean purchaseBodyArmour() {
        return purchase("BodyArmour");
    }

    @FXML
    private boolean purchaseShield() {
        return purchase("Shield");
    }

    @FXML
    private boolean purchaseHelmet() {
        return purchase("Helmet");
    }

    @FXML
    private boolean purchaseHealthPotion() {
        return purchase("HealthPotion");
    }

    // General Sell function
    private boolean sell(String itemName) {
        /* 
            Pseudocode to sell
            if no item to sell:
                return false
            for item in unequippedInventoryItems:
                if item.getClass().getSimpleName().equals(itemName):
                    delete item from inventory
                    give money back
                    break
                end if
            end for
        */
        return true;
    }

    private boolean canAfford(int purchasePrice) {
        if (world.getGold() < purchasePrice) {
            return false;
        }
        return true;
    }

    // Helper functions
    private Image getImageForItem(Item item) {
        if (item.getClass().getSimpleName().equals("BodyArmour")) return bodyArmourImage;
        else if (item.getClass().getSimpleName().equals("HealthPotion")) return healthPotionImage;
        else if (item.getClass().getSimpleName().equals("Helmet")) return helmetImage;
        else if (item.getClass().getSimpleName().equals("Shield")) return shieldImage;
        else if (item.getClass().getSimpleName().equals("Staff")) return staffImage;
        else if (item.getClass().getSimpleName().equals("Stake")) return stakeImage;
        else if (item.getClass().getSimpleName().equals("Sword")) return swordImage;
        else return theOneRingImage;
    }
}

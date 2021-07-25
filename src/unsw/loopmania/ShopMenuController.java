package unsw.loopmania;

// import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.EnumMap;
// import java.util.HashMap;
// import java.util.List;

import javafx.fxml.FXML;
// import javafx.geometry.Rectangle2D;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
// import javafx.animation.Animation;
// import javafx.animation.KeyFrame;
// import javafx.animation.Timeline;
// import javafx.application.Platform;
// import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
// import javafx.beans.value.ChangeListener;
// import javafx.beans.value.ObservableValue;
// import javafx.event.EventHandler;
// import javafx.geometry.Point2D;
// import javafx.scene.Node;
import javafx.scene.control.Label;
// import javafx.scene.input.ClipboardContent;
// import javafx.scene.input.DragEvent;
// import javafx.scene.input.Dragboard;
// import javafx.scene.input.KeyEvent;
// import javafx.scene.input.MouseEvent;
// import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
// import javafx.util.Duration;
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
    // private List<ImageView> entityImages;

    private LoopManiaWorld world;
    private LoopManiaWorldController worldController;

    // Item list
    private ArrayList<String> itemsList = new ArrayList<String>(
        Arrays.asList(
            "Sword", "Stake", "Staff", "BodyArmour", "Helmet", "Shield", "HealthPotion"
        )
    );
    
    @FXML
    private Label shopGoldNum;

    @FXML
    private Label sellSwordNum;
    @FXML
    private Label sellStaffNum;
    @FXML
    private Label sellStakeNum;
    @FXML
    private Label sellHelmetNum;
    @FXML
    private Label sellBodyArmourNum;
    @FXML
    private Label sellShieldNum;
    @FXML
    private Label sellHealthPotionNum;
    @FXML
    private Label sellDoggieCoinNum;
    @FXML
    private Label doggiePrice;

    @FXML
    private Label shopResponseLabel;


    // Item Images
    // private Image bodyArmourImage;
    // private Image healthPotionImage;
    // private Image helmetImage;
    // private Image shieldImage;
    // private Image staffImage;
    // private Image stakeImage;
    // private Image swordImage;
    // private Image goldPileImage;
    // private Image theOneRingImage;

    private int numHealthPotionsBought = 0;
    private int numArmourBought = 0;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot
     * stretches over the entire game world, so we can detect dragging of
     * cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;
    
    public ShopMenuController() {
        // Items
        // bodyArmourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        // healthPotionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        // helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        // shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        // staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        // stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        // swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        // goldPileImage = new Image((new File("src/images/gold_pile.png")).toURI().toString());
        // theOneRingImage = new Image((new File("src/images/the_one_ring.png")).toURI().toString());
    }

    @FXML
    public void initialize() {
        world.goldProperty().bindBidirectional(shopGoldNum.textProperty());
        world.getSwordProperty().bindBidirectional(sellSwordNum.textProperty());
        world.getStaffProperty().bindBidirectional(sellStaffNum.textProperty());
        world.getStakeProperty().bindBidirectional(sellStakeNum.textProperty());
        world.getBodyArmourProperty().bindBidirectional(sellBodyArmourNum.textProperty());
        world.getHelmetProperty().bindBidirectional(sellHelmetNum.textProperty());
        world.getShieldProperty().bindBidirectional(sellShieldNum.textProperty());
        world.getHealthPotionProperty().bindBidirectional(sellHealthPotionNum.textProperty());
        world.getDoggieCoinProperty().bindBidirectional(sellDoggieCoinNum.textProperty());
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
        world.goldProperty();
        worldController.startTimer();
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
            actionNotSuccessfulText("Inventory is full");
            return false;
        } 
        if (string.equals("Sword")) {
            if (!canAfford(Sword.getPurchasePrice())) {
                actionNotSuccessfulText("Insufficient Gold");
                return false;
            } 
            item = new Sword(new Pair<Integer, Integer>(slotPos.getValue0(), slotPos.getValue1()));
            world.deductGold(Sword.getPurchasePrice());
        } else if (string.equals("Stake")) {
            if (!canAfford(Stake.getPurchasePrice())) {
                actionNotSuccessfulText("Insufficient Gold");
                return false;
            } 
            item = new Stake(new Pair<Integer, Integer>(slotPos.getValue0(), slotPos.getValue1()));
            world.deductGold(Stake.getPurchasePrice());
        } else if (string.equals("Staff")) {
            if (!canAfford(Staff.getPurchasePrice())) {
                actionNotSuccessfulText("Insufficient Gold");
                return false;
            } 
            item = new Staff(new Pair<Integer, Integer>(slotPos.getValue0(), slotPos.getValue1()));
            world.deductGold(Staff.getPurchasePrice());
        } else if (string.equals("BodyArmour")) {
            if (!canAfford(BodyArmour.getPurchasePrice())) {
                actionNotSuccessfulText("Insufficient Gold");
                return false;
            } else if (world.getGamemode().equals("Berserker") && this.numArmourBought > 0) {
                actionNotSuccessfulText("Berserker Mode: Can only buy one armour type");
                return false;
            }
            item = new BodyArmour(new Pair<Integer, Integer>(slotPos.getValue0(), slotPos.getValue1()));
            world.deductGold(BodyArmour.getPurchasePrice());
            numArmourBought++;
        } else if (string.equals("Shield")) {
            if (!canAfford(Shield.getPurchasePrice())) {
                actionNotSuccessfulText("Insufficient Gold");
                return false;
            } else if (world.getGamemode().equals("Berserker") && this.numArmourBought > 0) {
                actionNotSuccessfulText("Berserker Mode: Can only buy one armour type");
                return false;
            }
            item = new Shield(new Pair<Integer, Integer>(slotPos.getValue0(), slotPos.getValue1()));
            world.deductGold(Shield.getPurchasePrice());
            numArmourBought++;
        } else if (string.equals("Helmet")) {
            if (!canAfford(Helmet.getPurchasePrice())) {
                actionNotSuccessfulText("Insufficient Gold");
                return false;
            } else if (world.getGamemode().equals("Berserker") && this.numArmourBought > 0) {
                actionNotSuccessfulText("Berserker Mode: Can only buy one armour type");
                return false;
            }
            item = new Helmet(new Pair<Integer, Integer>(slotPos.getValue0(), slotPos.getValue1()));
            world.deductGold(Helmet.getPurchasePrice());
            numArmourBought++;
        } else if (string.equals("HealthPotion")) {
            if (!canAfford(HealthPotion.getPurchasePrice())) {
                actionNotSuccessfulText("Insufficient Gold");
                return false;
            } else if (world.getGamemode().equals("Survival") && this.numHealthPotionsBought > 0) {
                actionNotSuccessfulText("Survival Mode: Can only buy one Health Potion");
                return false;
            }
            item = new HealthPotion(new Pair<Integer, Integer>(slotPos.getValue0(), slotPos.getValue1()));
            world.deductGold(HealthPotion.getPurchasePrice());
            numHealthPotionsBought++;
        } else {
            return false;
        }
        world.addToUnequippedInventory(item);
        world.increaseUnequippedInventoryItemCount(item);
        world.updateItemProperty(item);
        world.goldProperty();
        worldController.loadItem(item);
        checkIfHitsZero(item);
        actionSuccessfulText(string + " bought");
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
        for (Item item: world.getUnequippedItems()) {
            if (item.getClass().getSimpleName().equals(itemName)) {
                // world.decreaseUnequippedInventoryItemCount(item);
                world.removeUnequippedInventoryItem(item);
                world.updateItemProperty(item);
                switch (item.getClass().getSimpleName()) {
                    case "Sword":
                        world.giveGold(Sword.getSellPrice());
                        break;
                    case "Stake":
                        world.giveGold(Stake.getSellPrice());
                        break;
                    case "Staff":
                        world.giveGold(Staff.getSellPrice());
                        break;
                    case "BodyArmour":
                        world.giveGold(BodyArmour.getSellPrice());
                        break;
                    case "Helmet":
                        world.giveGold(Helmet.getSellPrice());
                        break;
                    case "Shield":
                        world.giveGold(Shield.getSellPrice());
                        break;
                    case "HealthPotion":
                        world.giveGold(HealthPotion.getSellPrice());
                        break;
                    case "DoggieCoin":
                        world.giveGold(DoggieCoin.getSellPrice());
                    default:
                        break;
                }
                world.goldProperty();
                checkIfHitsZero(item);
                actionSuccessfulText(itemName + " Sold");
                return true;
            }
        }
        actionNotSuccessfulText("No " + itemName + "s left to sell");
        return false;
    }

    @FXML
    private boolean sellSword() {
        return sell("Sword");
    }

    @FXML
    private boolean sellStake() {
        return sell("Stake");
    }

    @FXML
    private boolean sellStaff() {
        return sell("Staff");
    }
    
    @FXML
    private boolean sellBodyArmour() {
        return sell("BodyArmour");
    }

    @FXML
    private boolean sellShield() {
        return sell("Shield");
    }

    @FXML
    private boolean sellHelmet() {
        return sell("Helmet");
    }

    @FXML
    private boolean sellDoggieCoin() {
        return sell("DoggieCoin");
    }

    @FXML
    private boolean sellHealthPotion() {
        return sell("HealthPotion");
    }

    private boolean canAfford(int purchasePrice) {
        if (world.getGold() < purchasePrice) {
            return false;
        }
        return true;
    }

    // Helper functions
    // private Image getImageForItem(Item item) {
    //     if (item.getClass().getSimpleName().equals("BodyArmour")) return bodyArmourImage;
    //     else if (item.getClass().getSimpleName().equals("HealthPotion")) return healthPotionImage;
    //     else if (item.getClass().getSimpleName().equals("Helmet")) return helmetImage;
    //     else if (item.getClass().getSimpleName().equals("Shield")) return shieldImage;
    //     else if (item.getClass().getSimpleName().equals("Staff")) return staffImage;
    //     else if (item.getClass().getSimpleName().equals("Stake")) return stakeImage;
    //     else if (item.getClass().getSimpleName().equals("Sword")) return swordImage;
    //     else return theOneRingImage;
    // }

    private void turnRed(Label l) {
        l.setTextFill(Color.web("#B22222", 0.8));
        l.setFont(Font.font("System", FontWeight.EXTRA_BOLD, 12));
    }

    private void turnBlack(Label l) {
        l.setTextFill(Color.web("#000000"));
        l.setFont(Font.font("System", FontWeight.NORMAL, 12));

    }

    public void checkIfHitsZero(Item i) {
        StringProperty p = null;
        Label l = null;
        switch (i.getClass().getSimpleName()) {
            case "Sword":
                p = world.getSwordProperty();
                l = sellSwordNum;
                break;
            case "Stake":
                p = world.getStakeProperty();
                l = sellStakeNum;
                break;
            case "Staff":
                p = world.getStaffProperty();
                l = sellStaffNum;
                break;
            case "BodyArmour":
                p = world.getBodyArmourProperty();   
                l = sellBodyArmourNum;
                break;
            case "Helmet":
                p = world.getHelmetProperty();
                l = sellHelmetNum;
                break;
            case "Shield":
                p = world.getShieldProperty();
                l = sellShieldNum;
                break;
            case "HealthPotion":
                p = world.getHealthPotionProperty();
                l = sellHealthPotionNum;
                break;
            case "DoggieCoin":
                p = world.getDoggieCoinProperty();
                l = sellDoggieCoinNum;
                break;
            default:
                break;
        }
        if (p.toString().equals("StringProperty [value: 0]")) {
            turnRed(l);
            // System.out.println(p.toString());
        } else {
            turnBlack(l);
            // System.out.println(p.toString());
        }
    }

    public void checkIfHitsZero(String i) {
        StringProperty p = null;
        Label l = null;
        switch (i) {
            case "Sword":
                p = world.getSwordProperty();
                l = sellSwordNum;
                break;
            case "Stake":
                p = world.getStakeProperty();
                l = sellStakeNum;
                break;
            case "Staff":
                p = world.getStaffProperty();
                l = sellStaffNum;
                break;
            case "BodyArmour":
                p = world.getBodyArmourProperty();   
                l = sellBodyArmourNum;
                break;
            case "Helmet":
                p = world.getHelmetProperty();
                l = sellHelmetNum;
                break;
            case "Shield":
                p = world.getShieldProperty();
                l = sellShieldNum;
                break;
            case "HealthPotion":
                p = world.getHealthPotionProperty();
                l = sellHealthPotionNum;
                break;
            case "DoggieCoin":
                p = world.getDoggieCoinProperty();
                l = sellDoggieCoinNum;
                break;
            default:
                break;
        }
        if (p.toString().equals("StringProperty [value: 0]")) {
            turnRed(l);
            // System.out.println(p.toString());
        } else {
            turnBlack(l);
            // System.out.println(p.toString());
        }
    }

    public void initialiseNumColours(){
        for (String item: itemsList) {
            checkIfHitsZero(item);
        }
    }

    public void actionSuccessfulText(String s) {
        shopResponseLabel.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 20));
        shopResponseLabel.setTextFill(Color.web("#00ff6d"));
        shopResponseLabel.setText(s);
    }

    public void actionNotSuccessfulText(String s) {
        shopResponseLabel.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 20));
        shopResponseLabel.setTextFill(Color.web("#FF0000"));
        shopResponseLabel.setText(s);
    }

    public void resetResponseText() {
        shopResponseLabel.setText("");
    }

    public void setCountersToZero() {
        this.numHealthPotionsBought = 0;
        this.numArmourBought = 0;
    }
}

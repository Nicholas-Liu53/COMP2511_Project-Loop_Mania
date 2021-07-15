package unsw.loopmania;

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

    // All image views including tiles, character, enemies, cards... even though
    // cards in separate gridpane...
    private List<ImageView> entityImages;

    private LoopManiaWorld world;
    
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

    public LoopManiaWorld getWorld() {
        return this.world;
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

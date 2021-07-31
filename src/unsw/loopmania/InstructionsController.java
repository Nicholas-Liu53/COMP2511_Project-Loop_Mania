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
import javafx.scene.media.AudioClip;

// import javafx.util.Duration;
import org.javatuples.Pair;
import unsw.loopmania.items.*;

/**
 * controller for the shop menu.
 * 
 */
public class InstructionsController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher mainMenuSwitcher;
    
    public InstructionsController() {
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

    }

    public void setMainMenuSwitcher(MenuSwitcher menuSwitcher){
        this.mainMenuSwitcher = menuSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        buttonClickedSound();
        mainMenuSwitcher.switchMenu();
    }

    @FXML
    private void buttonClickedSound() {
        AudioClip buttonPressed = new AudioClip("file:src/sounds/defaultbuttonclick.wav");
        buttonPressed.play();
    }

}

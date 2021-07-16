package unsw.loopmania;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
public class GamemodeController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;
    
    private LoopManiaWorld world;
    private LoopManiaWorldController worldController;
    
    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
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

    // Constructor
    public GamemodeController() {
        
    }


    @FXML
    private void setGamemodeToStandard() throws IOException {
        world.setGamemode("Standard");
        switchToGame();
    }

    @FXML
    private void setGamemodeToSurvival() throws IOException {
        world.setGamemode("Survival");
        switchToGame();
    }

    @FXML
    private void setGamemodeToBerserker() throws IOException {
        world.setGamemode("Berserker");
        switchToGame();
    }
}

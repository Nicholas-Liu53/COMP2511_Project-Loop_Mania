package unsw.loopmania;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {
    // TODO = possibly add other menus?

    /**
     * the controller for the game. Stored as a field so can terminate it when click exit button
     */
    private LoopManiaWorldController mainGameController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // Prevent human player resizing game window (since otherwise would see white space)
        // Alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        // Load the main game
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json");
        mainGameController = loopManiaLoader.loadController();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainGameController);
        Parent gameRoot = gameLoader.load();

        // Load the main menu
        MainMenuController mainMenuController = new MainMenuController();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

        // Load the shop menu
        ShopMenuController shopMenuController = new ShopMenuController();
        FXMLLoader shopMenuLoader = new FXMLLoader(getClass().getResource("ShopMenuView.fxml"));
        shopMenuLoader.setController(shopMenuController);
        Parent shopMenuRoot = shopMenuLoader.load();

        // Create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(mainMenuRoot);
        
        // Set functions which are activated when button click to switch menu is pressed
        // E.g. from main menu to start the game, or from the game to return to main menu
        mainGameController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});
        mainGameController.setShopMenuSwitcher(() -> {
            switchToRoot(scene, shopMenuRoot, primaryStage);
            shopMenuController.addItemsInInventory(mainGameController.getUnequippedItems());
        });
        mainMenuController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainGameController.startTimer();
        });
        shopMenuController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            // mainGameController.startTimer();
        });

        //???
        shopMenuController.setWorld(mainGameController.getWorld());
        
        // Deploy the main onto the stage
        gameRoot.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop(){
        // Wrap up activities when exit program
        mainGameController.terminate();
    }

    /**
     * Switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage){
        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

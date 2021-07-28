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

        // Load the instructions menu
        InstructionsController instructionsController = new InstructionsController();
        FXMLLoader instructionsLoader = new FXMLLoader(getClass().getResource("InstructionsView.fxml"));
        instructionsLoader.setController(instructionsController);
        Parent instructionsRoot = instructionsLoader.load();

        // Load the game over screen
        FXMLLoader gameoverLoader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
        Parent gameoverRoot = gameoverLoader.load();

        // Load the game won screen
        FXMLLoader gamewonLoader = new FXMLLoader(getClass().getResource("GameWonView.fxml"));
        Parent gamewonRoot = gamewonLoader.load();

        // Load the gamemode menu
        GamemodeController gamemodeController = new GamemodeController();
        gamemodeController.setWorld(mainGameController.getWorld());
        gamemodeController.setWorldController(mainGameController);
        FXMLLoader gamemodeLoader = new FXMLLoader(getClass().getResource("GamemodeView.fxml"));
        gamemodeLoader.setController(gamemodeController);
        Parent gamemodeRoot = gamemodeLoader.load();

        // Load the shop menu
        ShopMenuController shopMenuController = new ShopMenuController();
        shopMenuController.setWorld(mainGameController.getWorld());
        shopMenuController.setWorldController(mainGameController);
        FXMLLoader shopMenuLoader = new FXMLLoader(getClass().getResource("ShopMenuView.fxml"));
        shopMenuLoader.setController(shopMenuController);
        Parent shopMenuRoot = shopMenuLoader.load();
        mainGameController.setShopController(shopMenuController);

        // Load goals menu
        GoalsMenuController goalsMenuController = new GoalsMenuController();
        goalsMenuController.setWorld(mainGameController.getWorld());
        goalsMenuController.setWorldController(mainGameController);
        FXMLLoader goalsMenuLoader = new FXMLLoader(getClass().getResource("GoalsMenuView.fxml"));
        goalsMenuLoader.setController(goalsMenuController);
        Parent goalsMenuRoot = goalsMenuLoader.load();
        mainGameController.setGoalsMenuController(goalsMenuController);

        // Create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(mainMenuRoot);
        
        // Set functions which are activated when button click to switch menu is pressed
        // E.g. from main menu to start the game, or from the game to return to main menu
        mainGameController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});
        mainGameController.setGoalsMenuSwitcher(() -> {
            switchToRoot(scene, goalsMenuRoot, primaryStage);
        });
        mainGameController.setShopMenuSwitcher(() -> {
            switchToRoot(scene, shopMenuRoot, primaryStage);
        });
        mainGameController.setGameoverSwitcher(() -> {
            switchToRoot(scene, gameoverRoot, primaryStage);
        });
        mainGameController.setGamewonSwitcher(() -> {
            switchToRoot(scene, gamewonRoot, primaryStage);
        });
        mainMenuController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainGameController.startTimer();
        });
        mainMenuController.setGamemodeSwitcher(() -> {
            switchToRoot(scene, gamemodeRoot, primaryStage);
            // mainGameController.startTimer();
        });
        shopMenuController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            // mainGameController.startTimer();
        });
        gamemodeController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            // mainGameController.startTimer();
        });
        goalsMenuController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            // mainGameController.startTimer();
        });
        
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

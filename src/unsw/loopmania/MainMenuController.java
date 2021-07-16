package unsw.loopmania;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * controller for the main menu.
 * TODO = you could extend this, for example with a settings menu, or a menu to load particular maps.
 */
public class MainMenuController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;
    private MenuSwitcher gamemodeSwitcher;

    @FXML
    private Button startGameButton;

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        startGameButton.setText("Resume Game");
        gameSwitcher.switchMenu();
    }

    public void setGamemodeSwitcher(MenuSwitcher gamemodeSwitcher){
        this.gamemodeSwitcher = gamemodeSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGamemode() throws IOException {
        startGameButton.setText("Resume Game");
        gamemodeSwitcher.switchMenu();
    }
}

package unsw.loopmania;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;

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
    private MenuSwitcher instructionsSwitcher;

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
        buttonClickedSound();
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
        buttonClickedSound();
        gamemodeSwitcher.switchMenu();
    }

    public void setInstructionsSwitcher(MenuSwitcher instructionsSwitcher){
        this.instructionsSwitcher = instructionsSwitcher;
    }

    @FXML
    private void switchToInstructions() throws IOException {
        buttonClickedSound();
        instructionsSwitcher.switchMenu();
        
    }

    @FXML
    private void buttonClickedSound() {
        AudioClip buttonPressed = new AudioClip("file:src/sounds/defaultbuttonclick.wav");
        buttonPressed.play();
    }
}

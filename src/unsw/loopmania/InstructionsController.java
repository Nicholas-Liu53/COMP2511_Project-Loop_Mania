package unsw.loopmania;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;

/**
 * controller for the instructions menu.
 * 
 */
public class InstructionsController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher mainMenuSwitcher;

    public InstructionsController() {
    }

    @FXML
    public void initialize() {

    }

    public void setMainMenuSwitcher(MenuSwitcher menuSwitcher) {
        this.mainMenuSwitcher = menuSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * 
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

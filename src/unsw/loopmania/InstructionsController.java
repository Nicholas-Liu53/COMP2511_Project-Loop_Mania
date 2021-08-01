package unsw.loopmania;

// import java.io.File;
import java.io.IOException;
// import java.util.EnumMap;
// import java.util.HashMap;
// import java.util.List;

import javafx.fxml.FXML;

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
        mainMenuSwitcher.switchMenu();
    }

}

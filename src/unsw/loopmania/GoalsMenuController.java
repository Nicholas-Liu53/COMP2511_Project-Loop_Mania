package unsw.loopmania;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class GoalsMenuController {

    private MenuSwitcher gameSwitcher;
    private LoopManiaWorld world;
    private LoopManiaWorldController worldController;

    @FXML
    private Text goalsText;

    @FXML
    private Button returnButton;

    @FXML
    public void initialize() {
        world.goalsProperty().bindBidirectional(goalsText.textProperty());
    }

    @FXML
    public void returnGame(ActionEvent event) {
        world.goldProperty();
        worldController.startTimer();
        gameSwitcher.switchMenu();
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    public void setWorldController(LoopManiaWorldController worldController) {
        this.worldController = worldController;
    }

    public void setWorld(LoopManiaWorld world) {
        this.world = world;
    }
}
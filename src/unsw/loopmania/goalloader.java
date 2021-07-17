package unsw.loopmania;

import java.io.FileNotFoundException;
// import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
// import org.json.JSONTokener;

import unsw.loopmania.goals.*;

public class goalloader {
    private JSONObject json;

    public goalloader(String filename) throws FileNotFoundException {
        this.json = parseJSON(filename);
    }

    public static JSONObject parseJSON(String filename) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            return new JSONObject(content);
        } catch (IOException e) {
            return null;
        }
    }

    public void loadAllGoals() {
    }

    /**
     * Parses the JSON to create a goal.
     */
    public ComplexGoalComposite loadComplexGoal(ComplexGoalComposite cg, JSONObject goalCondition) {
        return cg;
    }

    private void loadSubGoals(ComplexGoalComposite cg, JSONArray subgoals) {
    }

    public static void main(String args[]) {
        try {
            goalloader g = new goalloader(
                    "C:/Users/Sanamya Gosain/21T2-cs2511-project/src/unsw/loopmania/goals/3.json");
            g.loadAllGoals();
        } catch (IOException e) {
            return;
        }
    }
}

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
        ComplexGoalComposite x = loadComplexGoal(null, this.json.getJSONObject("goal-condition"));
        System.out.println(x);
        System.out.println(x.getChildren());
    }

    /**
     * Parses the JSON to create a goal.
     */
    public ComplexGoalComposite loadComplexGoal(ComplexGoalComposite cg, JSONObject goalCondition) {
        if (cg == null) {
            if (goalCondition.getString("goal").equals("AND")) {
                cg = new ComplexGoalComposite("AND");
                loadSubGoals(cg, goalCondition.getJSONArray("subgoals"));
            } else if (goalCondition.getString("goal").equals("OR")) {
                cg = new ComplexGoalComposite("OR");
                loadSubGoals(cg, goalCondition.getJSONArray("subgoals"));
            }
            
            if (goalCondition.getString("goal").equals("cycles")) {
                cg.add(new CyclesBaseGoal(goalCondition.getInt("quantity")));
            } else if (goalCondition.getString("goal").equals("experience")) {
                cg.add(new XpBaseGoal(goalCondition.getInt("quantity")));
            } else if (goalCondition.getString("goal").equals("gold")) {
                cg.add(new GoldBaseGoal(goalCondition.getInt("quantity")));
            } else {
                throw new RuntimeException("Goal object requires either a basic goal or an operator for subgoal.");
            }

        return cg;
    }

    private void loadSubGoals(ComplexGoalComposite cg, JSONArray subgoals) {
        for (int i = 0; i < subgoals.length(); i++) {
            loadComplexGoal(cg, subgoals.getJSONObject(i));
        }
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

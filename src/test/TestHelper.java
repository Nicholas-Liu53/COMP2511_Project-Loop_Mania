package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.path.*;
import unsw.loopmania.goals.*;

import java.util.List;

/**
 * Functions we have taken from the starter code to help with testing
 */
public class TestHelper {

    /**
     * Creates PathPosition for use with moving entities in testing based on path
     * found in the file associated with fileName
     * 
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static PathPosition generatePathPosition(String fileName) throws FileNotFoundException {
        JSONObject json = new JSONObject(new JSONTokener(new FileReader(fileName)));

        int width = json.getInt("width");
        int height = json.getInt("height");

        List<Pair<Integer, Integer>> orderedPath = loadPathTiles(json.getJSONObject("path"), width, height);

        return (new PathPosition(0, orderedPath));
    }

    /**
     * Takes in a file path and returns
     * 
     * @param fileName
     * @return ordered path
     */
    public static List<Pair<Integer, Integer>> generatePathTiles(String fileName) throws FileNotFoundException {
        JSONObject json = new JSONObject(new JSONTokener(new FileReader(fileName)));

        int width = json.getInt("width");
        int height = json.getInt("height");

        return loadPathTiles(json.getJSONObject("path"), width, height);
    }

    /**
     * load path tiles, taken from LoopManiaWorldLoader.java
     * 
     * @param path   json data loaded from file containing path information
     * @param width  width in number of cells
     * @param height height in number of cells
     * @return list of x, y cell coordinate pairs representing game path
     */
    private static List<Pair<Integer, Integer>> loadPathTiles(JSONObject path, int width, int height) {
        if (!path.getString("type").equals("path_tile")) {
            // ... possible extension
            throw new RuntimeException(
                    "Path object requires path_tile type.  No other path types supported at this moment.");
        }
        PathTile starting = new PathTile(new SimpleIntegerProperty(path.getInt("x")),
                new SimpleIntegerProperty(path.getInt("y")));
        if (starting.getY() >= height || starting.getY() < 0 || starting.getX() >= width || starting.getX() < 0) {
            throw new IllegalArgumentException("Starting point of path is out of bounds");
        }
        // load connected path tiles
        List<PathTile.Direction> connections = new ArrayList<>();
        for (Object dir : path.getJSONArray("path").toList()) {
            connections.add(Enum.valueOf(PathTile.Direction.class, dir.toString()));
        }

        if (connections.isEmpty()) {
            throw new IllegalArgumentException(
                    "This path just consists of a single tile, it needs to consist of multiple to form a loop.");
        }

        // load the first position into the orderedPath
        PathTile.Direction first = connections.get(0);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(Pair.with(starting.getX(), starting.getY()));

        int x = starting.getX() + first.getXOffset();
        int y = starting.getY() + first.getYOffset();

        // add all coordinates of the path into the orderedPath
        for (int i = 1; i < connections.size(); i++) {
            orderedPath.add(Pair.with(x, y));

            if (y >= height || y < 0 || x >= width || x < 0) {
                throw new IllegalArgumentException(
                        "Path goes out of bounds at direction index " + (i - 1) + " (" + connections.get(i - 1) + ")");
            }

            PathTile.Direction dir = connections.get(i);
            x += dir.getXOffset();
            y += dir.getYOffset();
            if (orderedPath.contains(Pair.with(x, y)) && !(x == starting.getX() && y == starting.getY())) {
                throw new IllegalArgumentException("Path crosses itself at direction index " + i + " (" + dir + ")");
            }
        }
        // we should connect back to the starting point
        if (x != starting.getX() || y != starting.getY()) {
            throw new IllegalArgumentException(String.format(
                    "Path must loop back around on itself, this path doesn't finish where it began, it finishes at %d, %d.",
                    x, y));
        }

        return orderedPath;
    }

    /**
     * Generates a sample LoopManiaWorld for testing
     * 
     * @return the generated world
     */
    public static LoopManiaWorld generateWorld() {
        List<Pair<Integer, Integer>> orderedPath = null;

        try {
            orderedPath = TestHelper.generatePathTiles("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Using Gradle rather than VSCode, requires different path
            try {
                orderedPath = TestHelper.generatePathTiles("src/test/Resources/world_with_twists_and_turns.json");
            } catch (FileNotFoundException ee) {
                System.err.println("Unable to find testing resource");
            }
        }

        return new LoopManiaWorld(1, 2, orderedPath);
    }

    /**
     * Takes in the JSON goal condition and recursively generates a ComplexGoalComponent
     * that models the goals specified by the JSON file
     * 
     * @param goalCondition
     * @return ComplexGoalCompsite modelling the goals
     */
    public static ComplexGoalComponent loadGoals(JSONObject goalCondition) {
        if (goalCondition.getString("goal").equals("AND") || goalCondition.getString("goal").equals("OR")) {
            // Constructing a new complex goal out of the sub goals
            ComplexGoalComposite newComponent = new ComplexGoalComposite(goalCondition.getString("goal"));
            JSONArray subGoals = goalCondition.getJSONArray("subgoals");

            for (int i = 0; i < subGoals.length(); i++) {
                // Recursively add goals
                newComponent.add(loadGoals(subGoals.getJSONObject(i)));
            }

            return newComponent;
        } else {
            // Return a base goal
            if (goalCondition.getString("goal").equals("experience")) {
                return new XpBaseGoal(goalCondition.getInt("quantity"));
            } else if (goalCondition.getString("goal").equals("gold")) {
                return new GoldBaseGoal(goalCondition.getInt("quantity"));
            } else if (goalCondition.getString("goal").equals("cycles")) {
                return new CyclesBaseGoal(goalCondition.getInt("quantity"));
            }
        }

        // Execution shouldn't reach here
        return null;
    }
}

package unsw.loopmania.buildings;

import java.util.List;
import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.WorldStateObserver;
import unsw.loopmania.path.PathPosition;

/**
 * a Building in the world
 * which doesn't move
 */
public abstract class Building extends StaticEntity implements WorldStateObserver {
    // TODO = implement other varieties of card than VampireCastleCard
    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Implementing WorldStateObserver, by default a building does nothing
     * when notified
     */
    public void notify(LoopManiaWorld worldState) {
        return;
    }

    /**
     * Finds a path tile that is adjacent to this building's position
     * @param orderedPath
     * @return PathPosition if an adjacent tile is found, otherwise null
     */
    public PathPosition getAdjacentPathTile(List<Pair<Integer, Integer>> orderedPath) {
        int[] xList = {super.getX() + 1, super.getX() - 1};
        int[] yList = {super.getY() + 1, super.getY() - 1};

        for (int x : xList) {
            int y = super.getY();
            int pairIterator = 0;
            for (Pair<Integer, Integer> pair : orderedPath) {
                if (pair.getValue0() == x && pair.getValue1() == y) {
                    // Adjacent tile found, returning path position at that tile
                    return new PathPosition(pairIterator, orderedPath);
                }
            }
        } 
        
        for (int y : yList) {
            int x = super.getY();
            int pairIterator = 0;
            for (Pair<Integer, Integer> pair : orderedPath) {
                if (pair.getValue0() == x && pair.getValue1() == y) {
                    // Adjacent tile found, returning path position at that tile
                    return new PathPosition(pairIterator, orderedPath);
                }
            }
        } 

        return null;
    }
    
    @Override
    public String getStaticEntityType() {
        return "Building";
    }
}

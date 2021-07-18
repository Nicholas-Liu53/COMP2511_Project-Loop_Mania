package unsw.loopmania.buildings;

import java.util.List;
import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.StaticEntity;
import unsw.loopmania.WorldStateObserver;
import unsw.loopmania.character.Character;
import unsw.loopmania.path.PathPosition;

/**
 * a Building in the world which doesn't move
 */
public abstract class Building extends StaticEntity implements WorldStateObserver {
    private int buildingRadius;

    public Building(Pair<Integer, Integer> position, int radius) {
        super(position);
        this.buildingRadius = -1;
    }

    /**
     * @return building's radius
     */
    public int getBuildingRadius() {
        return this.buildingRadius;
    }

    /**
     * Implementing WorldStateObserver, by default a building does nothing when
     * notified
     */
    public void notifyCycle(LoopManiaWorld worldState) {
        return;
    }

    public void notifyTick(Character mainChar, LoopManiaWorld world) {
        return;
    }

    /**
     * Finds a path tile that is adjacent to this building's position
     * 
     * @param orderedPath
     * @return PathPosition if an adjacent tile is found, otherwise null
     */
    public PathPosition getAdjacentPathTile(List<Pair<Integer, Integer>> orderedPath) {
        int x = super.getX() - 1;
        int y = super.getY() - 1;

        int xIterator = 0;
        // Iterating through x
        while (xIterator < 3) {
            int yIterator = 0;
            // Iterating through y
            while (yIterator < 3) {
                // Iterating through path to find relevant tile
                int pairIterator = 0;
                for (Pair<Integer, Integer> pair : orderedPath) {
                    if (pair.getValue0() == x + xIterator && pair.getValue1() == y + yIterator) {
                        // Adjacent tile found, returning path position at that tile
                        return new PathPosition(pairIterator, orderedPath);
                    }
                    pairIterator++;
                }
                yIterator++;
            }
            xIterator++;
        }

        return null;
    }

    @Override
    public String getStaticEntityType() {
        return "Building";
    }
}

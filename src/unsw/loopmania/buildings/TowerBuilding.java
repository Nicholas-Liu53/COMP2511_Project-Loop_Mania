package unsw.loopmania.buildings;

import org.javatuples.Pair;

/**
 * represents a tower building in the backend game world
 */
public class TowerBuilding extends Building {
    private int shootingRadius;

    public TowerBuilding(Pair<Integer, Integer> position) {
        super(position, 3);
    }

    /**
     * @return Tower Building's shooting radius
     */
    public int getShootingRadius() {
        return this.shootingRadius;
    }
}

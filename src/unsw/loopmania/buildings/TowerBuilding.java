package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * a basic form of building in the world
 */
public class TowerBuilding extends Building {
    private int shootingRadius;

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, 3);
    }

    /**
     * @return Tower Building's shooting radius
     */
    public int getShootingRadius() {
        return this.shootingRadius;
    }
}

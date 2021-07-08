package unsw.loopmania.buildings;

import javafx.beans.property.SimpleIntegerProperty;
// import unsw.loopmania.StaticEntity;

/**
 * a basic form of building in the world
 */
public class ZombiePitBuilding  extends Building {
    // TODO = add more types of building, and make sure buildings have effects on entities as required by the spec
    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}

package unsw.loopmania.buildingcards;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a village card in the backend game world
 */
public class VillageCard extends Card {
    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    
}

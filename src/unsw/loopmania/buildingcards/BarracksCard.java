package unsw.loopmania.buildingcards;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a barracks card in the backend game world
 */
public class BarracksCard extends Card {
    public BarracksCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "BarracksCard");
    }    
}
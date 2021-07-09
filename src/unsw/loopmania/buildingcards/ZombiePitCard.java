package unsw.loopmania.buildingcards;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a zombie pit card in the backend game world
 */
public class ZombiePitCard extends Card {
    public ZombiePitCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "ZombiePitCard");
    }    
}
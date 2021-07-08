package unsw.loopmania.buildingcards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {
    
    String cardId;

    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y, String cardId) {
        super(x, y);
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }
}

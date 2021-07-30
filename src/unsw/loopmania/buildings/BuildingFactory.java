package unsw.loopmania.buildings;

import org.javatuples.Pair;
import unsw.loopmania.buildingcards.*;

/**
 * Factory for buildings
 */
public class BuildingFactory {
    
    /**
     * Takes in a card and a location, returns building specified by card in specified location
     * 
     * @param card
     * @param newLocation
     * @return newly created building
     */
    public static Building getBuilding(Card card, Pair<Integer, Integer> newLocation) {
        Building newBuilding = null;
        if (card instanceof BarracksCard)
            newBuilding = new BarracksBuilding(newLocation);
        else if (card instanceof CampfireCard)
            newBuilding = new CampfireBuilding(newLocation);
        else if (card instanceof TowerCard)
            newBuilding = new TowerBuilding(newLocation);
        else if (card instanceof TrapCard)
            newBuilding = new TrapBuilding(newLocation);
        else if (card instanceof VampireCastleCard)
            newBuilding = new VampireCastleBuilding(newLocation);
        else if (card instanceof VillageCard)
            newBuilding = new VillageBuilding(newLocation);
        else if (card instanceof ZombiePitCard)
            newBuilding = new ZombiePitBuilding(newLocation);

        return newBuilding;
    }
}

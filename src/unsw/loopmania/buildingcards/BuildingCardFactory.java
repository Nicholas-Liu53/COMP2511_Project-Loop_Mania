package unsw.loopmania.buildingcards;

import org.javatuples.Pair;

public class BuildingCardFactory {
    
    public static Card getCard(String cardType, Pair<Integer, Integer> position) {
        Card card = null;

        switch (cardType) {
            case "BarracksCard":
                card = new BarracksCard(position);
                break;
            case "CampfireCard":
                card = new CampfireCard(position);
                break;
            case "TowerCard":
                card = new TowerCard(position);
                break;
            case "TrapCard":
                card = new TrapCard(position);
                break;
            case "VillageCard":
                card = new VillageCard(position);
                break;
            case "ZombiePitCard":
                card = new ZombiePitCard(position);
                break;
            case "VampireCastleCard":
                card = new VampireCastleCard(position);
                break;
            default:
                break;
        }

        return card;
    }
}

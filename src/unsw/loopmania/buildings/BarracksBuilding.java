package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.Ally;
import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Soldier;

/**
 * represents a barracks building in the backend game world
 */
public class BarracksBuilding extends Building {
    public BarracksBuilding(Pair<Integer, Integer> position) {
        super(position, -1);
    }

    @Override
    public void notifyTick(Character mainChar, LoopManiaWorld world) {
        // Adding an ally everytime character passes through and isn't in battle
        if (this.getX() == mainChar.getX() && this.getY() == mainChar.getY() && !mainChar.getInBattle()) {
            Soldier newSoldier = new Soldier();
            mainChar.addAlly(newSoldier);
        }
    }
}

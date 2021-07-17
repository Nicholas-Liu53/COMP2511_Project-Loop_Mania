package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;

/**
 * represents a trap building in the backend game world
 */
public class TrapBuilding extends Building {
    public TrapBuilding(Pair<Integer, Integer> position) {
        super(position, -1);
    }

    @Override
    public void notifyTick(Character mainChar, LoopManiaWorld world) {
        // for (Building b : world.getbbuildingEntities) {
        //         if (b instanceof TrapBuilding) {
        //                 if (b.getX() == (enemy.getX()) && b.getY() == (enemy.getY())) {
        //                         enemy.receiveAttack(30);
        //                         this.buildingEntities.remove(b);
        //                         Pair<Integer, Integer> temp = new Pair<Integer, Integer>(b.getX(), b.getY());
        //                         this.locationOfPlacedBuildings.remove(temp);
        //                         b.destroy();
        //                         break;
        //                     }
        //                 }
        //             }
                    
        //             if (enemy.getHealth() == 0)
        //                 return true;
                    
        //             return false;
        return;
    }
}
